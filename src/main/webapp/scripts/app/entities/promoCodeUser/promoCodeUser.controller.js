'use strict';

angular.module('ubwebApp')
    .controller('PromoCodeUserController', function ($scope, PromoCodeUser, ParseLinks) {
        $scope.promoCodeUsers = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            PromoCodeUser.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.promoCodeUsers.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.promoCodeUsers = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            PromoCodeUser.update($scope.promoCodeUser,
                function () {
                    $scope.reset();
                    $('#savePromoCodeUserModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            PromoCodeUser.get({id: id}, function(result) {
                $scope.promoCodeUser = result;
                $('#savePromoCodeUserModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            PromoCodeUser.get({id: id}, function(result) {
                $scope.promoCodeUser = result;
                $('#deletePromoCodeUserConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PromoCodeUser.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deletePromoCodeUserConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.promoCodeUser = {emailUsed: null, promoCodeUsed: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
