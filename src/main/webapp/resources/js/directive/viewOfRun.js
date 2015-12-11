var app = angular.module('directiveApp');

app.directive("test", function($compile, $cookies){
	return {
		restrict: "E",
		scope: {
			testDetails: '=model',
			controller: '='
		},
		templateUrl: '/testSuite',
		transclude: true,
        replace: true
	}
});