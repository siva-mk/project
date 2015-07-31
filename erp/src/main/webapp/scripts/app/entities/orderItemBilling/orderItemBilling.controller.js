'use strict';

angular.module('erpApp')
    .controller('OrderItemBillingController', function ($scope, OrderItemBilling, OrderHeader, OrderItem, Invoice, InvoiceItem, ParseLinks) {
        $scope.orderItemBillings = [];
        $scope.orderheaders = OrderHeader.query();
        $scope.orderitems = OrderItem.query();
        $scope.invoices = Invoice.query();
        $scope.invoiceitems = InvoiceItem.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            OrderItemBilling.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.orderItemBillings = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            OrderItemBilling.update($scope.orderItemBilling,
                function () {
                    $scope.loadAll();
                    $('#saveOrderItemBillingModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            OrderItemBilling.get({id: id}, function(result) {
                $scope.orderItemBilling = result;
                $('#saveOrderItemBillingModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            OrderItemBilling.get({id: id}, function(result) {
                $scope.orderItemBilling = result;
                $('#deleteOrderItemBillingConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            OrderItemBilling.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOrderItemBillingConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.orderItemBilling = {quantity: null, amount: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
