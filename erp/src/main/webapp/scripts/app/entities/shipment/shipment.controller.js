'use strict';

angular.module('erpApp')
    .controller('ShipmentController', function ($scope, Shipment, ShipmentType, Party, ParseLinks) {
        $scope.shipments = [];
        $scope.shipmenttypes = ShipmentType.query();
        $scope.partys = Party.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Shipment.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.shipments = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Shipment.update($scope.shipment,
                function () {
                    $scope.loadAll();
                    $('#saveShipmentModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Shipment.get({id: id}, function(result) {
                $scope.shipment = result;
                $('#saveShipmentModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Shipment.get({id: id}, function(result) {
                $scope.shipment = result;
                $('#deleteShipmentConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Shipment.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteShipmentConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.shipment = {estimateShipDate: null, estimatedReadyDate: null, estimatedArrivalDate: null, estimatedShipCost: null, actualShipCost: null, latestCancelDate: null, handlingInstruction: null, lastUpdated: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
