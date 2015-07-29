'use strict';

angular.module('erpApp')
    .controller('UserGroupsController', function ($scope, UserGroups, ParseLinks) {
        $scope.userGroupss = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            UserGroups.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.userGroupss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            UserGroups.update($scope.userGroups,
                function () {
                    $scope.loadAll();
                    $('#saveUserGroupsModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            UserGroups.get({id: id}, function(result) {
                $scope.userGroups = result;
                $('#saveUserGroupsModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            UserGroups.get({id: id}, function(result) {
                $scope.userGroups = result;
                $('#deleteUserGroupsConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            UserGroups.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUserGroupsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.userGroups = {userGroupName: null, userGroupCode: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
