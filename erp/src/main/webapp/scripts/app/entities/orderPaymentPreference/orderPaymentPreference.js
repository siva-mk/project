'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('orderPaymentPreference', {
                parent: 'entity',
                url: '/orderPaymentPreference',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.orderPaymentPreference.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orderPaymentPreference/orderPaymentPreferences.html',
                        controller: 'OrderPaymentPreferenceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orderPaymentPreference');
                        return $translate.refresh();
                    }]
                }
            })
            .state('orderPaymentPreferenceDetail', {
                parent: 'entity',
                url: '/orderPaymentPreference/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.orderPaymentPreference.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/orderPaymentPreference/orderPaymentPreference-detail.html',
                        controller: 'OrderPaymentPreferenceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('orderPaymentPreference');
                        return $translate.refresh();
                    }]
                }
            });
    });
