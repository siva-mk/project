'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('ordertype', {
                parent: 'entity',
                url: '/ordertype',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.ordertype.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ordertype/ordertypes.html',
                        controller: 'OrdertypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ordertype');
                        return $translate.refresh();
                    }]
                }
            })
            .state('ordertypeDetail', {
                parent: 'entity',
                url: '/ordertype/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.ordertype.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ordertype/ordertype-detail.html',
                        controller: 'OrdertypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('ordertype');
                        return $translate.refresh();
                    }]
                }
            });
    });
