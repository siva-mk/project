'use strict';

angular.module('erpApp')
    .controller('InvoiceController', function ($scope, Invoice, InvoiceType, Party, Status, ParseLinks) {
        $scope.invoices = [];
        $scope.invoicetypes = InvoiceType.query();
        $scope.partys = Party.query();
        $scope.statuss = Status.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Invoice.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.invoices = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Invoice.update($scope.invoice,
                function () {
                    $scope.loadAll();
                    $('#saveInvoiceModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Invoice.get({id: id}, function(result) {
                $scope.invoice = result;
                $('#saveInvoiceModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Invoice.get({id: id}, function(result) {
                $scope.invoice = result;
                $('#deleteInvoiceConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Invoice.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInvoiceConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.invoice = {billingAccount_id: null, invoiceDate: null, invoiceMessage: null, description: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
