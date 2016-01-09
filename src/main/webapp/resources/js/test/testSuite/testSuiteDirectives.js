var app = angular.module('testSuiteApp');

app.directive("testSuite", function($compile, $cookies){
	return {
		restrict: "E",
		scope: {
			controller: '='
		},
		templateUrl: '/testSuiteTemplate',
	}
});