'use strict';

angular.module('ubwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('articleImage', {
                parent: 'entity',
                url: '/articleImage',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.articleImage.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/articleImage/articleImages.html',
                        controller: 'ArticleImageController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('articleImage');
                        return $translate.refresh();
                    }]
                }
            })
            .state('articleImageDetail', {
                parent: 'entity',
                url: '/articleImage/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.articleImage.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/articleImage/articleImage-detail.html',
                        controller: 'ArticleImageDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('articleImage');
                        return $translate.refresh();
                    }]
                }
            });
    });
