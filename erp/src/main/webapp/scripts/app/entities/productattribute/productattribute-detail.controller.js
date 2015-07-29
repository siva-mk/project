'use strict';

angular.module('erpApp')
    .controller('ProductattributeDetailController', function ($scope, $stateParams, Productattribute, Product) {
        $scope.productattribute = {};
        $scope.load = function (id) {
            Productattribute.get({id: id}, function(result) {
              $scope.productattribute = result;
            });
        };
        $scope.load($stateParams.id);
    });
