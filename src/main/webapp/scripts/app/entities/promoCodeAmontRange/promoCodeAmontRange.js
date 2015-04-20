'use strict';

angular.module('ubwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('promoCodeAmontRange', {
                parent: 'entity',
                url: '/promoCodeAmontRange',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.promoCodeAmontRange.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/promoCodeAmontRange/promoCodeAmontRanges.html',
                        controller: 'PromoCodeAmontRangeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('promoCodeAmontRange');
                        return $translate.refresh();
                    }]
                }
            })
            .state('promoCodeAmontRangeDetail', {
                parent: 'entity',
                url: '/promoCodeAmontRange/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.promoCodeAmontRange.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/promoCodeAmontRange/promoCodeAmontRange-detail.html',
                        controller: 'PromoCodeAmontRangeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('promoCodeAmontRange');
                        return $translate.refresh();
                    }]
                }
            });
    });
