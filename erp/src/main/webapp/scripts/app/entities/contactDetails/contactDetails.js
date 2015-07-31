'use strict';

angular.module('erpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('contactDetails', {
                parent: 'entity',
                url: '/contactDetails',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.contactDetails.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contactDetails/contactDetailss.html',
                        controller: 'ContactDetailsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('contactDetails');
                        return $translate.refresh();
                    }]
                }
            })
            .state('contactDetailsDetail', {
                parent: 'entity',
                url: '/contactDetails/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'erpApp.contactDetails.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contactDetails/contactDetails-detail.html',
                        controller: 'ContactDetailsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('contactDetails');
                        return $translate.refresh();
                    }]
                }
            });
    });
