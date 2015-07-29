'use strict';

angular.module('erpApp')
    .controller('ProductController', function ($scope, Product, Producttype, Productcategory, ParseLinks) {
        $scope.products = [];
        $scope.producttypes = Producttype.query();
        $scope.productcategorys = Productcategory.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Product.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.products = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Product.update($scope.product,
                function () {
                    $scope.loadAll();
                    $('#saveProductModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Product.get({id: id}, function(result) {
                $scope.product = result;
                $('#saveProductModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Product.get({id: id}, function(result) {
                $scope.product = result;
                $('#deleteProductConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Product.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProductConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.product = {manufacturerPartyId: null, introductionDate: null, salesDiscontinuationDate: null, supportDiscontinuationDate: null, productName: null, internalName: null, brandName: null, comments: null, description: null, inventoryMessage: null, requiredInventory: null, smallImageURL: null, largeImaleURL: null, quantityUOMId: null, quantityIncluded: null, piecesIncluded: null, weightUOMId: null, weight: null, taxable: null, createdOn: null, createdBy: null, modifiedOn: null, modifiedBy: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
