'use strict';

angular.module('erpApp')
    .controller('InvoiceAttributeController', function ($scope, InvoiceAttribute, Invoice, ParseLinks) {
        $scope.invoiceAttributes = [];
        $scope.invoices = Invoice.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            InvoiceAttribute.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.invoiceAttributes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            InvoiceAttribute.update($scope.invoiceAttribute,
                function () {
                    $scope.loadAll();
                    $('#saveInvoiceAttributeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            InvoiceAttribute.get({id: id}, function(result) {
                $scope.invoiceAttribute = result;
                $('#saveInvoiceAttributeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            InvoiceAttribute.get({id: id}, function(result) {
                $scope.invoiceAttribute = result;
                $('#deleteInvoiceAttributeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InvoiceAttribute.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInvoiceAttributeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.invoiceAttribute = {attributeName: null, attributeValue: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
