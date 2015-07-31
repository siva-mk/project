'use strict';

angular.module('erpApp')
    .controller('OrderItemDetailController', function ($scope, $stateParams, OrderItem, OrderHeader, Product, Status) {
        $scope.orderItem = {};
        $scope.load = function (id) {
            OrderItem.get({id: id}, function(result) {
              $scope.orderItem = result;
            });
        };
        $scope.load($stateParams.id);
    });
