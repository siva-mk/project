'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('party', {
                parent: 'entity',
                url: '/party',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.party.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/party/partys.html',
                        controller: 'PartyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('party');
                        return $translate.refresh();
                    }]
                }
            })
            .state('partyDetail', {
                parent: 'entity',
                url: '/party/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.party.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/party/party-detail.html',
                        controller: 'PartyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('party');
                        return $translate.refresh();
                    }]
                }
            });
    });
