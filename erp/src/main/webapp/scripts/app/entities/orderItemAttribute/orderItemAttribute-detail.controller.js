'use strict';

angular.module('erpApp')
    .controller('OrderItemAttributeDetailController', function ($scope, $stateParams, OrderItemAttribute, OrderHeader) {
        $scope.orderItemAttribute = {};
        $scope.load = function (id) {
            OrderItemAttribute.get({id: id}, function(result) {
              $scope.orderItemAttribute = result;
            });
        };
        $scope.load($stateParams.id);
    });
