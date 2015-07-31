'use strict';

angular.module('erpApp')
    .controller('OrderAttributeDetailController', function ($scope, $stateParams, OrderAttribute, OrderHeader) {
        $scope.orderAttribute = {};
        $scope.load = function (id) {
            OrderAttribute.get({id: id}, function(result) {
              $scope.orderAttribute = result;
            });
        };
        $scope.load($stateParams.id);
    });
