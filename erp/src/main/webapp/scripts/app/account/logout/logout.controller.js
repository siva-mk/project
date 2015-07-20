'use strict';

angular.module('erpApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
