'use strict';

angular.module('erpApp')
    .factory('Shipment', function ($resource) {
        return $resource('api/shipments/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.estimateShipDate = new Date(data.estimateShipDate);
                    data.estimatedReadyDate = new Date(data.estimatedReadyDate);
                    data.estimatedArrivalDate = new Date(data.estimatedArrivalDate);
                    data.latestCancelDate = new Date(data.latestCancelDate);
                    data.lastUpdated = new Date(data.lastUpdated);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
