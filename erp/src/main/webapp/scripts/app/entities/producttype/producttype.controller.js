'use strict';

angular.module('erpApp')
    .controller('ProducttypeController', function ($scope, Producttype, ParseLinks) {
        $scope.producttypes = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Producttype.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.producttypes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Producttype.update($scope.producttype,
                function () {
                    $scope.loadAll();
                    $('#saveProducttypeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Producttype.get({id: id}, function(result) {
                $scope.producttype = result;
                $('#saveProducttypeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Producttype.get({id: id}, function(result) {
                $scope.producttype = result;
                $('#deleteProducttypeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Producttype.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteProducttypeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.producttype = {description: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
