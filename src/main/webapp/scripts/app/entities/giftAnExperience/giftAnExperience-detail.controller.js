'use strict';

angular.module('ubwebApp')
    .controller('GiftAnExperienceDetailController', function ($scope, $stateParams, GiftAnExperience) {
        $scope.giftAnExperience = {};
        $scope.load = function (id) {
            GiftAnExperience.get({id: id}, function(result) {
              $scope.giftAnExperience = result;
            });
        };
        $scope.load($stateParams.id);
    });
