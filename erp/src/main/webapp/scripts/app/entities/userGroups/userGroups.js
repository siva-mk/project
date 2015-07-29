'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('userGroups', {
                parent: 'entity',
                url: '/userGroups',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.userGroups.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userGroups/userGroupss.html',
                        controller: 'UserGroupsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userGroups');
                        return $translate.refresh();
                    }]
                }
            })
            .state('userGroupsDetail', {
                parent: 'entity',
                url: '/userGroups/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.userGroups.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userGroups/userGroups-detail.html',
                        controller: 'UserGroupsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userGroups');
                        return $translate.refresh();
                    }]
                }
            });
    });
