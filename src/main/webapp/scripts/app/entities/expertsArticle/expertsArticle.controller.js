'use strict';

angular.module('ubwebApp')
    .controller('ExpertsArticleController', function ($scope, ExpertsArticle, ParseLinks) {
        $scope.expertsArticles = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            ExpertsArticle.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.expertsArticles.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.expertsArticles = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            ExpertsArticle.update($scope.expertsArticle,
                function () {
                    $scope.reset();
                    $('#saveExpertsArticleModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            ExpertsArticle.get({id: id}, function(result) {
                $scope.expertsArticle = result;
                $('#saveExpertsArticleModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            ExpertsArticle.get({id: id}, function(result) {
                $scope.expertsArticle = result;
                $('#deleteExpertsArticleConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ExpertsArticle.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteExpertsArticleConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.expertsArticle = {title: null, content: null, isApproved: null, isDeleted: null, uId: null, createdDate: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
