'use strict';

angular.module('erpApp')
    .factory('Product', function ($resource) {
        return $resource('api/products/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.introductionDate = new Date(data.introductionDate);
                    data.salesDiscontinuationDate = new Date(data.salesDiscontinuationDate);
                    data.supportDiscontinuationDate = new Date(data.supportDiscontinuationDate);
                    data.createdOn = new Date(data.createdOn);
                    data.modifiedOn = new Date(data.modifiedOn);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
