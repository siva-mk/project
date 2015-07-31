'use strict';

angular.module('erpApp')
    .controller('OrderItemBillingDetailController', function ($scope, $stateParams, OrderItemBilling, OrderHeader, OrderItem, Invoice, InvoiceItem) {
        $scope.orderItemBilling = {};
        $scope.load = function (id) {
            OrderItemBilling.get({id: id}, function(result) {
              $scope.orderItemBilling = result;
            });
        };
        $scope.load($stateParams.id);
    });
