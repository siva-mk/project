'use strict';

angular.module('erpApp')
    .factory('UserGroups', function ($resource) {
        return $resource('api/userGroupss/:id', {}, {
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
