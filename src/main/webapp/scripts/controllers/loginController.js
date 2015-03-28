'use strict';

angular.module('mswFrontendApp')
    .controller('LoginCtrl', ['$scope', 'authFactory', '$http','domainService','$location', function LoginCtrl($scope, authFactory, $http, domainService,$location) {

        $scope.login = function (user) {
            authFactory.login(user).success(function (data) {
                authFactory.setAuthData(data);
                domainService.loadData();
                $location.path('contacts')
                // Redirect etc.
            }).error(function () {
                // Error handling
            });
        };

        $scope.authData = authFactory.getAuthData();

    }])


;
