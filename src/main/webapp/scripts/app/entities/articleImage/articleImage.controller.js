'use strict';

angular.module('ubwebApp')
    .controller('ArticleImageController', function ($scope, ArticleImage, ExpertsArticle, ParseLinks) {
        $scope.articleImages = [];
        $scope.expertsarticles = ExpertsArticle.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            ArticleImage.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.articleImages.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.articleImages = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            ArticleImage.update($scope.articleImage,
                function () {
                    $scope.reset();
                    $('#saveArticleImageModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            ArticleImage.get({id: id}, function(result) {
                $scope.articleImage = result;
                $('#saveArticleImageModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            ArticleImage.get({id: id}, function(result) {
                $scope.articleImage = result;
                $('#deleteArticleImageConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ArticleImage.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteArticleImageConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.articleImage = {imageUrl: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
