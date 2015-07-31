'use strict';

angular.module('erpApp')
    .controller('InvoiceDetailController', function ($scope, $stateParams, Invoice, InvoiceType, Party, Status) {
        $scope.invoice = {};
        $scope.load = function (id) {
            Invoice.get({id: id}, function(result) {
              $scope.invoice = result;
            });
        };
        $scope.load($stateParams.id);
    });
