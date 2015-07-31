'use strict';

angular.module('erpApp')
    .factory('OrderItem', function ($resource) {
        return $resource('api/orderItems/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.estimatedDeliveryDate = new Date(data.estimatedDeliveryDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
