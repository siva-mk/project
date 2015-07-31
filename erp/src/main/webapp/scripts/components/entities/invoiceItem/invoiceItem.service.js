'use strict';

angular.module('erpApp')
    .factory('InvoiceItem', function ($resource) {
        return $resource('api/invoiceItems/:id', {}, {
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
