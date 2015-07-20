'use strict';

angular.module('erpApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


