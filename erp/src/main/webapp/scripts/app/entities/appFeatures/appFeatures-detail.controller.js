'use strict';

angular.module('erpApp')
    .controller('AppFeaturesDetailController', function ($scope, $stateParams, AppFeatures) {
        $scope.appFeatures = {};
        $scope.load = function (id) {
            AppFeatures.get({id: id}, function(result) {
              $scope.appFeatures = result;
            });
        };
        $scope.load($stateParams.id);
    });
