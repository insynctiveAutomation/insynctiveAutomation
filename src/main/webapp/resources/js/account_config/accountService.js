'use strict';

var app = angular.module('accountApp');

app.service('accountService', function($http) {
	
	this.getAccountConfig = function(callback) {
		$http.get('/accountProperties').success(callback);
	};

	this.saveConfig = function(dataPost, callback) {
		$http.post('/saveAccountConfig', dataPost).success(callback);
	};
	
});