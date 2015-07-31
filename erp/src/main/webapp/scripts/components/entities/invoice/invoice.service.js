'use strict';

angular.module('erpApp')
    .factory('Invoice', function ($resource) {
        return $resource('api/invoices/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.invoiceDate = new Date(data.invoiceDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
