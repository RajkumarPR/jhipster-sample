'use strict';

angular.module('ubwebApp')
    .controller('EnquiryDetailController', function ($scope, $stateParams, Enquiry) {
        $scope.enquiry = {};
        $scope.load = function (id) {
            Enquiry.get({id: id}, function(result) {
              $scope.enquiry = result;
            });
        };
        $scope.load($stateParams.id);
    });
