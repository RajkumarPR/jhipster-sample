'use strict';

angular.module('ubwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('giftAnExperience', {
                parent: 'entity',
                url: '/giftAnExperience',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.giftAnExperience.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/giftAnExperience/giftAnExperiences.html',
                        controller: 'GiftAnExperienceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('giftAnExperience');
                        return $translate.refresh();
                    }]
                }
            })
            .state('giftAnExperienceDetail', {
                parent: 'entity',
                url: '/giftAnExperience/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.giftAnExperience.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/giftAnExperience/giftAnExperience-detail.html',
                        controller: 'GiftAnExperienceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('giftAnExperience');
                        return $translate.refresh();
                    }]
                }
            });
    });
