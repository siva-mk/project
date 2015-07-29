'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('inventoryItemType', {
                parent: 'entity',
                url: '/inventoryItemType',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.inventoryItemType.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/inventoryItemType/inventoryItemTypes.html',
                        controller: 'InventoryItemTypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('inventoryItemType');
                        return $translate.refresh();
                    }]
                }
            })
            .state('inventoryItemTypeDetail', {
                parent: 'entity',
                url: '/inventoryItemType/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.inventoryItemType.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/inventoryItemType/inventoryItemType-detail.html',
                        controller: 'InventoryItemTypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('inventoryItemType');
                        return $translate.refresh();
                    }]
                }
            });
    });
