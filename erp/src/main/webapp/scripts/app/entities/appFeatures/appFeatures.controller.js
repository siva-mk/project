'use strict';

angular.module('erpApp')
    .controller('AppFeaturesController', function ($scope, AppFeatures, ParseLinks) {
        $scope.appFeaturess = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            AppFeatures.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.appFeaturess = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            AppFeatures.update($scope.appFeatures,
                function () {
                    $scope.loadAll();
                    $('#saveAppFeaturesModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            AppFeatures.get({id: id}, function(result) {
                $scope.appFeatures = result;
                $('#saveAppFeaturesModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            AppFeatures.get({id: id}, function(result) {
                $scope.appFeatures = result;
                $('#deleteAppFeaturesConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AppFeatures.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAppFeaturesConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.appFeatures = {description: null, pageDetails: null, active: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
