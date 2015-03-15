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




;
