'use strict';

angular.module('ubwebApp')
    .factory('EventCategorMap', function ($resource) {
        return $resource('api/eventCategorMaps/:id', {}, {
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
