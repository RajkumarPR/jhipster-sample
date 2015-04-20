'use strict';

angular.module('ubwebApp')
    .controller('EnquiryController', function ($scope, Enquiry, ParseLinks) {
        $scope.enquirys = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Enquiry.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.enquirys.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.enquirys = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.create = function () {
            Enquiry.update($scope.enquiry,
                function () {
                    $scope.reset();
                    $('#saveEnquiryModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            Enquiry.get({id: id}, function(result) {
                $scope.enquiry = result;
                $('#saveEnquiryModal').modal('show');
            });
        };

        $scope.delete = function (id) {
            Enquiry.get({id: id}, function(result) {
                $scope.enquiry = result;
                $('#deleteEnquiryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Enquiry.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteEnquiryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.enquiry = {eventId: null, customerName: null, email: null, enquiry_message: null, mobileNo: null, tickeNames: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
