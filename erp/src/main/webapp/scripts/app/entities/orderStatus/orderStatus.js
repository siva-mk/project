'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('orderStatus', {
                parent: 'entity',
                url: '/orderStatus',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.orderStatus.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orderStatus/orderStatuss.html',
                        controller: 'OrderStatusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orderStatus');
                        return $translate.refresh();
                    }]
                }
            })
            .state('orderStatusDetail', {
                parent: 'entity',
                url: '/orderStatus/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.orderStatus.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orderStatus/orderStatus-detail.html',
                        controller: 'OrderStatusDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orderStatus');
                        return $translate.refresh();
                    }]
                }
            });
    });
