'use strict';

angular.module('ubwebApp')
    .controller('ExpertsArticleDetailController', function ($scope, $stateParams, ExpertsArticle) {
        $scope.expertsArticle = {};
        $scope.load = function (id) {
            ExpertsArticle.get({id: id}, function(result) {
              $scope.expertsArticle = result;
            });
        };
        $scope.load($stateParams.id);
    });
