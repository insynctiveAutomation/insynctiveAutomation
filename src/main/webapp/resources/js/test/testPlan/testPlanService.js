'use strict';

var app = angular.module('testPlanApp');

app.service('testPlanService', function($http) {
	
	this.findTestPlan = function(id, callback, error) {
		$http.get('/testPlan/'+id).success(callback).error(error);
	};
	
	this.saveTestPlan  = function(testPlan, callback, error) {
		$http.post('/testPlan',testPlan).success(callback).error(error);
	};
	
});