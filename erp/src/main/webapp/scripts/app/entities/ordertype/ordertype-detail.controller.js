'use strict';

angular.module('erpApp')
    .controller('OrdertypeDetailController', function ($scope, $stateParams, Ordertype) {
        $scope.ordertype = {};
        $scope.load = function (id) {
            Ordertype.get({id: id}, function(result) {
              $scope.ordertype = result;
            });
        };
        $scope.load($stateParams.id);
    });
