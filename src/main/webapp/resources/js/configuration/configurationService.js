'use strict';

var app = angular.module('configuration');

app.service('configurationService', function($http) {
	
	var self = this;
	

	this.getConfiguration = function(accID, callback) {
		$http.get('configuration/'+accID).success(callback);
	};

	this.save = function(data, callback, errorCallback) {
		$http.post('configuration/save', data).success(callback).error(errorCallback);
	};
	
});