'use strict';

angular.module('erpApp')
    .factory('Ordertype', function ($resource) {
        return $resource('api/ordertypes/:id', {}, {
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
