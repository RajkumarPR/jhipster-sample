'use strict';

angular.module('ubwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('eventCategory', {
                parent: 'entity',
                url: '/eventCategory',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.eventCategory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/eventCategory/eventCategorys.html',
                        controller: 'EventCategoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('eventCategory');
                        return $translate.refresh();
                    }]
                }
            })
            .state('eventCategoryDetail', {
                parent: 'entity',
                url: '/eventCategory/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.eventCategory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/eventCategory/eventCategory-detail.html',
                        controller: 'EventCategoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('eventCategory');
                        return $translate.refresh();
                    }]
                }
            });
    });
