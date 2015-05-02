'use strict';

angular.module('ubwebApp')
    .controller('EventTicketController', function ($scope, EventTicket, Event, ParseLinks) {
        $scope.eventTickets = [];
        $scope.events = Event.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            EventTicket.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.eventTickets.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.eventTickets = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            EventTicket.update($scope.eventTicket,
                function () {
                    $scope.reset();
                    $('#saveEventTicketModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            EventTicket.get({id: id}, function(result) {
                $scope.eventTicket = result;
                $('#saveEventTicketModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            EventTicket.get({id: id}, function(result) {
                $scope.eventTicket = result;
                $('#deleteEventTicketConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            EventTicket.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteEventTicketConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.eventTicket = {ticketCost: null, totalTickets: null, discount: null, hasOffer: null, minimunTicket: null, ticketType: null, ticketName: null, startTime: null, endTime: null, duration: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
