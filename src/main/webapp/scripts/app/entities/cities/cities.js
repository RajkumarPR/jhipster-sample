'use strict';

angular.module('ubwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cities', {
                parent: 'entity',
                url: '/cities',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.cities.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cities/citiess.html',
                        controller: 'CitiesController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cities');
                        return $translate.refresh();
                    }]
                }
            })
            .state('citiesDetail', {
                parent: 'entity',
                url: '/cities/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.cities.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cities/cities-detail.html',
                        controller: 'CitiesDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('cities');
                        return $translate.refresh();
                    }]
                }
            });
    });
