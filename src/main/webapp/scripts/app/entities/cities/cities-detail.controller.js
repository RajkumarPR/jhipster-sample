'use strict';

angular.module('ubwebApp')
    .controller('CitiesDetailController', function ($scope, $stateParams, Cities) {
        $scope.cities = {};
        $scope.load = function (id) {
            Cities.get({id: id}, function(result) {
              $scope.cities = result;
            });
        };
        $scope.load($stateParams.id);
    });
