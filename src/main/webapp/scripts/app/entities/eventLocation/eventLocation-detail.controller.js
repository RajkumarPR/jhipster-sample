'use strict';

angular.module('ubwebApp')
    .controller('EventLocationDetailController', function ($scope, $stateParams, EventLocation, Event) {
        $scope.eventLocation = {};
        $scope.load = function (id) {
            EventLocation.get({id: id}, function(result) {
              $scope.eventLocation = result;
            });
        };
        $scope.load($stateParams.id);
    });
