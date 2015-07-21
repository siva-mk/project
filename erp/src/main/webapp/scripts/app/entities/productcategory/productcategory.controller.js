'use strict';

angular.module('erpApp')
    .controller('ProductcategoryController', function ($scope, Productcategory, ParseLinks) {
        $scope.productcategorys = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Productcategory.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.productcategorys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Productcategory.update($scope.productcategory,
                function () {
                    $scope.loadAll();
                    $('#saveProductcategoryModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Productcategory.get({id: id}, function(result) {
                $scope.productcategory = result;
                $('#saveProductcategoryModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Productcategory.get({id: id}, function(result) {
                $scope.productcategory = result;
                $('#deleteProductcategoryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Productcategory.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProductcategoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.productcategory = {description: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
