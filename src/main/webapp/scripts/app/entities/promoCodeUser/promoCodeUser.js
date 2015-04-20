'use strict';

angular.module('ubwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('promoCodeUser', {
                parent: 'entity',
                url: '/promoCodeUser',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.promoCodeUser.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/promoCodeUser/promoCodeUsers.html',
                        controller: 'PromoCodeUserController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('promoCodeUser');
                        return $translate.refresh();
                    }]
                }
            })
            .state('promoCodeUserDetail', {
                parent: 'entity',
                url: '/promoCodeUser/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.promoCodeUser.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/promoCodeUser/promoCodeUser-detail.html',
                        controller: 'PromoCodeUserDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('promoCodeUser');
                        return $translate.refresh();
                    }]
                }
            });
    });
