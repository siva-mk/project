'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('inventoryItemStatus', {
                parent: 'entity',
                url: '/inventoryItemStatus',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.inventoryItemStatus.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/inventoryItemStatus/inventoryItemStatuss.html',
                        controller: 'InventoryItemStatusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('inventoryItemStatus');
                        return $translate.refresh();
                    }]
                }
            })
            .state('inventoryItemStatusDetail', {
                parent: 'entity',
                url: '/inventoryItemStatus/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.inventoryItemStatus.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/inventoryItemStatus/inventoryItemStatus-detail.html',
                        controller: 'InventoryItemStatusDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('inventoryItemStatus');
                        return $translate.refresh();
                    }]
                }
            });
    });
