'use strict';

var app = angular.module('dashboardApp');

app.service('dashboardService', function($http) {
	
	this.countTestSuites = function(callback) {
		$http.get('/countTestSuites').success(callback);
	};
	
	this.getTestsSuites = function(page, count, callback) {
		$http.get('/getTestsSuites/'+page+"/"+count).success(callback);
	};
	
	this.getAllTestsSuites = function(callback) {
		$http.get('/getAllTestsSuites').success(callback);
	};
	
	this.retry = function(tsID, callback, error) {
		$http.get('/retry/'+tsID).success(callback).error(error);
	};
});