'use strict';

angular.module('mswFrontendApp')
    .factory('domainService', ['$rootScope', '$http', function ($rootScope, $http) {
        var sharedService = {};
        sharedService.model = undefined;

        sharedService.prepForBroadcast = function (msg) {
            this.broadcastItem();
        };

        sharedService.broadcastItem = function () {
            $rootScope.$broadcast('modelUpdated');
        };

        sharedService.loadData = function () {
            {

                $http.get("resources/users/getuser").success(
                    function (response) {
                        response.username = response.username;
                        sharedService.setModel(response);
                        sharedService.broadcastItem();
                    });
            }
        }

        sharedService.hasModel = function () {
            return !angular.isUndefined(sharedService.model);
        };


        sharedService.getModel = function () {
            return sharedService.model;
        }

        sharedService.setModel = function (newmodel) {
            sharedService.model = newmodel;
        }

        return sharedService;

    }


    ]
);



