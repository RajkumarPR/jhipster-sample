'use strict';

angular.module('ubwebApp')
    .controller('EventLocationController', function ($scope, EventLocation, Event, ParseLinks) {
        $scope.eventLocations = [];
        $scope.events = Event.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            EventLocation.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.eventLocations.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.eventLocations = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            EventLocation.update($scope.eventLocation,
                function () {
                    $scope.reset();
                    $('#saveEventLocationModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            EventLocation.get({id: id}, function(result) {
                $scope.eventLocation = result;
                $('#saveEventLocationModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            EventLocation.get({id: id}, function(result) {
                $scope.eventLocation = result;
                $('#deleteEventLocationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            EventLocation.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteEventLocationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.eventLocation = {location: null, eventAddress: null, latitude: null, longitude: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
