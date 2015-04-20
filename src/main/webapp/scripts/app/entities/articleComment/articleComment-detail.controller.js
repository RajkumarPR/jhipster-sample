'use strict';

angular.module('ubwebApp')
    .controller('ArticleCommentDetailController', function ($scope, $stateParams, ArticleComment, ExpertsArticle) {
        $scope.articleComment = {};
        $scope.load = function (id) {
            ArticleComment.get({id: id}, function(result) {
              $scope.articleComment = result;
            });
        };
        $scope.load($stateParams.id);
    });
