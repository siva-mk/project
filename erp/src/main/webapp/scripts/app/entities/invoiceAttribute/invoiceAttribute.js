'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('invoiceAttribute', {
                parent: 'entity',
                url: '/invoiceAttribute',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.invoiceAttribute.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/invoiceAttribute/invoiceAttributes.html',
                        controller: 'InvoiceAttributeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('invoiceAttribute');
                        return $translate.refresh();
                    }]
                }
            })
            .state('invoiceAttributeDetail', {
                parent: 'entity',
                url: '/invoiceAttribute/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.invoiceAttribute.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/invoiceAttribute/invoiceAttribute-detail.html',
                        controller: 'InvoiceAttributeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('invoiceAttribute');
                        return $translate.refresh();
                    }]
                }
            });
    });
