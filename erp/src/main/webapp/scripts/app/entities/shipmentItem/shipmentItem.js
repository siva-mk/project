'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('shipmentItem', {
                parent: 'entity',
                url: '/shipmentItem',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.shipmentItem.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shipmentItem/shipmentItems.html',
                        controller: 'ShipmentItemController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('shipmentItem');
                        return $translate.refresh();
                    }]
                }
            })
            .state('shipmentItemDetail', {
                parent: 'entity',
                url: '/shipmentItem/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.shipmentItem.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shipmentItem/shipmentItem-detail.html',
                        controller: 'ShipmentItemDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('shipmentItem');
                        return $translate.refresh();
                    }]
                }
            });
    });
