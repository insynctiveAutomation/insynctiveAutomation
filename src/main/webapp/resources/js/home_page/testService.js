'use strict';

var app = angular.module('testApp');

app.service('testService', function($http) {
	
	this.getTestsSuites = function(callback) {
		$http.get('testsSuites').success(callback);
	};

	this.startTest = function(testSuiteName, environmentName, browser, callback, errorCallback) {
		$http.post('test/'+testSuiteName+"/"+environmentName+"/"+browser, {}).success(callback).error(errorCallback);
	};
	
	this.getTestDetails = function(testSuiteName, callback) {
		$http.get('get/'+testSuiteName).success(callback);
	};
	
	this.getTestsStatus = function(tlaIndex, callback) {
		$http.get('status/'+tlaIndex).success(callback);
	};
	
	this.getVideoLink = function(index, callback) {
		$http.get('video/'+index).success(callback);
	};
	
	this.clearTests = function(tlaIndex, callback) {
		$http.get('clearTest/'+tlaIndex).success(callback);
	};
	
	this.logout = function(callback) {
		$http.post('logout', {}).success(callback);
	};
});