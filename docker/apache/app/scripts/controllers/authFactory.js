'use strict';

angular.module('mswFrontendApp')
    .factory('authFactory', ['$rootScope' , '$http', '$cookies','$location', function ($rootScope, $http, $cookies,$location) {
//    .factory('authFactory', ['$scope', '$rootScope' , '$http', '$cookies','$location', function ($scope, $rootScope, $http, $cookies,$location) {

        var authFactory = {
            authData: undefined
        };


        authFactory.initialize = function () {
            this.authData = {
                authId: $cookies.authId,
                authToken: $cookies.authToken,
                authPermission: $cookies.authPermission
            };

            $rootScope.$on('authenticationFailed', function() {
                self.logOff();
                $location.path('login')
            });
            var self = this;
        }

        authFactory.setAuthData = function (authData) {
            this.authData = {
                authId: authData.authId,
                authToken: authData.authToken,
                authPermission: authData.authPermission
            };
            $cookies.authId = authData.authId;
            $cookies.authToken = authData.authToken;
            $cookies.authPermission = authData.authPermission;
            $rootScope.$broadcast('authChanged');
        };

        authFactory.getAuthData = function () {
            return this.authData;
        };

        authFactory.isAuthenticated = function () {
            return !angular.isUndefined(this.getAuthData().authId);
        };

        authFactory.login = function (user) {
            return $http.post('resources/auth/login', user);
        };

        authFactory.logOff = function () {
            this.authData.authId = undefined;
            this.authData.authToken = undefined;
            this.authData.authPermission = undefined;
            $cookies.authId = undefined;
            $cookies.authToken = undefined;
            $cookies.authPermission = undefined;
            $location.path('/')
        };



        authFactory.initialize();

        return authFactory;
    }])



;
