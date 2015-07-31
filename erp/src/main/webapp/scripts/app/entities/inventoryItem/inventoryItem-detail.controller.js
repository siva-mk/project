'use strict';

angular.module('erpApp')
    .controller('InventoryItemDetailController', function ($scope, $stateParams, InventoryItem, Product, InventoryItemType, Party, Status) {
        $scope.inventoryItem = {};
        $scope.load = function (id) {
            InventoryItem.get({id: id}, function(result) {
              $scope.inventoryItem = result;
            });
        };
        $scope.load($stateParams.id);
    });
