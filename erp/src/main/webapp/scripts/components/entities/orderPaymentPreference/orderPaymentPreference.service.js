'use strict';

angular.module('erpApp')
    .factory('OrderPaymentPreference', function ($resource) {
        return $resource('api/orderPaymentPreferences/:id', {}, {
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
