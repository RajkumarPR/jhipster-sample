'use strict';

angular.module('ubwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('promoCode', {
                parent: 'entity',
                url: '/promoCode',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.promoCode.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/promoCode/promoCodes.html',
                        controller: 'PromoCodeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('promoCode');
                        return $translate.refresh();
                    }]
                }
            })
            .state('promoCodeDetail', {
                parent: 'entity',
                url: '/promoCode/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.promoCode.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/promoCode/promoCode-detail.html',
                        controller: 'PromoCodeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('promoCode');
                        return $translate.refresh();
                    }]
                }
            });
    });
