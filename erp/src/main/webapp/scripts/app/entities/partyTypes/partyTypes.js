'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('partyTypes', {
                parent: 'entity',
                url: '/partyTypes',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.partyTypes.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/partyTypes/partyTypess.html',
                        controller: 'PartyTypesController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('partyTypes');
                        return $translate.refresh();
                    }]
                }
            })
            .state('partyTypesDetail', {
                parent: 'entity',
                url: '/partyTypes/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.partyTypes.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/partyTypes/partyTypes-detail.html',
                        controller: 'PartyTypesDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('partyTypes');
                        return $translate.refresh();
                    }]
                }
            });
    });
