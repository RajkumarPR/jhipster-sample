'use strict';

angular.module('ubwebApp')
    .controller('ArticleImageDetailController', function ($scope, $stateParams, ArticleImage, ExpertsArticle) {
        $scope.articleImage = {};
        $scope.load = function (id) {
            ArticleImage.get({id: id}, function(result) {
              $scope.articleImage = result;
            });
        };
        $scope.load($stateParams.id);
    });
