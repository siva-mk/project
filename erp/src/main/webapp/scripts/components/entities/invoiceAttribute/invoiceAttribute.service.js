'use strict';

angular.module('erpApp')
    .factory('InvoiceAttribute', function ($resource) {
        return $resource('api/invoiceAttributes/:id', {}, {
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
