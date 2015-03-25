'use strict';

angular.module('mswFrontendApp')

.config(['$httpProvider', function($httpProvider) {
        $httpProvider.interceptors.push('authHttpRequestInterceptor');
}])



;
