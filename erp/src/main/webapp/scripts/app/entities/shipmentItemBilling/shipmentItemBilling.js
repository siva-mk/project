'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('shipmentItemBilling', {
                parent: 'entity',
                url: '/shipmentItemBilling',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.shipmentItemBilling.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shipmentItemBilling/shipmentItemBillings.html',
                        controller: 'ShipmentItemBillingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('shipmentItemBilling');
                        return $translate.refresh();
                    }]
                }
            })
            .state('shipmentItemBillingDetail', {
                parent: 'entity',
                url: '/shipmentItemBilling/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.shipmentItemBilling.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shipmentItemBilling/shipmentItemBilling-detail.html',
                        controller: 'ShipmentItemBillingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('shipmentItemBilling');
                        return $translate.refresh();
                    }]
                }
            });
    });
