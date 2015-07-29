'use strict';

angular.module('erpApp')
    .factory('Productattribute', function ($resource) {
        return $resource('api/productattributes/:id', {}, {
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
