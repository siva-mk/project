'use strict';

angular.module('erpApp')
    .controller('OrderItemController', function ($scope, OrderItem, OrderHeader, Product, Status, ParseLinks) {
        $scope.orderItems = [];
        $scope.orderheaders = OrderHeader.query();
        $scope.products = Product.query();
        $scope.statuss = Status.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            OrderItem.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.orderItems = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            OrderItem.update($scope.orderItem,
                function () {
                    $scope.loadAll();
                    $('#saveOrderItemModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            OrderItem.get({id: id}, function(result) {
                $scope.orderItem = result;
                $('#saveOrderItemModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            OrderItem.get({id: id}, function(result) {
                $scope.orderItem = result;
                $('#deleteOrderItemConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            OrderItem.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOrderItemConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.orderItem = {budget_id: null, budgetItem_id: null, productFeadute_id: null, quote_id: null, quoteItem_id: null, quantity: null, unitPrice: null, unitListPrice: null, unitAverageCost: null, estimatedDeliveryDate: null, itemDescription: null, correspondingPo_id: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
