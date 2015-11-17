'use strict';

var app = angular.module('parameterApp');

app.service('parameterService', function($http) {
	
	this.getParameters = function(className, testName, callback) {
		$http.get('parameter/'+className+'/'+testName).success(callback);
	};
	
});