'use strict';

angular.module('ubwebApp')
    .controller('EventCategorMapController', function ($scope, EventCategorMap, EventCategory, Event, ParseLinks) {
        $scope.eventCategorMaps = [];
        $scope.eventcategorys = EventCategory.query();
        $scope.events = Event.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            EventCategorMap.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.eventCategorMaps.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.eventCategorMaps = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            EventCategorMap.update($scope.eventCategorMap,
                function () {
                    $scope.reset();
                    $('#saveEventCategorMapModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            EventCategorMap.get({id: id}, function(result) {
                $scope.eventCategorMap = result;
                $('#saveEventCategorMapModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            EventCategorMap.get({id: id}, function(result) {
                $scope.eventCategorMap = result;
                $('#deleteEventCategorMapConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            EventCategorMap.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteEventCategorMapConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.eventCategorMap = {id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
