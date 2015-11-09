'use strict';

var app = angular.module('dashboardApp');

app.service('dashboardService', function($http) {
	
	this.getTestsSuites = function(callback) {
		$http.get('getAllTestsSuites').success(callback);
	};
	
	this.retry = function(tsID, callback, error) {
		$http.get('retry/'+tsID).success(callback).error(error);
	};
});