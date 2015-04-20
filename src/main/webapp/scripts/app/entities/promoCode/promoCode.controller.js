'use strict';

angular.module('ubwebApp')
    .controller('PromoCodeController', function ($scope, PromoCode, ParseLinks) {
        $scope.promoCodes = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            PromoCode.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.promoCodes.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.promoCodes = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            PromoCode.update($scope.promoCode,
                function () {
                    $scope.reset();
                    $('#savePromoCodeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            PromoCode.get({id: id}, function(result) {
                $scope.promoCode = result;
                $('#savePromoCodeModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            PromoCode.get({id: id}, function(result) {
                $scope.promoCode = result;
                $('#deletePromoCodeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PromoCode.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deletePromoCodeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.promoCode = {promoCode: null, promoAmount: null, isPromoValid: null, promoValidity: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
