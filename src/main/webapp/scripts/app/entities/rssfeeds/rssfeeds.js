'use strict';

angular.module('ubwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rssfeeds', {
                parent: 'entity',
                url: '/rssfeeds',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.rssfeeds.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rssfeeds/rssfeedss.html',
                        controller: 'RssfeedsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rssfeeds');
                        return $translate.refresh();
                    }]
                }
            })
            .state('rssfeedsDetail', {
                parent: 'entity',
                url: '/rssfeeds/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.rssfeeds.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rssfeeds/rssfeeds-detail.html',
                        controller: 'RssfeedsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('rssfeeds');
                        return $translate.refresh();
                    }]
                }
            });
    });
