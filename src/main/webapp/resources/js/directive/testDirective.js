var app = angular.module('directiveApp');

app.directive("testView", function($compile, $cookies){
	return {
		restrict: "E",
		scope: {
			test: '=',
			controller: '='
		},
		templateUrl: '/testTemplate',
	}
});