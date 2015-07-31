'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('orderItemBilling', {
                parent: 'entity',
                url: '/orderItemBilling',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.orderItemBilling.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orderItemBilling/orderItemBillings.html',
                        controller: 'OrderItemBillingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orderItemBilling');
                        return $translate.refresh();
                    }]
                }
            })
            .state('orderItemBillingDetail', {
                parent: 'entity',
                url: '/orderItemBilling/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.orderItemBilling.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orderItemBilling/orderItemBilling-detail.html',
                        controller: 'OrderItemBillingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orderItemBilling');
                        return $translate.refresh();
                    }]
                }
            });
    });
