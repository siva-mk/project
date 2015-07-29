'use strict';

angular.module('erpApp')
    .controller('UserGroupsDetailController', function ($scope, $stateParams, UserGroups) {
        $scope.userGroups = {};
        $scope.load = function (id) {
            UserGroups.get({id: id}, function(result) {
              $scope.userGroups = result;
            });
        };
        $scope.load($stateParams.id);
    });
