'use strict';

angular.module('erpApp')
    .controller('OrderAttributeController', function ($scope, OrderAttribute, OrderHeader, ParseLinks) {
        $scope.orderAttributes = [];
        $scope.orderheaders = OrderHeader.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            OrderAttribute.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.orderAttributes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            OrderAttribute.update($scope.orderAttribute,
                function () {
                    $scope.loadAll();
                    $('#saveOrderAttributeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            OrderAttribute.get({id: id}, function(result) {
                $scope.orderAttribute = result;
                $('#saveOrderAttributeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            OrderAttribute.get({id: id}, function(result) {
                $scope.orderAttribute = result;
                $('#deleteOrderAttributeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            OrderAttribute.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOrderAttributeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.orderAttribute = {attributeName: null, attributeValue: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
