'use strict';

angular.module('erpApp')
    .controller('ShipmentItemBillingDetailController', function ($scope, $stateParams, ShipmentItemBilling, Shipment, ShipmentItem, Invoice, InvoiceItem) {
        $scope.shipmentItemBilling = {};
        $scope.load = function (id) {
            ShipmentItemBilling.get({id: id}, function(result) {
              $scope.shipmentItemBilling = result;
            });
        };
        $scope.load($stateParams.id);
    });
