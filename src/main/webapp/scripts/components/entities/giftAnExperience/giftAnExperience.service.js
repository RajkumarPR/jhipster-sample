'use strict';

angular.module('ubwebApp')
    .factory('GiftAnExperience', function ($resource) {
        return $resource('api/giftAnExperiences/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateOfTransaction = new Date(data.dateOfTransaction);
                    data.voucherValidity = new Date(data.voucherValidity);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
