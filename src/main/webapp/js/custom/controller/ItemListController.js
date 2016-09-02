catalogApp.controller("ItemListController", function ItemListController($scope, ItemsFct) {

    var itemListCtrl = this,
        pagination = {
            page: 1,
            pageSize: 10,
            total: 0,
            pageCount: 0,
            pagesForSelection: [1],
            sortProperty: 'itemId',
            sortDirection: 'asc',
            items: {},
            wasError: false
        };

    $scope.pagination = pagination;

    /**
     *  Refresh table
     */
    $scope.refresh = function () {

        var countSuccessCb = function (result) {
            var total = result.data;

            //refresh pagination model
            _updatePaginationData(total);
            pagination.wasError = false;

            if (total > 0) {// get items
                ItemsFct.query(
                    { //request data
                        page: pagination.page,
                        pageSize: pagination.pageSize,
                        sortProperty: pagination.sortProperty,
                        sortDirection: pagination.sortDirection
                    },
                    function (result) { // success callback
                        pagination.items = result;
                        pagination.wasError = false;
                    },
                    function (result) { // error callback
                        pagination.wasError = true;
                    }
                );
            }
        };

        var errorCb = function(error) {
            pagination.wasError = true;
        };

        ItemsFct.count(countSuccessCb, errorCb);
    };

    // load table
    $scope.refresh();

    /**
     * Define pagination data by total item count.
     * Set total count, page count, pages for selection bar
     * @param total item count
     * @private
     */
    var _updatePaginationData = function (total) {
        //set total items count
        pagination.total = total;

        // set page count
        var pageCount = 1;
        if (pagination.total > pagination.pageSize) {
            pageCount = Math.ceil((pagination.total / pagination.pageSize));
        }
        pagination.pageCount = pageCount;

        // set pages for view
        // it is for page selection bar, under table
        var n = 3;
        var startViewPage = 1;  // first displayed page for bar
        if (pagination.page > (n + 1)) { // will show 'n' pages before current page
            startViewPage = pagination.page - n;
        }

        var endViewPage = 1; // last displayed page for bar
        if (pageCount > 1) { // will show 'n' pages after current page
            endViewPage = pagination.page + n;
            if (endViewPage > pageCount) {
                endViewPage = pageCount;
            }
        }

        var pagesForSelection = []; // all allowed pages for selection bar
        for (var page = startViewPage; page <= endViewPage; page++) {
            pagesForSelection.push(page);
        }
        pagination.pagesForSelection = pagesForSelection;
    };

    itemListCtrl.nextPage = function () {
        if (pagination.page < pagination.pageCount) {
            pagination.page = pagination.page + 1;
            $scope.refresh();
        }
    };

    itemListCtrl.toPage = function (page) {
        if (pagination.page != page) {
            pagination.page = page;
            $scope.refresh();
        }
    };

    itemListCtrl.previousPage = function () {
        if ($scope.pagination.page > 1) {
            pagination.page = $scope.pagination.page - 1;
            $scope.refresh();
        }
    };
});