/**
 * Created by gy on 16/7/10.
 */

'use strict';
angular.module('myApp')
    .controller('UserCtrl', function($scope, userService) {
        this.greeting = 'hello world';

        this.register = function (user) {
            var exist = userService.isExist({}, JSON.stringify({
                email: user.email
            }));
            exist.$promise.then(function () {
                alert('email exists');
                return ;
            });
            if (user.password != user.passwordConfirm) {
                alert('confirm password');
                return ;
            }
            var t = userService.save({}, JSON.stringify({
                email: user.email,
                username: user.username,
                password: CryptoJS.MD5(user.password).toString(),
                gender: user.gender
            }));
            t.$promise.then(function () {
                console.log("register success");
            })
        };

        this.login = function(credentials) {
            var t = userService.login({}, {
                username: credentials.username,
                password: CryptoJS.MD5(credentials.password).toString()
            });
        }

    });
