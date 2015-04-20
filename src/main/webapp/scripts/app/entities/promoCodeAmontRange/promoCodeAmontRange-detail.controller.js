'use strict';

angular.module('ubwebApp')
    .controller('PromoCodeAmontRangeDetailController', function ($scope, $stateParams, PromoCodeAmontRange) {
        $scope.promoCodeAmontRange = {};
        $scope.load = function (id) {
            PromoCodeAmontRange.get({id: id}, function(result) {
              $scope.promoCodeAmontRange = result;
            });
        };
        $scope.load($stateParams.id);
    });
