'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('userGroupMembership', {
                parent: 'entity',
                url: '/userGroupMembership',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.userGroupMembership.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userGroupMembership/userGroupMemberships.html',
                        controller: 'UserGroupMembershipController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userGroupMembership');
                        return $translate.refresh();
                    }]
                }
            })
            .state('userGroupMembershipDetail', {
                parent: 'entity',
                url: '/userGroupMembership/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.userGroupMembership.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userGroupMembership/userGroupMembership-detail.html',
                        controller: 'UserGroupMembershipDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userGroupMembership');
                        return $translate.refresh();
                    }]
                }
            });
    });
