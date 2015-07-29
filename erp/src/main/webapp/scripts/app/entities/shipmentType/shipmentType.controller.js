'use strict';

angular.module('erpApp')
    .controller('ShipmentTypeController', function ($scope, ShipmentType, ParseLinks) {
        $scope.shipmentTypes = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            ShipmentType.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.shipmentTypes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            ShipmentType.update($scope.shipmentType,
                function () {
                    $scope.loadAll();
                    $('#saveShipmentTypeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            ShipmentType.get({id: id}, function(result) {
                $scope.shipmentType = result;
                $('#saveShipmentTypeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            ShipmentType.get({id: id}, function(result) {
                $scope.shipmentType = result;
                $('#deleteShipmentTypeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ShipmentType.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteShipmentTypeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.shipmentType = {shipmenType: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
