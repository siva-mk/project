'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('userType', {
                parent: 'entity',
                url: '/userType',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.userType.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userType/userTypes.html',
                        controller: 'UserTypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userType');
                        return $translate.refresh();
                    }]
                }
            })
            .state('userTypeDetail', {
                parent: 'entity',
                url: '/userType/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.userType.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/userType/userType-detail.html',
                        controller: 'UserTypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('userType');
                        return $translate.refresh();
                    }]
                }
            });
    });
