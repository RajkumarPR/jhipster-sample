'use strict';

angular.module('ubwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('eventLocation', {
                parent: 'entity',
                url: '/eventLocation',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.eventLocation.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/eventLocation/eventLocations.html',
                        controller: 'EventLocationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('eventLocation');
                        return $translate.refresh();
                    }]
                }
            })
            .state('eventLocationDetail', {
                parent: 'entity',
                url: '/eventLocation/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.eventLocation.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/eventLocation/eventLocation-detail.html',
                        controller: 'EventLocationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('eventLocation');
                        return $translate.refresh();
                    }]
                }
            });
    });
