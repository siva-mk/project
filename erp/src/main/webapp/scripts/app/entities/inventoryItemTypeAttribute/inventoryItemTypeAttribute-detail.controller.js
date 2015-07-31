'use strict';

angular.module('erpApp')
    .controller('InventoryItemTypeAttributeDetailController', function ($scope, $stateParams, InventoryItemTypeAttribute) {
        $scope.inventoryItemTypeAttribute = {};
        $scope.load = function (id) {
            InventoryItemTypeAttribute.get({id: id}, function(result) {
              $scope.inventoryItemTypeAttribute = result;
            });
        };
        $scope.load($stateParams.id);
    });
