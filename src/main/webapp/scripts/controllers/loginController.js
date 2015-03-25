'use strict';

angular.module('mswFrontendApp')
    .controller('LoginCtrl', ['$scope', 'authFactory', '$http','domainService','$location', function LoginCtrl($scope, authFactory, $http, domainService,$location) {

        $scope.test = function () {
                {$http.get("resources/users/getuser").success(
                function(response) {
                    alert(response.username);
                });}
        }



        $scope.login = function (user) {
            authFactory.login(user).success(function (data) {
                authFactory.setAuthData(data);
                domainService.loadData();
                $location.path('/')
                // Redirect etc.
            }).error(function () {
                // Error handling
            });
        };

        $scope.authData = authFactory.getAuthData();

    }])


;
