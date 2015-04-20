'use strict';

angular.module('ubwebApp')
    .factory('EventCategory', function ($resource) {
        return $resource('api/eventCategorys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.lastModifiedDate = new Date(data.lastModifiedDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
