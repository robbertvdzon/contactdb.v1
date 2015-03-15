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
    }]
)

;
