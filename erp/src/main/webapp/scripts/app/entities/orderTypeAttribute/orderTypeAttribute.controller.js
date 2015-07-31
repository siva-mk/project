'use strict';

angular.module('erpApp')
    .controller('OrderTypeAttributeController', function ($scope, OrderTypeAttribute, Ordertype, ParseLinks) {
        $scope.orderTypeAttributes = [];
        $scope.ordertypes = Ordertype.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            OrderTypeAttribute.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.orderTypeAttributes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            OrderTypeAttribute.update($scope.orderTypeAttribute,
                function () {
                    $scope.loadAll();
                    $('#saveOrderTypeAttributeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            OrderTypeAttribute.get({id: id}, function(result) {
                $scope.orderTypeAttribute = result;
                $('#saveOrderTypeAttributeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            OrderTypeAttribute.get({id: id}, function(result) {
                $scope.orderTypeAttribute = result;
                $('#deleteOrderTypeAttributeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            OrderTypeAttribute.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOrderTypeAttributeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.orderTypeAttribute = {attributeName: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
