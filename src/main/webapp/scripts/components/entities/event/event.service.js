'use strict';

angular.module('ubwebApp')
    .factory('Event', function ($resource) {
        return $resource('api/events/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.startDate = new Date(data.startDate);
                    data.endDate = new Date(data.endDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
