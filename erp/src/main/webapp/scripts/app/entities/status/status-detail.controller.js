'use strict';

angular.module('erpApp')
    .controller('StatusDetailController', function ($scope, $stateParams, Status) {
        $scope.status = {};
        $scope.load = function (id) {
            Status.get({id: id}, function(result) {
              $scope.status = result;
            });
        };
        $scope.load($stateParams.id);
    });
