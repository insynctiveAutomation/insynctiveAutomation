'use strict';

var app = angular.module('accountApp', [ 'ngAnimate', 'ui.bootstrap']);

app.controller('AccountController', function($modalInstance, accountService) {

	var self = this;
	this.accountConfig;
	this.saved = '';
	
	/*On Load Methods*/
	this.getConfig = function() {
		accountService.getAccountConfig(function(data) {
			self.accountConfig = data;
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