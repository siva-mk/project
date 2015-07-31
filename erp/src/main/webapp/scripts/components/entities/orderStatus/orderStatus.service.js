'use strict';

angular.module('erpApp')
    .factory('OrderStatus', function ($resource) {
        return $resource('api/orderStatuss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.statusDateTime = new Date(data.statusDateTime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
