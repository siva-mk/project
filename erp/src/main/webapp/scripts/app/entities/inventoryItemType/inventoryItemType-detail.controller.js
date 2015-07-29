'use strict';

angular.module('erpApp')
    .controller('InventoryItemTypeDetailController', function ($scope, $stateParams, InventoryItemType) {
        $scope.inventoryItemType = {};
        $scope.load = function (id) {
            InventoryItemType.get({id: id}, function(result) {
              $scope.inventoryItemType = result;
            });
        };
        $scope.load($stateParams.id);
    });
