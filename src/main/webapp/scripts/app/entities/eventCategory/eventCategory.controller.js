'use strict';

angular.module('ubwebApp')
    .controller('EventCategoryController', function ($scope, EventCategory, ParseLinks) {
        $scope.eventCategorys = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            EventCategory.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.eventCategorys.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.eventCategorys = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            EventCategory.update($scope.eventCategory,
                function () {
                    $scope.reset();
                    $('#saveEventCategoryModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            EventCategory.get({id: id}, function(result) {
                $scope.eventCategory = result;
                $('#saveEventCategoryModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            EventCategory.get({id: id}, function(result) {
                $scope.eventCategory = result;
                $('#deleteEventCategoryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            EventCategory.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteEventCategoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.eventCategory = {categoryname: null, categorydescription: null, lastModifiedDate: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
