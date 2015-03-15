'use strict';

angular.module('mswFrontendApp')

/*
* VOLGENS MIJ KAN DEZE MODULE WEG!!!
*/

.factory('httpRequestInterceptor', function ($cookieStore) {
    return {
        request: function (config) {
            var token = $cookieStore.get("auth");
            config.url = URI(config.url).addSearch({'_auth_token':token}).toString();
            return config;
        }
    };
})
;
