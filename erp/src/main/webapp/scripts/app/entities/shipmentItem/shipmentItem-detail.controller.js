'use strict';

angular.module('erpApp')
    .controller('ShipmentItemDetailController', function ($scope, $stateParams, ShipmentItem, Shipment, Product) {
        $scope.shipmentItem = {};
        $scope.load = function (id) {
            ShipmentItem.get({id: id}, function(result) {
              $scope.shipmentItem = result;
            });
        };
        $scope.load($stateParams.id);
    });
