'use strict';

angular.module('erpApp')
    .factory('InventoryItemType', function ($resource) {
        return $resource('api/inventoryItemTypes/:id', {}, {
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
