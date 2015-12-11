'use strict';

var app = angular.module('testPlanApp');

app.service('testPlanService', function($http) {
	
	this.findTestSuites = function(callback) {
		$http.get('testclasses').success(callback);
	};
});