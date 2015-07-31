'use strict';

angular.module('erpApp')
    .factory('OrderItemAttribute', function ($resource) {
        return $resource('api/orderItemAttributes/:id', {}, {
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
