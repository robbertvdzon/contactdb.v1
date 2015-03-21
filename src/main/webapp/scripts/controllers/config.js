'use strict';

angular.module('mswFrontendApp')

.config(['$httpProvider', function($httpProvider) {
//        $httpProvider.interceptors.push('httpRequestInterceptor');
        $httpProvider.interceptors.push('authHttpRequestInterceptor');
}])



;
