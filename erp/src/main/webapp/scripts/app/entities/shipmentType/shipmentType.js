'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('shipmentType', {
                parent: 'entity',
                url: '/shipmentType',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.shipmentType.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shipmentType/shipmentTypes.html',
                        controller: 'ShipmentTypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('shipmentType');
                        return $translate.refresh();
                    }]
                }
            })
            .state('shipmentTypeDetail', {
                parent: 'entity',
                url: '/shipmentType/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.shipmentType.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shipmentType/shipmentType-detail.html',
                        controller: 'ShipmentTypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('shipmentType');
                        return $translate.refresh();
                    }]
                }
            });
    });
