'use strict';

angular.module('erpApp')
    .controller('InventoryItemAttributeController', function ($scope, InventoryItemAttribute, InventoryItem, ParseLinks) {
        $scope.inventoryItemAttributes = [];
        $scope.inventoryitems = InventoryItem.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            InventoryItemAttribute.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.inventoryItemAttributes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            InventoryItemAttribute.update($scope.inventoryItemAttribute,
                function () {
                    $scope.loadAll();
                    $('#saveInventoryItemAttributeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            InventoryItemAttribute.get({id: id}, function(result) {
                $scope.inventoryItemAttribute = result;
                $('#saveInventoryItemAttributeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            InventoryItemAttribute.get({id: id}, function(result) {
                $scope.inventoryItemAttribute = result;
                $('#deleteInventoryItemAttributeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InventoryItemAttribute.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInventoryItemAttributeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.inventoryItemAttribute = {attributeName: null, attributeValue: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
