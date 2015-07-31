'use strict';

angular.module('erpApp')
    .controller('UserGroupMembershipController', function ($scope, UserGroupMembership, UserGroups, ParseLinks) {
        $scope.userGroupMemberships = [];
        $scope.usergroupss = UserGroups.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            UserGroupMembership.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.userGroupMemberships = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            UserGroupMembership.update($scope.userGroupMembership,
                function () {
                    $scope.loadAll();
                    $('#saveUserGroupMembershipModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            UserGroupMembership.get({id: id}, function(result) {
                $scope.userGroupMembership = result;
                $('#saveUserGroupMembershipModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            UserGroupMembership.get({id: id}, function(result) {
                $scope.userGroupMembership = result;
                $('#deleteUserGroupMembershipConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            UserGroupMembership.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUserGroupMembershipConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.userGroupMembership = {user_id: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
