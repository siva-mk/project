'use strict';

angular.module('erpApp')
    .controller('UserGroupMembershipDetailController', function ($scope, $stateParams, UserGroupMembership, UserGroups) {
        $scope.userGroupMembership = {};
        $scope.load = function (id) {
            UserGroupMembership.get({id: id}, function(result) {
              $scope.userGroupMembership = result;
            });
        };
        $scope.load($stateParams.id);
    });
