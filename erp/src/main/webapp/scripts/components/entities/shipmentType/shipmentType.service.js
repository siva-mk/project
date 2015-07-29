'use strict';

angular.module('erpApp')
    .factory('ShipmentType', function ($resource) {
        return $resource('api/shipmentTypes/:id', {}, {
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
