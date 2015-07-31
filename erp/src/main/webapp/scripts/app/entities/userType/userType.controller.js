'use strict';

angular.module('erpApp')
    .controller('UserTypeController', function ($scope, UserType, ParseLinks) {
        $scope.userTypes = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            UserType.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.userTypes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            UserType.update($scope.userType,
                function () {
                    $scope.loadAll();
                    $('#saveUserTypeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            UserType.get({id: id}, function(result) {
                $scope.userType = result;
                $('#saveUserTypeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            UserType.get({id: id}, function(result) {
                $scope.userType = result;
                $('#deleteUserTypeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            UserType.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUserTypeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.userType = {userTypeName: null, userTypeCode: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
