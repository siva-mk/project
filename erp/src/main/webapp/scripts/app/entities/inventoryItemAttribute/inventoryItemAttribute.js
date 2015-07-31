'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('inventoryItemAttribute', {
                parent: 'entity',
                url: '/inventoryItemAttribute',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.inventoryItemAttribute.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/inventoryItemAttribute/inventoryItemAttributes.html',
                        controller: 'InventoryItemAttributeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('inventoryItemAttribute');
                        return $translate.refresh();
                    }]
                }
            })
            .state('inventoryItemAttributeDetail', {
                parent: 'entity',
                url: '/inventoryItemAttribute/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.inventoryItemAttribute.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/inventoryItemAttribute/inventoryItemAttribute-detail.html',
                        controller: 'InventoryItemAttributeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('inventoryItemAttribute');
                        return $translate.refresh();
                    }]
                }
            });
    });
