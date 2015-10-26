'use strict';

var app = angular.module('parameterApp',  [ 'ngAnimate', 'ui.bootstrap', 'ngCookies', 'configuration']);

app.controller('modalParametersController', function(defaulObject, selectedTest, className, $cookies, $http, $window, $modal, $scope, $interval, parameterService) {
	
	var self = this;
	this.parameters = [];
	this.labels = [];
	this.loading = true;
	this.selectedTest = selectedTest;
	this.testName = selectedTest.name;
	
	/* On Load Methods */
	this.getParameters = function() {
		parameterService.getParameters(className, selectedTest.name, function(data) {
			self.parameters = data.params;
			data.params.forEach(function(params) {
				var param = params.split('.')[0]
				selectedTest.paramObject[param] = selectedTest.paramObject[param]
			});
			self.labels = data.labels;
			self.loading = false;
		});
	};
	this.getParameters();
	
	this.openUsAddressModal = function(usAddress) {
		var modalInstance = $modal.open({
			animation : true,
			template : '<us-address ng-required="true" ng-model="usAddress"></us-address>',
			backdrop: true,
			windowClass: 'edit-parameter-modal',
			size : 'lg',
			resolve : {
				usAddress: function () {
			          return usAddress
			    }
	       }
		});
	}
	
});
