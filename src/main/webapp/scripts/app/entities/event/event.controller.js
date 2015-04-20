'use strict';

angular.module('ubwebApp')
    .controller('EventController', function ($scope, Event, Cities, User, ParseLinks) {
        $scope.events = [];
        $scope.citiess = Cities.query();
        $scope.users = User.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Event.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.events.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.events = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Event.update($scope.event,
                function () {
                    $scope.reset();
                    $('#saveEventModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Event.get({id: id}, function(result) {
                $scope.event = result;
                $('#saveEventModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Event.get({id: id}, function(result) {
                $scope.event = result;
                $('#deleteEventConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Event.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteEventConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.event = {title: null, description: null, eventVenue: null, costIncludes: null, specialInstructions: null, isActive: null, isApproved: null, isBookable: null, isClosed: null, isOnlyEnquiry: null, startDate: null, endDate: null, eventContact: null, eventType: null, videoLink: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
