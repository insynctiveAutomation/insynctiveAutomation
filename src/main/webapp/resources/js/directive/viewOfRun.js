var app = angular.module('directiveApp');

app.directive("viewOfRun", function($compile, $cookies){
	return {
		restrict: "E",
		scope: {
			testDetails: '=model',
			controller: '=',
			testParameterText: '@'
		},
		templateUrl: '/testSuiteHome',
		transclude: true,
        replace: true
	}
});