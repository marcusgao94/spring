/**
 * Created by gy on 16/7/11.
 */

'use strict';
angular.module('myApp')
    .factory('userService', function ($resource) {
        return $resource('user/:uid', {}, {
            get: {
                method: 'GET',
                cache: true
            },
            save: {
                url: SAVEUSER,
                method: 'POST'
            },
            login: {
                url: SIGNIN,
                method: 'POST'
            },
            isExist: {
                url:  ISEMAILEXIST,
                method: 'POST'
            }
        })
    });
