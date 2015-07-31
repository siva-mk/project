'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('orderAttribute', {
                parent: 'entity',
                url: '/orderAttribute',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.orderAttribute.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orderAttribute/orderAttributes.html',
                        controller: 'OrderAttributeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orderAttribute');
                        return $translate.refresh();
                    }]
                }
            })
            .state('orderAttributeDetail', {
                parent: 'entity',
                url: '/orderAttribute/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.orderAttribute.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orderAttribute/orderAttribute-detail.html',
                        controller: 'OrderAttributeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orderAttribute');
                        return $translate.refresh();
                    }]
                }
            });
    });
