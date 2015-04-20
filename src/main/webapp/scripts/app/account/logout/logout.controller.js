'use strict';

angular.module('ubwebApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
