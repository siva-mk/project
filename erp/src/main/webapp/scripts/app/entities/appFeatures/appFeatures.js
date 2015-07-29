'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('appFeatures', {
                parent: 'entity',
                url: '/appFeatures',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.appFeatures.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/appFeatures/appFeaturess.html',
                        controller: 'AppFeaturesController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('appFeatures');
                        return $translate.refresh();
                    }]
                }
            })
            .state('appFeaturesDetail', {
                parent: 'entity',
                url: '/appFeatures/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.appFeatures.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/appFeatures/appFeatures-detail.html',
                        controller: 'AppFeaturesDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('appFeatures');
                        return $translate.refresh();
                    }]
                }
            });
    });
