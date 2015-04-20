'use strict';

angular.module('ubwebApp')
    .controller('RssfeedsDetailController', function ($scope, $stateParams, Rssfeeds) {
        $scope.rssfeeds = {};
        $scope.load = function (id) {
            Rssfeeds.get({id: id}, function(result) {
              $scope.rssfeeds = result;
            });
        };
        $scope.load($stateParams.id);
    });
