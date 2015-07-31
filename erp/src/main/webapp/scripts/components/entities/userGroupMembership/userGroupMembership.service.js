'use strict';

angular.module('erpApp')
    .factory('UserGroupMembership', function ($resource) {
        return $resource('api/userGroupMemberships/:id', {}, {
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
