'use strict';

angular.module('erpApp')
    .controller('InvoiceTypeDetailController', function ($scope, $stateParams, InvoiceType) {
        $scope.invoiceType = {};
        $scope.load = function (id) {
            InvoiceType.get({id: id}, function(result) {
              $scope.invoiceType = result;
            });
        };
        $scope.load($stateParams.id);
    });
