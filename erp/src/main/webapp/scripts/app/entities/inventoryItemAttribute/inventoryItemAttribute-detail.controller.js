'use strict';

angular.module('erpApp')
    .controller('InventoryItemAttributeDetailController', function ($scope, $stateParams, InventoryItemAttribute, InventoryItem) {
        $scope.inventoryItemAttribute = {};
        $scope.load = function (id) {
            InventoryItemAttribute.get({id: id}, function(result) {
              $scope.inventoryItemAttribute = result;
            });
        };
        $scope.load($stateParams.id);
    });
