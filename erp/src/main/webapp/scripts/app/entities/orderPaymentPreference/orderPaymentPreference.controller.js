'use strict';

angular.module('erpApp')
    .controller('OrderPaymentPreferenceController', function ($scope, OrderPaymentPreference, OrderHeader, Status, ParseLinks) {
        $scope.orderPaymentPreferences = [];
        $scope.orderheaders = OrderHeader.query();
        $scope.statuss = Status.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            OrderPaymentPreference.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.orderPaymentPreferences = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            OrderPaymentPreference.update($scope.orderPaymentPreference,
                function () {
                    $scope.loadAll();
                    $('#saveOrderPaymentPreferenceModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            OrderPaymentPreference.get({id: id}, function(result) {
                $scope.orderPaymentPreference = result;
                $('#saveOrderPaymentPreferenceModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            OrderPaymentPreference.get({id: id}, function(result) {
                $scope.orderPaymentPreference = result;
                $('#deleteOrderPaymentPreferenceConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            OrderPaymentPreference.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOrderPaymentPreferenceConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.orderPaymentPreference = {paymentMethod_id: null, maxAmount: null, authCode: null, authMessage: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
