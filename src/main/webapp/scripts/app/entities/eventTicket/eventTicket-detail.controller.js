'use strict';

angular.module('ubwebApp')
    .controller('EventTicketDetailController', function ($scope, $stateParams, EventTicket, Event) {
        $scope.eventTicket = {};
        $scope.load = function (id) {
            EventTicket.get({id: id}, function(result) {
              $scope.eventTicket = result;
            });
        };
        $scope.load($stateParams.id);
    });
