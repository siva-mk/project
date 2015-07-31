'use strict';

angular.module('erpApp')
    .controller('UserRightsDetailController', function ($scope, $stateParams, UserRights, AppFeatures) {
        $scope.userRights = {};
        $scope.load = function (id) {
            UserRights.get({id: id}, function(result) {
              $scope.userRights = result;
            });
        };
        $scope.load($stateParams.id);
    });
