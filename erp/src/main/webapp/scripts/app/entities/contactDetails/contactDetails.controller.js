'use strict';

angular.module('erpApp')
    .controller('ContactDetailsController', function ($scope, ContactDetails, ParseLinks) {
        $scope.contactDetailss = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            ContactDetails.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.contactDetailss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            ContactDetails.update($scope.contactDetails,
                function () {
                    $scope.loadAll();
                    $('#saveContactDetailsModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            ContactDetails.get({id: id}, function(result) {
                $scope.contactDetails = result;
                $('#saveContactDetailsModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            ContactDetails.get({id: id}, function(result) {
                $scope.contactDetails = result;
                $('#deleteContactDetailsConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ContactDetails.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteContactDetailsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.contactDetails = {user: null, address1: null, address2: null, active: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
