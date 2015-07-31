'use strict';

angular.module('erpApp')
    .controller('InvoiceAttributeDetailController', function ($scope, $stateParams, InvoiceAttribute, Invoice) {
        $scope.invoiceAttribute = {};
        $scope.load = function (id) {
            InvoiceAttribute.get({id: id}, function(result) {
              $scope.invoiceAttribute = result;
            });
        };
        $scope.load($stateParams.id);
    });
