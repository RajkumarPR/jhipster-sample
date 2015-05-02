'use strict';

angular.module('ubwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('eventCategorMap', {
                parent: 'entity',
                url: '/eventCategorMap',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.eventCategorMap.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/eventCategorMap/eventCategorMaps.html',
                        controller: 'EventCategorMapController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('eventCategorMap');
                        return $translate.refresh();
                    }]
                }
            })
            .state('eventCategorMapDetail', {
                parent: 'entity',
                url: '/eventCategorMap/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.eventCategorMap.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/eventCategorMap/eventCategorMap-detail.html',
                        controller: 'EventCategorMapDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('eventCategorMap');
                        return $translate.refresh();
                    }]
                }
            });
    });
