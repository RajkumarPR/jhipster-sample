'use strict';

angular.module('ubwebApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('eventTicket', {
                parent: 'entity',
                url: '/eventTicket',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.eventTicket.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/eventTicket/eventTickets.html',
                        controller: 'EventTicketController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('eventTicket');
                        return $translate.refresh();
                    }]
                }
            })
            .state('eventTicketDetail', {
                parent: 'entity',
                url: '/eventTicket/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'ubwebApp.eventTicket.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/eventTicket/eventTicket-detail.html',
                        controller: 'EventTicketDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('eventTicket');
                        return $translate.refresh();
                    }]
                }
            });
    });
