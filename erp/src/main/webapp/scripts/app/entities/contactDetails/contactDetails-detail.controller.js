'use strict';

angular.module('erpApp')
    .controller('ContactDetailsDetailController', function ($scope, $stateParams, ContactDetails) {
        $scope.contactDetails = {};
        $scope.load = function (id) {
            ContactDetails.get({id: id}, function(result) {
              $scope.contactDetails = result;
            });
        };
        $scope.load($stateParams.id);
    });
