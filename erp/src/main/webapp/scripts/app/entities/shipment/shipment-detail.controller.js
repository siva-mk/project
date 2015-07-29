'use strict';

angular.module('erpApp')
    .controller('ShipmentDetailController', function ($scope, $stateParams, Shipment, ShipmentType, Party) {
        $scope.shipment = {};
        $scope.load = function (id) {
            Shipment.get({id: id}, function(result) {
              $scope.shipment = result;
            });
        };
        $scope.load($stateParams.id);
    });
