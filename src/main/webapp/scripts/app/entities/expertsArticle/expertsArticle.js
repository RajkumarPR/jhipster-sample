'use strict';

angular.module('ubwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('expertsArticle', {
                parent: 'entity',
                url: '/expertsArticle',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.expertsArticle.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/expertsArticle/expertsArticles.html',
                        controller: 'ExpertsArticleController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('expertsArticle');
                        return $translate.refresh();
                    }]
                }
            })
            .state('expertsArticleDetail', {
                parent: 'entity',
                url: '/expertsArticle/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.expertsArticle.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/expertsArticle/expertsArticle-detail.html',
                        controller: 'ExpertsArticleDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('expertsArticle');
                        return $translate.refresh();
                    }]
                }
            });
    });
