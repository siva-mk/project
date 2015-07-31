'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('orderTypeAttribute', {
                parent: 'entity',
                url: '/orderTypeAttribute',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.orderTypeAttribute.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orderTypeAttribute/orderTypeAttributes.html',
                        controller: 'OrderTypeAttributeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orderTypeAttribute');
                        return $translate.refresh();
                    }]
                }
            })
            .state('orderTypeAttributeDetail', {
                parent: 'entity',
                url: '/orderTypeAttribute/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.orderTypeAttribute.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orderTypeAttribute/orderTypeAttribute-detail.html',
                        controller: 'OrderTypeAttributeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orderTypeAttribute');
                        return $translate.refresh();
                    }]
                }
            });
    });
