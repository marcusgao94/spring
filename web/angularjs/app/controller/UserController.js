/**
 * Created by gy on 16/7/10.
 */

'use strict';
angular.module('myApp')
    .controller('userCtrl', function($scope, userService) {
        $scope.greeting = 'hello';

        $scope.register = function(user) {
            var s = userService.save({}, JSON.stringify({
                email: user.email,
                firstName: user.firstName,
                lastName: user.lastName,
                gender: user.gender
            }));
            s.$promise.then(function () {
                console.log('success');
            })
        };
        
        $scope.login = function(credentials) {
            console.log('aa');
        };
    });
