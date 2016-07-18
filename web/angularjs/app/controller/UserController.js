/**
 * Created by gy on 16/7/10.
 */

'use strict';
angular.module('myApp')
    .controller('UserCtrl', function($scope, userService) {
        this.greeting = 'hello world';

        this.register = function (user) {
            var t = userService.save({}, JSON.stringify({
                email: user.email,
                username: user.username,
                password: user.password,
                gender: user.gender
            }));
            t.$promise.then(function () {
                console.log("register success");
            })
        };

        this.login = function(credentials) {
            var t = userService.login({}, {
                username: credentials.username,
                password: credentials.password
            });
        }

    });
