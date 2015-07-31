'use strict';

angular.module('erpApp')
    .factory('InventoryItem', function ($resource) {
        return $resource('api/inventoryItems/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateTimeReceived = new Date(data.dateTimeReceived);
                    data.expiryDate = new Date(data.expiryDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
