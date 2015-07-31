'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('userRights', {
                parent: 'entity',
                url: '/userRights',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.userRights.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userRights/userRightss.html',
                        controller: 'UserRightsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userRights');
                        return $translate.refresh();
                    }]
                }
            })
            .state('userRightsDetail', {
                parent: 'entity',
                url: '/userRights/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.userRights.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userRights/userRights-detail.html',
                        controller: 'UserRightsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userRights');
                        return $translate.refresh();
                    }]
                }
            });
    });
