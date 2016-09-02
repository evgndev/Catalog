catalogApp.controller("ItemController", function ItemController($scope, ItemsFct, Upload, $routeParams, $modal) {

    var itemCtrl = this;
    itemCtrl.readonly = false;
    itemCtrl.item = {};
    itemCtrl.image = {
        itemId: undefined,
        imageUrl: undefined,
        thumbnailsURL: undefined,
        imageURL: undefined
    };

    /**
     * Get item data (for render to form)
     */
    itemCtrl.getItem = function () {
        if (itemCtrl.item && itemCtrl.item.itemId) {
            ItemsFct.get(
                {//params
                    itemId: itemCtrl.item.itemId
                },
                function (data) {//success request
                    //set item
                    itemCtrl.item = data;
                    if (data.imageId) {
                        // set imageId
                        itemCtrl.image.imageId = data.imageId;
                        //set image urls
                        itemCtrl.image.thumbnailsURL = '/images/' + data.imageId + '/thumbnails.jpg';
                        itemCtrl.image.imageURL = '/images/' + data.imageId + '/image.jpg';
                    }
                }
            );
        } else {
            itemCtrl.item = {};
        }
    };

    // if url with itemId, get item by this id
    if ($routeParams.itemId) {
        //set item id
        itemCtrl.item = {
            itemId: $routeParams.itemId
        };
        // get and show item
        itemCtrl.getItem();
    }

    /**
     * Update/create item
     */
    itemCtrl.update = function () {
        var successFn = function (success) {
            $scope.navCtrl.changeView('/items', 2);
        };

        var errorFn = function (error) {
            console.log('TEST error', error);
            $scope.navCtrl.itemCreationStatus = 1;
        };

        if (itemCtrl.item.itemId) { // update or create new
            itemCtrl.item = ItemsFct.update({itemId: itemCtrl.item.itemId}, itemCtrl.item, successFn, errorFn);
        } else {
            itemCtrl.item = ItemsFct.create(itemCtrl.item, successFn);
        }
    };

    /**
     * Send image after selection
     *
     * @param $file selected file
     */
    itemCtrl.onFileSelect = function ($file) {
        if ($file) {
            Upload
                .upload({
                    url: '/images',
                    fields: {'name': itemCtrl.item.name},
                    file: $file
                })
                .then(
                    function (successData) {// file is uploaded successfully
                        var imageObject = successData.data;
                        // set thumbnail image for view
                        itemCtrl.image.thumbnailsURL = 'data:image/jpeg;base64,' + imageObject.thumbnailData;
                        itemCtrl.image.imageURL = '/images/' + imageObject.imageId + '/image.jpg'; //TODO pop-up
                        // set image id for item
                        itemCtrl.item.imageId = imageObject.imageId;
                    },
                    function (errorData) {
                        console.log('Send image error: ', errorData.data.message);
                    }
                );
        }
    };


    itemCtrl.showFullImage = function () {
        if (itemCtrl.image.imageId) {
            $modal({
                title: 'Image',
                template: 'template/image.html',
                scope: $scope,
                show: true
            });
        }
    };
});

catalogApp.controller('ModalCtrl', function($scope) {
    $scope.title = "Title from isolate scope";
    $scope.content = "Content from isolate scope (modal controller scope)";
});

