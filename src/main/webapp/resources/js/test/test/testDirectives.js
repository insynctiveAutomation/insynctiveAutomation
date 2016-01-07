var app = angular.module('testApp');

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