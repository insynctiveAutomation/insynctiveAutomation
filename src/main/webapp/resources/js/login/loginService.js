'use strict';

var app = angular.module('loginApp');

app.service('loginService', function($http) {
	
	this.login = function(data, callback) {
		$http.post('login',data).success(callback);;
	};
});