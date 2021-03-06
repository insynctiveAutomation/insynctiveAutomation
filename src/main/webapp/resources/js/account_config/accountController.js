'use strict';

var app = angular.module('accountApp', [ 'ngAnimate', 'ui.bootstrap']);

app.controller('AccountController', function($modalInstance, accountService) {

	var self = this;
	this.accountConfig;
	this.paramObject;
	this.saved = '';
	this.isLoading = false;
	this.isLocalhost = true;
	
	/*On Load Methods*/
	this.getConfig = function() {
		self.isLoading = true;
		accountService.getAccountConfig(function(data) {
			self.accountConfig = data.accountProperty;
			self.paramObject = data.paramObject;
			self.isLoading = false;
		});
	};
	this.accountConfig = this.getConfig();
	
	this.saveConfig = function(){
		accountService.saveConfig(self.accountConfig,function(data) {
			self.saved = data;
			$modalInstance.close();
		});
	};
});