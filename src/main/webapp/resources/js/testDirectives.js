var app = angular.module('testApp');

app.directive("result", function($compile) {
	return {
		require : 'ngModel',
		restrict : "E",
		scope : {
			bind : '=',
			value : '@'
		},
		link : function(scope, element, attrs, ngModel) {
			scope.$watch(function(){
				return ngModel.$modelValue
			},function (newValue, oldValue){
				if (newValue === 'SUCCESS'){
					element.addClass('success');
				} else if(newValue === 'FAILED'){
					element.addClass('failed');
				} else if(newValue === 'SKIPED'){
					element.addClass('skiped');
				} else {
					element.addClass('notRun');
				}
				$compile(element)(scope);
            });
		},
		template : function() {
			return '<span ng-bind="bind"></span>'
		},
		replace : true
	}
});