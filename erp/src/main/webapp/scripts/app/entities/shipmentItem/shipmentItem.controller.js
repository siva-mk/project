'use strict';

angular.module('erpApp')
    .controller('ShipmentItemController', function ($scope, ShipmentItem, Shipment, Product, ParseLinks) {
        $scope.shipmentItems = [];
        $scope.shipments = Shipment.query();
        $scope.products = Product.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            ShipmentItem.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.shipmentItems = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            ShipmentItem.update($scope.shipmentItem,
                function () {
                    $scope.loadAll();
                    $('#saveShipmentItemModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            ShipmentItem.get({id: id}, function(result) {
                $scope.shipmentItem = result;
                $('#saveShipmentItemModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            ShipmentItem.get({id: id}, function(result) {
                $scope.shipmentItem = result;
                $('#deleteShipmentItemConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ShipmentItem.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteShipmentItemConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.shipmentItem = {quantity: null, shipmentContentDesc: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
