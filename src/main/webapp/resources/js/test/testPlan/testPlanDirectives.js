var app = angular.module('testPlanApp');

app.directive("testPlan", function($compile, $cookies){
	return {
		restrict: "E",
		scope: {
			controller: '='
		},
		templateUrl: '/testPlanTemplate',
	}
});