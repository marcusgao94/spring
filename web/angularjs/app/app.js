'use strict';

// Declare app level module which depends on views, and components
angular.module('myApp', ['ngResource', 'ngRoute'])
    .config(function ($routeProvider) {
        $routeProvider
            .when('/register', {
                templateUrl: 'register',
                controller: 'userCtrl'
            })
            .when('/login', {
                templateUrl: 'login',
                controller: 'userCtrl'
            });
    });
