'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('orderHeader', {
                parent: 'entity',
                url: '/orderHeader',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.orderHeader.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orderHeader/orderHeaders.html',
                        controller: 'OrderHeaderController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orderHeader');
                        return $translate.refresh();
                    }]
                }
            })
            .state('orderHeaderDetail', {
                parent: 'entity',
                url: '/orderHeader/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.orderHeader.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orderHeader/orderHeader-detail.html',
                        controller: 'OrderHeaderDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orderHeader');
                        return $translate.refresh();
                    }]
                }
            });
    });
