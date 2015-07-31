'use strict';

angular.module('erpApp')
    .factory('InvoiceType', function ($resource) {
        return $resource('api/invoiceTypes/:id', {}, {
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
