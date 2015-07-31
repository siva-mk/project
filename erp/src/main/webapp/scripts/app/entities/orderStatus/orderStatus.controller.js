'use strict';

angular.module('erpApp')
    .controller('OrderStatusController', function ($scope, OrderStatus, Status, OrderHeader, ParseLinks) {
        $scope.orderStatuss = [];
        $scope.statuss = Status.query();
        $scope.orderheaders = OrderHeader.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            OrderStatus.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.orderStatuss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            OrderStatus.update($scope.orderStatus,
                function () {
                    $scope.loadAll();
                    $('#saveOrderStatusModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            OrderStatus.get({id: id}, function(result) {
                $scope.orderStatus = result;
                $('#saveOrderStatusModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            OrderStatus.get({id: id}, function(result) {
                $scope.orderStatus = result;
                $('#deleteOrderStatusConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            OrderStatus.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOrderStatusConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.orderStatus = {orderItem_id: null, statusDateTime: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
