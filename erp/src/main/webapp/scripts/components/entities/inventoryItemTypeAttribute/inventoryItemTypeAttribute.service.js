'use strict';

angular.module('erpApp')
    .factory('InventoryItemTypeAttribute', function ($resource) {
        return $resource('api/inventoryItemTypeAttributes/:id', {}, {
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
