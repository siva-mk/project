'use strict';

angular.module('erpApp')
    .controller('UserGroupRightsController', function ($scope, UserGroupRights, UserGroups, ParseLinks) {
        $scope.userGroupRightss = [];
        $scope.usergroupss = UserGroups.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            UserGroupRights.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.userGroupRightss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            UserGroupRights.update($scope.userGroupRights,
                function () {
                    $scope.loadAll();
                    $('#saveUserGroupRightsModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            UserGroupRights.get({id: id}, function(result) {
                $scope.userGroupRights = result;
                $('#saveUserGroupRightsModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            UserGroupRights.get({id: id}, function(result) {
                $scope.userGroupRights = result;
                $('#deleteUserGroupRightsConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            UserGroupRights.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUserGroupRightsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.userGroupRights = {id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
