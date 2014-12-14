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
    .controller('LoginCtrl', ['$scope', 'authFactory', '$http','domainService', function LoginCtrl($scope, authFactory, $http, domainService) {

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
                // Redirect etc.
            }).error(function () {
                // Error handling
            });
        };

        $scope.authData = authFactory.getAuthData();

    }])

    .factory('authFactory', ['$rootScope', '$http', '$cookies', function ($rootScope, $http, $cookies) {


        var authFactory = {
            authData: undefined
        };


        authFactory.initialize = function () {
            this.authData = {
                authId: $cookies.authId,
                authToken: $cookies.authToken,
                authPermission: $cookies.authPermission
            };
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
            return !angular.isUndefined(this.getAuthData());
        };

        authFactory.login = function (user) {
            return $http.post('/api/resources/auth/login', user);
        };

        authFactory.logOff = function () {
            this.authData = undefined;
            $cookies.authId = undefined;
            $cookies.authToken = undefined;
            $cookies.authPermission = undefined;
        };


        authFactory.initialize();

        return authFactory;
    }])


    .factory('authHttpRequestInterceptor', ['$rootScope', '$injector', function ($rootScope, $injector) {
        var authHttpRequestInterceptor = {
            request: function ($request) {
                var authFactory = $injector.get('authFactory');
                if (authFactory.isAuthenticated()) {
                    $request.headers['auth-id'] = authFactory.getAuthData().authId;
                    $request.headers['auth-token'] = authFactory.getAuthData().authToken;
                }
                return $request;
            }
        }

        return authHttpRequestInterceptor;
    }])


.factory('httpRequestInterceptor', function ($cookieStore) {
    return {
        request: function (config) {
            var token = $cookieStore.get("auth");
            config.url = URI(config.url).addSearch({'_auth_token':token}).toString();
            return config;
        }
    };
})

.config(['$httpProvider', function($httpProvider) {
//        $httpProvider.interceptors.push('httpRequestInterceptor');
        $httpProvider.interceptors.push('authHttpRequestInterceptor');
}])



;
