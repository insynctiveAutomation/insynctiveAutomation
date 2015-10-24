var app = angular.module('configuration');

app.directive('gender', function($compile, $cookies){
	return {
		restrict: "E",
		require: 'ngModel',
		scope: {
			gender: '=ngModel',
		},
		templateUrl : '/gender_template'
	}
})

app.directive('maritalStatus', function($compile, $cookies){
	return {
		restrict: "E",
		require: 'ngModel',
		scope: {
			maritalStatus: '=ngModel',
		},
		templateUrl : '/marital_status'
	}
})