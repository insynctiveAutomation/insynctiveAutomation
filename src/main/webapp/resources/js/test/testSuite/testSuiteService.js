'use strict';

var app = angular.module('testSuiteApp');

app.service('testSuiteService', function($http) {
	
	this.findTestSuite = function(id, callback, error) {
		$http.get('/testSuite/'+id).success(callback).error(error);
	};
	
	this.saveTestSuite = function(testSuite, callback, error) {
		$http.post('/testPlan',testSuite).success(callback).error(error);
	};
	
	this.findAllTestSuites = function(callback) {
		$http.get('/testSuites').success(callback);
	};
	
});