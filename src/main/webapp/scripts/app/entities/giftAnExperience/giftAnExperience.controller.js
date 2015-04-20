'use strict';

angular.module('ubwebApp')
    .controller('GiftAnExperienceController', function ($scope, GiftAnExperience, ParseLinks) {
        $scope.giftAnExperiences = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            GiftAnExperience.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.giftAnExperiences.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.giftAnExperiences = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            GiftAnExperience.update($scope.giftAnExperience,
                function () {
                    $scope.reset();
                    $('#saveGiftAnExperienceModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            GiftAnExperience.get({id: id}, function(result) {
                $scope.giftAnExperience = result;
                $('#saveGiftAnExperienceModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            GiftAnExperience.get({id: id}, function(result) {
                $scope.giftAnExperience = result;
                $('#deleteGiftAnExperienceConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            GiftAnExperience.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteGiftAnExperienceConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.giftAnExperience = {receiverName: null, receiverMail: null, senderName: null, senderMail: null, senderPhone: null, giftMessage: null, voucherCode: null, voucherAmount: null, transactionId: null, dateOfTransaction: null, isVoucherValid: null, voucherValidity: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
