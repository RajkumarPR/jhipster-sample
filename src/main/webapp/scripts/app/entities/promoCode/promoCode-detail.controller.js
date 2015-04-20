'use strict';

angular.module('ubwebApp')
    .controller('PromoCodeDetailController', function ($scope, $stateParams, PromoCode) {
        $scope.promoCode = {};
        $scope.load = function (id) {
            PromoCode.get({id: id}, function(result) {
              $scope.promoCode = result;
            });
        };
        $scope.load($stateParams.id);
    });
