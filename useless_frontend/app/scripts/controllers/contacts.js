'use strict';

/**
 * @ngdoc function
 * @name mswFrontendApp.controller:GameCtrl
 * @description
 * # ContactsCtrl
 * Controller of the mswFrontendApp
 */
angular.module('mswFrontendApp')

    .controller('ContactsCtrl', ['$scope', '$rootScope', 'domainService', function ($scope, $rootScope, domainService) {
        $scope.var1 = {text: domainService.model.username};
        $scope.contacts = domainService.model.contacts;

        $rootScope.$on('modelUpdated', function () {
            $scope.var1 = {text: domainService.model.username};
            $scope.contacts = domainService.model.contacts;
        });

    }]);
