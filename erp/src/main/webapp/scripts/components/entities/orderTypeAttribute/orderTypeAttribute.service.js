'use strict';

angular.module('erpApp')
    .factory('OrderTypeAttribute', function ($resource) {
        return $resource('api/orderTypeAttributes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
