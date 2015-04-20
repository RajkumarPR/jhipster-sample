'use strict';

angular.module('ubwebApp')
    .controller('RssfeedsController', function ($scope, Rssfeeds, ParseLinks) {
        $scope.rssfeedss = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Rssfeeds.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.rssfeedss.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.rssfeedss = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Rssfeeds.update($scope.rssfeeds,
                function () {
                    $scope.reset();
                    $('#saveRssfeedsModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Rssfeeds.get({id: id}, function(result) {
                $scope.rssfeeds = result;
                $('#saveRssfeedsModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Rssfeeds.get({id: id}, function(result) {
                $scope.rssfeeds = result;
                $('#deleteRssfeedsConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Rssfeeds.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteRssfeedsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.rssfeeds = {title: null, eventUrl: null, description: null, pubDate: null, guid: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
