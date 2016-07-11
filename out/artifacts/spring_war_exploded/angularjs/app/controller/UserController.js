/**
 * Created by gy on 16/7/10.
 */

'use strict';
angular.module('myApp')
    .controller('userCtrl', function($scope, userService) {
        $scope.greeting = 'hello';

        $scope.save = function () {
            var s = userService.save({}, JSON.stringify({
                email: $scope.user.email,
                firstName: $scope.user.firstName,
                lastName: $scope.user.lastName,
                gender: $scope.user.gender
            }));
            s.$promise.then(function () {
                console.log('success');
            })
        }
    });
