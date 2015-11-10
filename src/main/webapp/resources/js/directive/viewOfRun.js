var app = angular.module('testApp');

app.directive("vewOfRun", function($compile, $cookies){
	return {
		restrict: "E",
		scope: {
			testDetails: '=model',
			controller: '=',
			testParameterText: '@'
		},
		templateUrl: '/testSuite',
		transclude: true,
        replace: true
		}
});