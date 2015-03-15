'use strict';

/**
 * @ngdoc function
 * @name mswFrontendApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the mswFrontendApp
 */
angular.module('mswFrontendApp')

    .controller('MainCtrl', ['$scope','$rootScope','domainService',  function ($scope, $rootScope, domainService) {
//alert("boot");
        domainService.loadData();

        $scope.awesomeThings = [
            'HTML5 Boilerplate',
            'AngularJS',
            'Karma'
        ];
    }]
)

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
