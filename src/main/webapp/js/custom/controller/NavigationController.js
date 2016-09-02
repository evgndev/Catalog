catalogApp.controller("NavigationController", function NavigationController($scope, $location, $cookieStore) {
    var ctrl = this;

    // 0 ok
    // 1 error
    // 2 success
    ctrl.itemCreationStatus = 0;
    ctrl.editorMode = $cookieStore.get('editorMode') | false;

    $scope.currentView = $location.path() || '/home';

    ctrl.changeView = function (view, itemCreationStatus) {
        $location.path(view);
        $scope.currentView = view;
        ctrl.itemCreationStatus = itemCreationStatus || 0;
    };
    ctrl.changeMode = function () {
        ctrl.editorMode = !ctrl.editorMode
        $cookieStore.put('editorMode', ctrl.editorMode);
    };
});