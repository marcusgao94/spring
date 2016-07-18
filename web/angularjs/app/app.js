'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', ['ngResource', 'ngRoute'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/register', {
                templateUrl: 'register',
                controller: 'UserCtrl as userCtrl'
            })
            .when('/login', {
                templateUrl: 'loginpage',
                controller: 'UserCtrl as userCtrl'
            })
            .when('/forum', {
                templateUrl: 'secure/forum'
            })
    });
