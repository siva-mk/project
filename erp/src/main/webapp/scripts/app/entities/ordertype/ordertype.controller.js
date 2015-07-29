'use strict';

angular.module('erpApp')
    .controller('OrdertypeController', function ($scope, Ordertype, ParseLinks) {
        $scope.ordertypes = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Ordertype.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.ordertypes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Ordertype.update($scope.ordertype,
                function () {
                    $scope.loadAll();
                    $('#saveOrdertypeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Ordertype.get({id: id}, function(result) {
                $scope.ordertype = result;
                $('#saveOrdertypeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Ordertype.get({id: id}, function(result) {
                $scope.ordertype = result;
                $('#deleteOrdertypeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Ordertype.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOrdertypeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.ordertype = {description: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
