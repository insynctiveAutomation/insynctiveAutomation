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
				elm.replaceWith($compile('<gender ng-required="true" ng-model="model">' + elm.html() + '</gender>')(scope));
			} else if(scope.param === 'maritalStatus'){
				elm.replaceWith($compile('<marital-status ng-required="true" ng-model="model">' + elm.html() + '</marital-status>')(scope));
			} else if(scope.param === 'falta el de us addres same as home TODO'){
				elm.replaceWith($compile('<select ng-model="model" ng-options="o.v as o.n for o in [{ n: "Yes", v: true }, { n: "No", v: false }]">' + elm.html() + '</select>')(scope));
			} else if(scope.param === 'falta el de los benefits'){

			} else {
				elm.replaceWith($compile('<input ng-required="true" type="text" ng-model="model">' + elm.html() + '</input>')(scope));
			};
		},
		transclude: true,
        replace: true
		}
});