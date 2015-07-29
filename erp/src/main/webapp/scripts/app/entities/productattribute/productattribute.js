'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('productattribute', {
                parent: 'entity',
                url: '/productattribute',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.productattribute.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/productattribute/productattributes.html',
                        controller: 'ProductattributeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('productattribute');
                        return $translate.refresh();
                    }]
                }
            })
            .state('productattributeDetail', {
                parent: 'entity',
                url: '/productattribute/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.productattribute.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/productattribute/productattribute-detail.html',
                        controller: 'ProductattributeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('productattribute');
                        return $translate.refresh();
                    }]
                }
            });
    });
