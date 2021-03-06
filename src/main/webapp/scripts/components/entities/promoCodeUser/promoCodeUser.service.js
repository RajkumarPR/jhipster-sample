'use strict';

angular.module('ubwebApp')
    .factory('PromoCodeUser', function ($resource) {
        return $resource('api/promoCodeUsers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
