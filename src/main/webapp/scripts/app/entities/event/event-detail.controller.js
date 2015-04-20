'use strict';

angular.module('ubwebApp')
    .controller('EventDetailController', function ($scope, $stateParams, Event, Cities, User) {
        $scope.event = {};
        $scope.load = function (id) {
            Event.get({id: id}, function(result) {
              $scope.event = result;
            });
        };
        $scope.load($stateParams.id);
    });
