var app = angular.module('testApp');

app.directive("viewOfRun", function($compile, $cookies){
	return {
		restrict: "E",
		scope: {
			testDetails: '=model',
			controller: '=',
			testParameterText: '@'
		},
		templateUrl: '/testSuiteTemplate',
		transclude: true,
        replace: true
		}
});