'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('producttype', {
                parent: 'entity',
                url: '/producttype',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.producttype.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/producttype/producttypes.html',
                        controller: 'ProducttypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('producttype');
                        return $translate.refresh();
                    }]
                }
            })
            .state('producttypeDetail', {
                parent: 'entity',
                url: '/producttype/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.producttype.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/producttype/producttype-detail.html',
                        controller: 'ProducttypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('producttype');
                        return $translate.refresh();
                    }]
                }
            });
    });
