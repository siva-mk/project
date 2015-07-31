'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('orderItemAttribute', {
                parent: 'entity',
                url: '/orderItemAttribute',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.orderItemAttribute.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orderItemAttribute/orderItemAttributes.html',
                        controller: 'OrderItemAttributeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orderItemAttribute');
                        return $translate.refresh();
                    }]
                }
            })
            .state('orderItemAttributeDetail', {
                parent: 'entity',
                url: '/orderItemAttribute/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.orderItemAttribute.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orderItemAttribute/orderItemAttribute-detail.html',
                        controller: 'OrderItemAttributeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orderItemAttribute');
                        return $translate.refresh();
                    }]
                }
            });
    });
