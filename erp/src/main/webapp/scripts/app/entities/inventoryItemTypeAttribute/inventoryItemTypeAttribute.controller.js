'use strict';

angular.module('erpApp')
    .controller('InventoryItemTypeAttributeController', function ($scope, InventoryItemTypeAttribute, ParseLinks) {
        $scope.inventoryItemTypeAttributes = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            InventoryItemTypeAttribute.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.inventoryItemTypeAttributes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            InventoryItemTypeAttribute.update($scope.inventoryItemTypeAttribute,
                function () {
                    $scope.loadAll();
                    $('#saveInventoryItemTypeAttributeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            InventoryItemTypeAttribute.get({id: id}, function(result) {
                $scope.inventoryItemTypeAttribute = result;
                $('#saveInventoryItemTypeAttributeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            InventoryItemTypeAttribute.get({id: id}, function(result) {
                $scope.inventoryItemTypeAttribute = result;
                $('#deleteInventoryItemTypeAttributeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InventoryItemTypeAttribute.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInventoryItemTypeAttributeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.inventoryItemTypeAttribute = {attributeName: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
