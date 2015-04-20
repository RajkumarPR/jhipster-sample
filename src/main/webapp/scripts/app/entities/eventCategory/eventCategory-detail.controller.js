'use strict';

angular.module('ubwebApp')
    .controller('EventCategoryDetailController', function ($scope, $stateParams, EventCategory) {
        $scope.eventCategory = {};
        $scope.load = function (id) {
            EventCategory.get({id: id}, function(result) {
              $scope.eventCategory = result;
            });
        };
        $scope.load($stateParams.id);
    });
