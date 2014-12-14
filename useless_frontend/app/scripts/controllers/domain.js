'use strict';

/**
 * @ngdoc function
 * @name mswFrontendApp.controller:GameCtrl
 * @description
 * # GameCtrl
 * Controller of the mswFrontendApp
 */
angular.module('mswFrontendApp')

  .controller('DomainCtrl', ['$scope', function ($scope) {
    $scope.var1 = {text:"piet"};
        alert("in domain");
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];

        $scope.$on('handleBroadcast', function() {
            $scope.message = sharedService.message;
            alert($scope.message);
        });

    }]);
