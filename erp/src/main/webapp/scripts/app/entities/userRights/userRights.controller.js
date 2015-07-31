'use strict';

angular.module('erpApp')
    .controller('UserRightsController', function ($scope, UserRights, AppFeatures, ParseLinks) {
        $scope.userRightss = [];
        $scope.appfeaturess = AppFeatures.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            UserRights.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.userRightss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            UserRights.update($scope.userRights,
                function () {
                    $scope.loadAll();
                    $('#saveUserRightsModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            UserRights.get({id: id}, function(result) {
                $scope.userRights = result;
                $('#saveUserRightsModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            UserRights.get({id: id}, function(result) {
                $scope.userRights = result;
                $('#deleteUserRightsConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            UserRights.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUserRightsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.userRights = {user_id: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
