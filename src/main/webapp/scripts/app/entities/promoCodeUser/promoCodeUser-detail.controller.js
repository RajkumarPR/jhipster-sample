'use strict';

angular.module('ubwebApp')
    .controller('PromoCodeUserDetailController', function ($scope, $stateParams, PromoCodeUser) {
        $scope.promoCodeUser = {};
        $scope.load = function (id) {
            PromoCodeUser.get({id: id}, function(result) {
              $scope.promoCodeUser = result;
            });
        };
        $scope.load($stateParams.id);
    });
