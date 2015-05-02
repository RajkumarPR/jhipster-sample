'use strict';

angular.module('ubwebApp')
    .factory('EventTicket', function ($resource) {
        return $resource('api/eventTickets/:id', {}, {
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
