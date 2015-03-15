'use strict';

/**
 * @ngdoc function
 * @name mswFrontendApp.controller:LoginCtrl
 * @description
 * # LoginCtrl
 * Controller of the mswFrontendApp
 */
angular.module('mswFrontendApp')
//angular.module('mswFrontendApp,ngCookies')
    .controller('LoginCtrl', ['$scope', 'authFactory', '$http','domainService','$location', function LoginCtrl($scope, authFactory, $http, domainService,$location) {

//        initialize();
//
//
//        $scope.initialize = function () {
//        }

        $scope.test = function () {

//            alert(authFactory.getAuthData().authId+":"+authFactory.getAuthData().authPermission);
//            if (authFactory.isAuthenticated()) alert("logged in")
//            else alert("no");
//            {$http.get("http://localhost/api/resources/teams/all").success(
                {$http.get("/api/resources/users/getuser").success(
//                {$http.get("/api/resources/contacts/all").success(
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
