'use strict';

angular.module('mswFrontendApp')
    .controller('menuCtrl', ['$scope', 'authFactory', '$http','domainService', function LoginCtrl($scope, authFactory, $http, domainService) {
        $scope.isAuth = function () {
            return authFactory.isAuthenticated();
        }

        $scope.logOff = function () {
            return authFactory.logOff();
        }

        $scope.reload = function () {
            return domainService.loadData();
        }

    }])

;
