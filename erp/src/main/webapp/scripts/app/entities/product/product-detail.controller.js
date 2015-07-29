'use strict';

angular.module('erpApp')
    .controller('ProductDetailController', function ($scope, $stateParams, Product, Producttype, Productcategory) {
        $scope.product = {};
        $scope.load = function (id) {
            Product.get({id: id}, function(result) {
              $scope.product = result;
            });
        };
        $scope.load($stateParams.id);
    });
