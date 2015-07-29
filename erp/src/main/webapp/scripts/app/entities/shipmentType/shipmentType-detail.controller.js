'use strict';

angular.module('erpApp')
    .controller('ShipmentTypeDetailController', function ($scope, $stateParams, ShipmentType) {
        $scope.shipmentType = {};
        $scope.load = function (id) {
            ShipmentType.get({id: id}, function(result) {
              $scope.shipmentType = result;
            });
        };
        $scope.load($stateParams.id);
    });
