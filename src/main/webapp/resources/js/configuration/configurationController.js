'use strict';

var app = angular.module('configuration', [ 'ngAnimate', 'ui.bootstrap', 'ngCookies', ]);

app.controller('configurationController', function($cookies, $http, $window, $modal, $scope, $interval, configurationService) {
	
	var self = this;
	this.configuration;
	this.configurationTemplate;
	this.needToBeSave;
	this.form;
	
	this.finalSSN = '';
	this.finalPrimaryPhone = '';
	this.finalPhoneEmergency = '';
	this.finalEmail = '';
	
	this.isLoading = true;
	
	/* On Load Methods */
	this.getConfiguration = function() {
		configurationService.getConfiguration($cookies.get('userID'), function(data) {
			self.configuration = data;
			self.configurationTemplate = jQuery.extend({}, self.configuration);
			self.addFinalsLabels();
			self.haveChanges();
			self.isLoading = false;
		});
	};
	this.getConfiguration();
	
	this.haveChanges = function() {
		self.needToBeSave = _.isEqual(self.configuration, self.configurationTemplate);
	}
	
	this.saveConfiguration = function() {
		configurationService.save(self.configuration, function(data) {
			$window.location.href = '/';
		});
	};
	
	this.addFinalsLabels = function() {
		self.changeSSNLabel();
		self.changeEmailLabel();
		self.finalPrimaryPhone = self.changePrimaryPhoneLabel(self.finalPrimaryPhone, self.configuration.paramObject.primaryPhone);
		self.finalPhoneEmergency = self.changePrimaryPhoneLabel(self.finalPhoneEmergency, self.configuration.paramObject.emergencyContact.phone);
	}
	
	this.changeSSNLabel = function() {
		var ssn = (self.configuration) ? self.configuration.paramObject.ssn : undefined;
		var runID = (self.configuration) ? self.configuration.runID : '';
		self.finalSSN = (ssn) ? ssn.substring(0, ssn.length-runID.toString().length).concat(runID) : '-';
	}
	
	this.changePrimaryPhoneLabel = function(label, phone) {
		var runID = (self.configuration) ? self.configuration.runID : undefined;
		label = (phone) ? phone.substring(0, phone.length-runID.toString().length).concat(runID) : '-';
		return label;
	}
	
	this.changeEmailLabel = function() {
		var email = (self.configuration) ? self.configuration.paramObject.email : undefined;
		var runID = (self.configuration) ? self.configuration.runID : '';
		self.finalEmail = email.split('@')[0].concat('+').concat(runID).concat('@').concat(email.split('@')[1]);
	}
});