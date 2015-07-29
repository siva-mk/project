'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('shipment', {
                parent: 'entity',
                url: '/shipment',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.shipment.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shipment/shipments.html',
                        controller: 'ShipmentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('shipment');
                        return $translate.refresh();
                    }]
                }
            })
            .state('shipmentDetail', {
                parent: 'entity',
                url: '/shipment/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.shipment.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shipment/shipment-detail.html',
                        controller: 'ShipmentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('shipment');
                        return $translate.refresh();
                    }]
                }
            });
    });
