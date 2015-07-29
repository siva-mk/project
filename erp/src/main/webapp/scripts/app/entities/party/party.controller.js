'use strict';

angular.module('erpApp')
    .controller('PartyController', function ($scope, Party, PartyTypes, ParseLinks) {
        $scope.partys = [];
        $scope.partytypess = PartyTypes.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Party.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.partys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Party.update($scope.party,
                function () {
                    $scope.loadAll();
                    $('#savePartyModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Party.get({id: id}, function(result) {
                $scope.party = result;
                $('#savePartyModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Party.get({id: id}, function(result) {
                $scope.party = result;
                $('#deletePartyConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Party.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePartyConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.party = {id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
