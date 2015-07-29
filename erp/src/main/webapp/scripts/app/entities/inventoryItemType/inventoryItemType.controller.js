'use strict';

angular.module('erpApp')
    .controller('InventoryItemTypeController', function ($scope, InventoryItemType, ParseLinks) {
        $scope.inventoryItemTypes = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            InventoryItemType.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.inventoryItemTypes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            InventoryItemType.update($scope.inventoryItemType,
                function () {
                    $scope.loadAll();
                    $('#saveInventoryItemTypeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            InventoryItemType.get({id: id}, function(result) {
                $scope.inventoryItemType = result;
                $('#saveInventoryItemTypeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            InventoryItemType.get({id: id}, function(result) {
                $scope.inventoryItemType = result;
                $('#deleteInventoryItemTypeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InventoryItemType.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInventoryItemTypeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.inventoryItemType = {description: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
