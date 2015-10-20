'use strict';

var app = angular.module('configuration', [ 'ngAnimate', 'ui.bootstrap', 'ngCookies', ]);

app.controller('configurationController', function($cookies, $http, $window, $modal, $scope, $interval, configurationService) {
	
	var self = this;
	this.configuration;
	this.isLoading = true;
	
	/* On Load Methods */
	this.getConfiguration = function() {
		configurationService.getConfiguration($cookies.get('userID'), function(data) {
			self.configuration = data;
			self.isLoading = false;
		});
	};
	this.getConfiguration();
	
	this.saveConfiguration = function() {
		configurationService.save(self.configuration, function(data) {
			$window.location.href = '/';
		});
	};
	
});