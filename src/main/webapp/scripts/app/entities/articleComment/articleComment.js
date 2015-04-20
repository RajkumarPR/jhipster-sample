'use strict';

angular.module('ubwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('articleComment', {
                parent: 'entity',
                url: '/articleComment',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.articleComment.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/articleComment/articleComments.html',
                        controller: 'ArticleCommentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('articleComment');
                        return $translate.refresh();
                    }]
                }
            })
            .state('articleCommentDetail', {
                parent: 'entity',
                url: '/articleComment/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.articleComment.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/articleComment/articleComment-detail.html',
                        controller: 'ArticleCommentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('articleComment');
                        return $translate.refresh();
                    }]
                }
            });
    });
