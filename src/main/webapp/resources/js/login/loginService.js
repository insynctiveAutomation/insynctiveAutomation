'use strict';

var app = angular.module('loginApp');

app.service('loginService', function($http, $cookies, $window) {
	
	this.login = function(data, callback, errorcallback) {
		$http.post('login',data).success(callback).error(errorcallback);
	};
	
	this.logout = function(callback) {
		$http.post('/logout', {}).success(callback);
	};
});