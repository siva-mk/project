'use strict';

angular.module('erpApp')
    .controller('InventoryItemController', function ($scope, InventoryItem, Product, InventoryItemType, Party, Status, ParseLinks) {
        $scope.inventoryItems = [];
        $scope.products = Product.query();
        $scope.inventoryitemtypes = InventoryItemType.query();
        $scope.partys = Party.query();
        $scope.statuss = Status.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            InventoryItem.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.inventoryItems = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            InventoryItem.update($scope.inventoryItem,
                function () {
                    $scope.loadAll();
                    $('#saveInventoryItemModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            InventoryItem.get({id: id}, function(result) {
                $scope.inventoryItem = result;
                $('#saveInventoryItemModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            InventoryItem.get({id: id}, function(result) {
                $scope.inventoryItem = result;
                $('#deleteInventoryItemConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InventoryItem.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInventoryItemConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.inventoryItem = {dateTimeReceived: null, expiryDate: null, facility_id: null, container_id: null, lot_id: null, UOM_id: null, comments: null, quantityOnHand: null, availableToPromise: null, serialNumber: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
