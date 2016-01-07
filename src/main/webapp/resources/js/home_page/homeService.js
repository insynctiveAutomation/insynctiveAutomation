'use strict';

var app = angular.module('homeApp');

app.service('homeService', function($http) {
	
	this.getTestsSuites = function(callback) {
		$http.get('/testSuitesNames').success(callback);
	};

	this.startTest = function(testDetails, testSuiteName, environmentName, browser, callback, errorCallback) {
		$http.post('/test/'+testSuiteName+"/"+environmentName+"/"+browser, testDetails).success(callback).error(errorCallback);
	};
	
	this.getTestDetails = function(testSuiteName, callback) {
		$http.get('/get/'+testSuiteName).success(callback);
	};
	
	this.getTestsStatus = function(tlaIndex, callback) {
		$http.get('/status/'+tlaIndex).success(callback);
	};
	
	this.getVideoLink = function(index, callback) {
		$http.get('/video/'+index).success(callback);
	};
	
	this.clearTests = function(tlaIndex, callback) {
		$http.get('/clearTest/'+tlaIndex).success(callback);
	};
	
	this.getTestSuiteByID = function(id, callback) {
		$http.get('/testSuiteRun/'+id).success(callback);
	};
});