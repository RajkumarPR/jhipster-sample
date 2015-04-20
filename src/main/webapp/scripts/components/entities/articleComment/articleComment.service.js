'use strict';

angular.module('ubwebApp')
    .factory('ArticleComment', function ($resource) {
        return $resource('api/articleComments/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.commentedOn = new Date(data.commentedOn);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
