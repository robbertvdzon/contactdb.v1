'use strict';

angular.module('mswFrontendApp')

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
  .factory('httpErrorInterceptor', function($rootScope, $q, $location) {
    return {
      'responseError': function(rejection) {
        // permission denied
        if(rejection.status === 403) {
            $rootScope.$broadcast("authenticationFailed");
        }
        $q.reject(rejection);
      }
    };
  })
  .config(function($httpProvider) {
    $httpProvider.interceptors.push('httpErrorInterceptor');
});
