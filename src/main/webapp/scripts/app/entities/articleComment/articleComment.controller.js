'use strict';

angular.module('ubwebApp')
    .controller('ArticleCommentController', function ($scope, ArticleComment, ExpertsArticle, ParseLinks) {
        $scope.articleComments = [];
        $scope.expertsarticles = ExpertsArticle.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            ArticleComment.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.articleComments.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.articleComments = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            ArticleComment.update($scope.articleComment,
                function () {
                    $scope.reset();
                    $('#saveArticleCommentModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            ArticleComment.get({id: id}, function(result) {
                $scope.articleComment = result;
                $('#saveArticleCommentModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            ArticleComment.get({id: id}, function(result) {
                $scope.articleComment = result;
                $('#deleteArticleCommentConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ArticleComment.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteArticleCommentConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.articleComment = {comment: null, commenter: null, isApproved: null, commentedOn: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
