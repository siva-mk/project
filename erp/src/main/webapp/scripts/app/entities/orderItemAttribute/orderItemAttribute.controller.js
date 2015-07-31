'use strict';

angular.module('erpApp')
    .controller('OrderItemAttributeController', function ($scope, OrderItemAttribute, OrderHeader, ParseLinks) {
        $scope.orderItemAttributes = [];
        $scope.orderheaders = OrderHeader.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            OrderItemAttribute.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.orderItemAttributes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            OrderItemAttribute.update($scope.orderItemAttribute,
                function () {
                    $scope.loadAll();
                    $('#saveOrderItemAttributeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            OrderItemAttribute.get({id: id}, function(result) {
                $scope.orderItemAttribute = result;
                $('#saveOrderItemAttributeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            OrderItemAttribute.get({id: id}, function(result) {
                $scope.orderItemAttribute = result;
                $('#deleteOrderItemAttributeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            OrderItemAttribute.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOrderItemAttributeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.orderItemAttribute = {attributeName: null, attributeValue: null, orderItemSeq_id: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
