'use strict';

angular.module('mswFrontendApp')

    .controller('MainCtrl', ['$scope','$rootScope','domainService',  function ($scope, $rootScope, domainService) {
        domainService.loadData();
    }]
)

;
