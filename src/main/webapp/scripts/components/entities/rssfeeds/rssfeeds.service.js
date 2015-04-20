'use strict';

angular.module('ubwebApp')
    .factory('Rssfeeds', function ($resource) {
        return $resource('api/rssfeedss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.pubDate = new Date(data.pubDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
