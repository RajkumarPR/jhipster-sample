'use strict';

angular.module('ubwebApp')
    .factory('PromoCodeAmontRange', function ($resource) {
        return $resource('api/promoCodeAmontRanges/:id', {}, {
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
