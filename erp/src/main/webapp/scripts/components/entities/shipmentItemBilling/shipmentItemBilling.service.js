'use strict';

angular.module('erpApp')
    .factory('ShipmentItemBilling', function ($resource) {
        return $resource('api/shipmentItemBillings/:id', {}, {
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
