'use strict';

angular.module('ubwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('enquiry', {
                parent: 'entity',
                url: '/enquiry',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.enquiry.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/enquiry/enquirys.html',
                        controller: 'EnquiryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('enquiry');
                        return $translate.refresh();
                    }]
                }
            })
            .state('enquiryDetail', {
                parent: 'entity',
                url: '/enquiry/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.enquiry.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/enquiry/enquiry-detail.html',
                        controller: 'EnquiryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('enquiry');
                        return $translate.refresh();
                    }]
                }
            });
    });
