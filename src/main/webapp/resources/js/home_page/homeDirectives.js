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
				} else if (value === 'FAILURE'){
					element.removeClass('failed');
				} else if(value === 'SKIPED'){
					element.removeClass('skiped');
				} else if (value === 'NOT RUN'){
					element.removeClass('notRun');
				} else if(value && value.indexOf("airbrake") > -1){
					element.removeClass('failed');
				}
			}
			var addClass = function(value){
				if (value === 'SUCCESS'){
					element.addClass('success');
				} else if(value === 'SKIPED'){
					element.addClass('skiped');
				} else if (value === 'NOT RUN'){
					element.addClass('notRun');
				} else if (value === 'FAILURE'){
					element.addClass('failed');
				} else if(value && value.indexOf("airbrake") > -1){
					element.addClass('failed');
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
		template : function(element, attrs, ngModel) {
			return 	'<span>'+
					'	<span ng-show="bind && (bind.indexOf(\'airbrake\') <= -1)" ng-bind="bind"></span>'+
					'	<a class="failed" ng-show="bind && (bind.indexOf(\'airbrake\') > -1)" ng-href="{{bind}}">FAILURE</a>'+
					'</span>'
		},
		replace : true
	}
});
	