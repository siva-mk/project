'use strict';

angular.module('erpApp')
    .controller('ProductattributeController', function ($scope, Productattribute, Product, ParseLinks) {
        $scope.productattributes = [];
        $scope.products = Product.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Productattribute.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.productattributes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Productattribute.update($scope.productattribute,
                function () {
                    $scope.loadAll();
                    $('#saveProductattributeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Productattribute.get({id: id}, function(result) {
                $scope.productattribute = result;
                $('#saveProductattributeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Productattribute.get({id: id}, function(result) {
                $scope.productattribute = result;
                $('#deleteProductattributeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Productattribute.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProductattributeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.productattribute = {attributeName: null, attributeValue: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
