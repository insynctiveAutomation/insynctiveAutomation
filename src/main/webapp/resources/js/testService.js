'use strict';

var app = angular.module('testApp');

app.service('testService', function($http) {
	
	this.getTestsSuites = function(callback) {
		$http.get('/testsSuites').success(callback);
	};

	this.startTest = function(testSuiteName, callback) {
		$http.get('/test/'+testSuiteName).success(callback);
	};
	
	this.getTestDetails = function(testSuiteName, callback) {
		$http.get('/get/'+testSuiteName).success(callback);
	};
	
	this.getTestsStatus = function(callback) {
		$http.get('/status').success(callback);
	};
	
	this.getVideoLink = function(callback) {
		$http.get('/video').success(callback);
	};
});