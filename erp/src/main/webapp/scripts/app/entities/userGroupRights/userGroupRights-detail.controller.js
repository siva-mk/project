'use strict';

angular.module('erpApp')
    .controller('UserGroupRightsDetailController', function ($scope, $stateParams, UserGroupRights, UserGroups) {
        $scope.userGroupRights = {};
        $scope.load = function (id) {
            UserGroupRights.get({id: id}, function(result) {
              $scope.userGroupRights = result;
            });
        };
        $scope.load($stateParams.id);
    });
