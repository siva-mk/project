'use strict';

angular.module('erpApp')
    .controller('PartyDetailController', function ($scope, $stateParams, Party, PartyTypes) {
        $scope.party = {};
        $scope.load = function (id) {
            Party.get({id: id}, function(result) {
              $scope.party = result;
            });
        };
        $scope.load($stateParams.id);
    });
