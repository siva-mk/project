'use strict';

angular.module('erpApp')
    .factory('OrderItemBilling', function ($resource) {
        return $resource('api/orderItemBillings/:id', {}, {
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
