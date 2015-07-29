'use strict';

angular.module('erpApp')
    .controller('PartyTypesDetailController', function ($scope, $stateParams, PartyTypes) {
        $scope.partyTypes = {};
        $scope.load = function (id) {
            PartyTypes.get({id: id}, function(result) {
              $scope.partyTypes = result;
            });
        };
        $scope.load($stateParams.id);
    });
