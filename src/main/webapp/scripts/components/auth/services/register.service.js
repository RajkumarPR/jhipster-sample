'use strict';

angular.module('ubwebApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


