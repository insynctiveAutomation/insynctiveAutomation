var app = angular.module('testSuiteApp');

app.directive("testSuiteView", function($compile, $cookies){
	return {
		restrict: "E",
		scope: {
			test: '=',
			controller: '='
		},
		templateUrl: '/testSuiteTemplate',
	}
});