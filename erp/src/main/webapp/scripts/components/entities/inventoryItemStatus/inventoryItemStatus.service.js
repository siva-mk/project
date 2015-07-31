'use strict';

angular.module('erpApp')
    .factory('InventoryItemStatus', function ($resource) {
        return $resource('api/inventoryItemStatuss/:id', {}, {
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
