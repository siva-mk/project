'use strict';

angular.module('erpApp')
    .factory('AppFeatures', function ($resource) {
        return $resource('api/appFeaturess/:id', {}, {
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
