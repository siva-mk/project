'use strict';

angular.module('erpApp')
    .controller('InventoryItemStatusController', function ($scope, InventoryItemStatus, InventoryItem, Status, ParseLinks) {
        $scope.inventoryItemStatuss = [];
        $scope.inventoryitems = InventoryItem.query();
        $scope.statuss = Status.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            InventoryItemStatus.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.inventoryItemStatuss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            InventoryItemStatus.update($scope.inventoryItemStatus,
                function () {
                    $scope.loadAll();
                    $('#saveInventoryItemStatusModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            InventoryItemStatus.get({id: id}, function(result) {
                $scope.inventoryItemStatus = result;
                $('#saveInventoryItemStatusModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            InventoryItemStatus.get({id: id}, function(result) {
                $scope.inventoryItemStatus = result;
                $('#deleteInventoryItemStatusConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InventoryItemStatus.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInventoryItemStatusConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.inventoryItemStatus = {statusDateTime: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
