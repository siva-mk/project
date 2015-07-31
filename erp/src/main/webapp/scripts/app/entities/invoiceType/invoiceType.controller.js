'use strict';

angular.module('erpApp')
    .controller('InvoiceTypeController', function ($scope, InvoiceType, ParseLinks) {
        $scope.invoiceTypes = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            InvoiceType.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.invoiceTypes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            InvoiceType.update($scope.invoiceType,
                function () {
                    $scope.loadAll();
                    $('#saveInvoiceTypeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            InvoiceType.get({id: id}, function(result) {
                $scope.invoiceType = result;
                $('#saveInvoiceTypeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            InvoiceType.get({id: id}, function(result) {
                $scope.invoiceType = result;
                $('#deleteInvoiceTypeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InvoiceType.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInvoiceTypeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.invoiceType = {description: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
