'use strict';

angular.module('erpApp')
    .factory('OrderHeader', function ($resource) {
        return $resource('api/orderHeaders/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.orderDate = new Date(data.orderDate);
                    data.entryDate = new Date(data.entryDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
