'use strict';

angular.module('erpApp')
    .controller('OrderStatusDetailController', function ($scope, $stateParams, OrderStatus, Status, OrderHeader) {
        $scope.orderStatus = {};
        $scope.load = function (id) {
            OrderStatus.get({id: id}, function(result) {
              $scope.orderStatus = result;
            });
        };
        $scope.load($stateParams.id);
    });
