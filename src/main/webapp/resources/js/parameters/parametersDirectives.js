var app = angular.module('parameterApp');

app.directive("parameter", function($compile, $cookies){
	return {
		restrict: "E",
		scope: {
			model: '=',
			param: '@',
			editable: '='
		},
		link: function(scope, elm, attrs){
			if(scope.editable === undefined || scope.editable){
				if(scope.param === 'gender'){
					elm.replaceWith($compile('<gender ng-required="true" ng-model="model">' + elm.html() + '</gender>')(scope));
				} else if(scope.param === 'maritalStatus'){
					elm.replaceWith($compile('<marital-status ng-required="true" ng-model="model">' + elm.html() + '</marital-status>')(scope));
				} else if(scope.param === 'usAddress.sameAsHome'){
					elm.replaceWith($compile('<yes-no ng-model="model">' + elm.html() + '</yes-no>')(scope));
				} else if(scope.param === 'booleanParam'){
					elm.replaceWith($compile('<select ng-model="model" ng-options="o.v as o.n for o in [{ n: \'Open Person File\', v: true }, { n: \'Create Person\', v: false }]">' + elm.html() + '</select>')(scope));
				} else if(scope.param === 'medicalBenefit.company' || scope.param === 'dentalBenefit.company' || scope.param === 'visionBenefit.company'){
					elm.replaceWith($compile('<benefit-company ng-model="model">' + elm.html() + '</benefit-company>')(scope));
				} else {
					elm.replaceWith($compile('<input ng-required="true" type="text" ng-model="model">' + elm.html() + '</input>')(scope));
				};
			} else {
				elm.replaceWith($compile('<span ng-bind="model">' + elm.html() + '</span>')(scope));
			}
			
		},
		transclude: true,
        replace: true
		}
});