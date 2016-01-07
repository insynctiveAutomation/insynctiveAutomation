var app = angular.module('homeApp');

app.directive("result", function($compile) {
	return {
		require : 'ngModel',
		restrict : "E",
		scope : {
			bind : '=',
			value : '@'
		},
		link : function(scope, element, attrs, ngModel) {
			var removeClass = function(value){
				if (value === 'SUCCESS'){
					element.removeClass('success');
				} else if(value === 'FAILED'){
					element.removeClass('failed');
				} else if(value === 'SKIPED'){
					element.removeClass('skiped');
				} else if (value === 'NOT RUN'){
					element.removeClass('notRun');
				}
			}
			var addClass = function(value){
				if (value === 'SUCCESS'){
					element.addClass('success');
				} else if(value === 'FAILED'){
					element.addClass('failed');
				} else if(value === 'SKIPED'){
					element.addClass('skiped');
				} else if (value === 'NOT RUN'){
					element.addClass('notRun');
				}
			}
			
			scope.$watch(function(){
				return ngModel.$modelValue
			},function (newValue, oldValue){
				addClass(newValue);
				removeClass(oldValue);
				$compile(element)(scope);
            });
		},
		template : function() {
			return '<span ng-bind="bind"></span>'
		},
		replace : true
	}
});
	