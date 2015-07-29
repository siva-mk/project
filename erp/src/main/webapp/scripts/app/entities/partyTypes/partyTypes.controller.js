'use strict';

angular.module('erpApp')
    .controller('PartyTypesController', function ($scope, PartyTypes, ParseLinks) {
        $scope.partyTypess = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            PartyTypes.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.partyTypess = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            PartyTypes.update($scope.partyTypes,
                function () {
                    $scope.loadAll();
                    $('#savePartyTypesModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            PartyTypes.get({id: id}, function(result) {
                $scope.partyTypes = result;
                $('#savePartyTypesModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            PartyTypes.get({id: id}, function(result) {
                $scope.partyTypes = result;
                $('#deletePartyTypesConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PartyTypes.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePartyTypesConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.partyTypes = {partyType: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
