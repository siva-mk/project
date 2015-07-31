'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('userGroupRights', {
                parent: 'entity',
                url: '/userGroupRights',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.userGroupRights.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userGroupRights/userGroupRightss.html',
                        controller: 'UserGroupRightsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userGroupRights');
                        return $translate.refresh();
                    }]
                }
            })
            .state('userGroupRightsDetail', {
                parent: 'entity',
                url: '/userGroupRights/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.userGroupRights.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userGroupRights/userGroupRights-detail.html',
                        controller: 'UserGroupRightsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userGroupRights');
                        return $translate.refresh();
                    }]
                }
            });
    });
