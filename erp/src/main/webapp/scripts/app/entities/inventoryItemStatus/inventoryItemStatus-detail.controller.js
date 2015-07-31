'use strict';

angular.module('erpApp')
    .controller('InventoryItemStatusDetailController', function ($scope, $stateParams, InventoryItemStatus, InventoryItem, Status) {
        $scope.inventoryItemStatus = {};
        $scope.load = function (id) {
            InventoryItemStatus.get({id: id}, function(result) {
              $scope.inventoryItemStatus = result;
            });
        };
        $scope.load($stateParams.id);
    });
