var app = angular.module('parameterApp');

app.directive("parameter", function($compile, $cookies){
	return {
		restrict: "E",
		scope: {
			model: '=',
			param: '@'
		},
		link: function(scope, elm, attrs){
			if(scope.param === 'gender'){
				debugger
				elm.replaceWith($compile('<gender ng-model="model">' + elm.html() + '</gender>')(scope));
			} else if(scope.param === 'maritalStatus'){
				debugger
				elm.replaceWith($compile('<marital-status ng-model="model">' + elm.html() + '</marital-status>')(scope));
			} else {
				elm.replaceWith($compile('<input type="text" ng-model="model">' + elm.html() + '</input>')(scope));
			};
		},
		transclude: true,
        replace: true
		}
});