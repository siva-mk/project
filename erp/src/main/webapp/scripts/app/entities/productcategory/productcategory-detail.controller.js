'use strict';

angular.module('erpApp')
    .controller('ProductcategoryDetailController', function ($scope, $stateParams, Productcategory) {
        $scope.productcategory = {};
        $scope.load = function (id) {
            Productcategory.get({id: id}, function(result) {
              $scope.productcategory = result;
            });
        };
        $scope.load($stateParams.id);
    });
