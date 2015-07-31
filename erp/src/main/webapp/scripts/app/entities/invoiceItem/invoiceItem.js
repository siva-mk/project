'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('invoiceItem', {
                parent: 'entity',
                url: '/invoiceItem',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.invoiceItem.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/invoiceItem/invoiceItems.html',
                        controller: 'InvoiceItemController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('invoiceItem');
                        return $translate.refresh();
                    }]
                }
            })
            .state('invoiceItemDetail', {
                parent: 'entity',
                url: '/invoiceItem/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.invoiceItem.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/invoiceItem/invoiceItem-detail.html',
                        controller: 'InvoiceItemDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('invoiceItem');
                        return $translate.refresh();
                    }]
                }
            });
    });
