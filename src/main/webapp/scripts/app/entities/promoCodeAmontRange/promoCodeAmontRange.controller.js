'use strict';

angular.module('ubwebApp')
    .controller('PromoCodeAmontRangeController', function ($scope, PromoCodeAmontRange, ParseLinks) {
        $scope.promoCodeAmontRanges = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            PromoCodeAmontRange.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.promoCodeAmontRanges.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.promoCodeAmontRanges = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            PromoCodeAmontRange.update($scope.promoCodeAmontRange,
                function () {
                    $scope.reset();
                    $('#savePromoCodeAmontRangeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            PromoCodeAmontRange.get({id: id}, function(result) {
                $scope.promoCodeAmontRange = result;
                $('#savePromoCodeAmontRangeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            PromoCodeAmontRange.get({id: id}, function(result) {
                $scope.promoCodeAmontRange = result;
                $('#deletePromoCodeAmontRangeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PromoCodeAmontRange.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deletePromoCodeAmontRangeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.promoCodeAmontRange = {lower: null, higher: null, threshold: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
