'use strict';

angular.module('ubwebApp')
    .controller('CitiesController', function ($scope, Cities, ParseLinks) {
        $scope.citiess = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Cities.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.citiess.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.citiess = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Cities.update($scope.cities,
                function () {
                    $scope.reset();
                    $('#saveCitiesModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Cities.get({id: id}, function(result) {
                $scope.cities = result;
                $('#saveCitiesModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Cities.get({id: id}, function(result) {
                $scope.cities = result;
                $('#deleteCitiesConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Cities.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteCitiesConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.cities = {cityName: null, cityCode: null, state: null, country: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
