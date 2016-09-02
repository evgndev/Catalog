var catalogApp = angular.module('catalogApp',
    [
        'ngResource',
        'ngRoute',
        'ngCookies',
        'pascalprecht.translate',
        'ngFileUpload',
        'mgcrea.ngStrap.modal'
    ]
);

// Message localization
var translation = function ($translateProvider) {
    $translateProvider.preferredLanguage('en');
};
catalogApp.config(translation);

// White list
catalogApp.config(function ($compileProvider) {
    $compileProvider.imgSrcSanitizationWhitelist(/^\s*(https|http|ftp|mailto|file|tel|data)/);
});

// Navigation
function router($routeProvider) {
    $routeProvider
        .when('/home', {
            templateUrl: 'template/home.html'
        })
        .when('/items', {
            templateUrl: 'template/items.html',
            controller: 'ItemListController as itemListCtrl'
        })
        .when('/items/new', {
            templateUrl: 'template/edit.html',
            controller: 'ItemController as itemCtrl'
        })
        .when('/items/edit/:itemId', {
            templateUrl: 'template/edit.html',
            controller: 'ItemController as itemCtrl'
        })
        .when('/items/:itemId', {
            templateUrl: 'template/view.html',
            controller: 'ItemController as itemCtrl'
        })
        .when('/contacts', {
            templateUrl: 'template/contacts.html'
        })
        .otherwise({
            redirectTo: '/home'
        });
}
catalogApp.config(router);

// Items REST service
catalogApp.factory("ItemsFct", ["$resource", function ($resource) {
    return $resource('items/:count:itemId', {}, {
        'query': {
            method: 'GET',
            isArray: true,
            params: {
                page: '@page',
                pageSize: '@pageSize',
                sortProperty: '@sortProperty',
                sortDirection: '@sortDirection'
            }
        },
        'count': {
            method: 'GET',
            params: {count: 'count'}
        },
        'get': {
            method: 'GET',
            params: {itemId: '@itemId'}
        },
        'create': {
            method: 'POST'
        },
        'update': {
            method: 'PUT',
            params: {itemId: '@itemId'}
        },
        'delete': {
            method: 'DELETE'
        }
    });
}]);

