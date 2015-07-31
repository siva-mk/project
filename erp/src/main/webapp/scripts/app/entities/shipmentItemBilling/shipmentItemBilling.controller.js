'use strict';

angular.module('erpApp')
    .controller('ShipmentItemBillingController', function ($scope, ShipmentItemBilling, Shipment, ShipmentItem, Invoice, InvoiceItem, ParseLinks) {
        $scope.shipmentItemBillings = [];
        $scope.shipments = Shipment.query();
        $scope.shipmentitems = ShipmentItem.query();
        $scope.invoices = Invoice.query();
        $scope.invoiceitems = InvoiceItem.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            ShipmentItemBilling.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.shipmentItemBillings = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            ShipmentItemBilling.update($scope.shipmentItemBilling,
                function () {
                    $scope.loadAll();
                    $('#saveShipmentItemBillingModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            ShipmentItemBilling.get({id: id}, function(result) {
                $scope.shipmentItemBilling = result;
                $('#saveShipmentItemBillingModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            ShipmentItemBilling.get({id: id}, function(result) {
                $scope.shipmentItemBilling = result;
                $('#deleteShipmentItemBillingConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ShipmentItemBilling.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteShipmentItemBillingConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.shipmentItemBilling = {id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
