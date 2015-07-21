'use strict';

angular.module('erpApp')
    .controller('ProducttypeDetailController', function ($scope, $stateParams, Producttype) {
        $scope.producttype = {};
        $scope.load = function (id) {
            Producttype.get({id: id}, function(result) {
              $scope.producttype = result;
            });
        };
        $scope.load($stateParams.id);
    });
