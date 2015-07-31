'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('invoiceType', {
                parent: 'entity',
                url: '/invoiceType',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.invoiceType.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/invoiceType/invoiceTypes.html',
                        controller: 'InvoiceTypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('invoiceType');
                        return $translate.refresh();
                    }]
                }
            })
            .state('invoiceTypeDetail', {
                parent: 'entity',
                url: '/invoiceType/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.invoiceType.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/invoiceType/invoiceType-detail.html',
                        controller: 'InvoiceTypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('invoiceType');
                        return $translate.refresh();
                    }]
                }
            });
    });
