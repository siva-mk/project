'use strict';

angular.module('erpApp')
    .controller('OrderHeaderDetailController', function ($scope, $stateParams, OrderHeader, Ordertype, Status) {
        $scope.orderHeader = {};
        $scope.load = function (id) {
            OrderHeader.get({id: id}, function(result) {
              $scope.orderHeader = result;
            });
        };
        $scope.load($stateParams.id);
    });
