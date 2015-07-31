'use strict';

angular.module('erpApp')
    .controller('InvoiceItemDetailController', function ($scope, $stateParams, InvoiceItem, Invoice, InventoryItem, Product) {
        $scope.invoiceItem = {};
        $scope.load = function (id) {
            InvoiceItem.get({id: id}, function(result) {
              $scope.invoiceItem = result;
            });
        };
        $scope.load($stateParams.id);
    });
