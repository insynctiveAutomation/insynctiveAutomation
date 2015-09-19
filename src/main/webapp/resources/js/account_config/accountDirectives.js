var app = angular.module('accountApp');

app.directive('accountConfigContent', function($compile,$templateRequest) {
    return {
        restrict : 'E',
        templateUrl: '/accountConfigContent',
    }
})