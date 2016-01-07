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
});

app.directive('maritalStatus', function($compile, $cookies){
	return {
		restrict: "E",
		require: 'ngModel',
		scope: {
			maritalStatus: '=ngModel',
		},
		templateUrl : '/marital_status'
	}
});

app.directive('usAddress', function($compile, $cookies){
	return {
		restrict: "E",
		require: 'ngModel',
		scope: {
			usAddress: '=ngModel',
		},
		templateUrl : '/us_address'
	}
});

app.directive('benefitCompany', function($compile, $cookies){
	return {
		restrict: "E",
		require: 'ngModel',
		scope: {
			benefitCompany: '=ngModel',
		},
		templateUrl : '/benefit_company'
	}
});

app.directive('yesNo', function($compile, $cookies){
	return {
		restrict: "E",
		require: 'ngModel',
		scope: {
			model: '=ngModel',
		},
		templateUrl : '/yes_no'
	}
});