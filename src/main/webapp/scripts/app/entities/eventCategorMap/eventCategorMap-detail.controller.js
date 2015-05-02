'use strict';

angular.module('ubwebApp')
    .controller('EventCategorMapDetailController', function ($scope, $stateParams, EventCategorMap, EventCategory, Event) {
        $scope.eventCategorMap = {};
        $scope.load = function (id) {
            EventCategorMap.get({id: id}, function(result) {
              $scope.eventCategorMap = result;
            });
        };
        $scope.load($stateParams.id);
    });
