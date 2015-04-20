'use strict';

angular.module('ubwebApp')
    .factory('PromoCode', function ($resource) {
        return $resource('api/promoCodes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.promoValidity = new Date(data.promoValidity);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
