var app = angular.module('testApp');

app.directive("test", function($compile, $cookies){
	return {
		restrict: "E",
		scope: {
			controller: '='
		},
		templateUrl: '/testTemplate',
	}
});