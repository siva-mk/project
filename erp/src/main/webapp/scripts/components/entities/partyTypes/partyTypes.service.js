'use strict';

angular.module('erpApp')
    .factory('PartyTypes', function ($resource) {
        return $resource('api/partyTypess/:id', {}, {
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
