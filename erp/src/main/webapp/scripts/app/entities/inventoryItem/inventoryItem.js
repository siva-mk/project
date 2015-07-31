'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('inventoryItem', {
                parent: 'entity',
                url: '/inventoryItem',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.inventoryItem.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/inventoryItem/inventoryItems.html',
                        controller: 'InventoryItemController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('inventoryItem');
                        return $translate.refresh();
                    }]
                }
            })
            .state('inventoryItemDetail', {
                parent: 'entity',
                url: '/inventoryItem/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.inventoryItem.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/inventoryItem/inventoryItem-detail.html',
                        controller: 'InventoryItemDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('inventoryItem');
                        return $translate.refresh();
                    }]
                }
            });
    });
