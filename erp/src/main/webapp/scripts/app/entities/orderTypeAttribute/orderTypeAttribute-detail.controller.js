'use strict';

angular.module('erpApp')
    .controller('OrderTypeAttributeDetailController', function ($scope, $stateParams, OrderTypeAttribute, Ordertype) {
        $scope.orderTypeAttribute = {};
        $scope.load = function (id) {
            OrderTypeAttribute.get({id: id}, function(result) {
              $scope.orderTypeAttribute = result;
            });
        };
        $scope.load($stateParams.id);
    });
