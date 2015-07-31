'use strict';

angular.module('erpApp')
    .controller('OrderPaymentPreferenceDetailController', function ($scope, $stateParams, OrderPaymentPreference, OrderHeader, Status) {
        $scope.orderPaymentPreference = {};
        $scope.load = function (id) {
            OrderPaymentPreference.get({id: id}, function(result) {
              $scope.orderPaymentPreference = result;
            });
        };
        $scope.load($stateParams.id);
    });
