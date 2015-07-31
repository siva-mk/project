'use strict';

angular.module('erpApp')
    .controller('InvoiceItemController', function ($scope, InvoiceItem, Invoice, InventoryItem, Product, ParseLinks) {
        $scope.invoiceItems = [];
        $scope.invoices = Invoice.query();
        $scope.inventoryitems = InventoryItem.query();
        $scope.products = Product.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            InvoiceItem.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.invoiceItems = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            InvoiceItem.update($scope.invoiceItem,
                function () {
                    $scope.loadAll();
                    $('#saveInvoiceItemModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            InvoiceItem.get({id: id}, function(result) {
                $scope.invoiceItem = result;
                $('#saveInvoiceItemModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            InvoiceItem.get({id: id}, function(result) {
                $scope.invoiceItem = result;
                $('#deleteInvoiceItemConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InvoiceItem.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInvoiceItemConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.invoiceItem = {invoiceItemSeq_id: null, invoiceItemType_id: null, productFeature_id: null, UOM_id: null, quantity: null, amount: null, description: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
