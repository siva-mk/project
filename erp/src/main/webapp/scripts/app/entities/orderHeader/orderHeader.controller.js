'use strict';

angular.module('erpApp')
    .controller('OrderHeaderController', function ($scope, OrderHeader, Ordertype, Status, ParseLinks) {
        $scope.orderHeaders = [];
        $scope.ordertypes = Ordertype.query();
        $scope.statuss = Status.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            OrderHeader.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.orderHeaders = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            OrderHeader.update($scope.orderHeader,
                function () {
                    $scope.loadAll();
                    $('#saveOrderHeaderModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            OrderHeader.get({id: id}, function(result) {
                $scope.orderHeader = result;
                $('#saveOrderHeaderModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            OrderHeader.get({id: id}, function(result) {
                $scope.orderHeader = result;
                $('#deleteOrderHeaderConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            OrderHeader.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOrderHeaderConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.orderHeader = {orderDate: null, entryDate: null, visit_id: null, createdBy: null, syncStatus_id: null, billingAccount_id: null, originFacility_id: null, grandTotal: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
