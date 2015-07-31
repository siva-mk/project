'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('inventoryItemTypeAttribute', {
                parent: 'entity',
                url: '/inventoryItemTypeAttribute',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.inventoryItemTypeAttribute.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/inventoryItemTypeAttribute/inventoryItemTypeAttributes.html',
                        controller: 'InventoryItemTypeAttributeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('inventoryItemTypeAttribute');
                        return $translate.refresh();
                    }]
                }
            })
            .state('inventoryItemTypeAttributeDetail', {
                parent: 'entity',
                url: '/inventoryItemTypeAttribute/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.inventoryItemTypeAttribute.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/inventoryItemTypeAttribute/inventoryItemTypeAttribute-detail.html',
                        controller: 'InventoryItemTypeAttributeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('inventoryItemTypeAttribute');
                        return $translate.refresh();
                    }]
                }
            });
    });
