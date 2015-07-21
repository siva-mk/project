'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('productcategory', {
                parent: 'entity',
                url: '/productcategory',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.productcategory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/productcategory/productcategorys.html',
                        controller: 'ProductcategoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('productcategory');
                        return $translate.refresh();
                    }]
                }
            })
            .state('productcategoryDetail', {
                parent: 'entity',
                url: '/productcategory/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.productcategory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/productcategory/productcategory-detail.html',
                        controller: 'ProductcategoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('productcategory');
                        return $translate.refresh();
                    }]
                }
            });
    });
