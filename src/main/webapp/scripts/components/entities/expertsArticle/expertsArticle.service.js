'use strict';

angular.module('ubwebApp')
    .factory('ExpertsArticle', function ($resource) {
        return $resource('api/expertsArticles/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    var createdDateFrom = data.createdDate.split("-");
                    data.createdDate = new Date(new Date(createdDateFrom[0], createdDateFrom[1] - 1, createdDateFrom[2]));
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
