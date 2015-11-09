'use strict';

var app = angular.module('dashboardApp', [ 'ngAnimate', 'ui.bootstrap', 'ngCookies', 'parameterApp', 'testApp']);

app.controller('DashboardController', function($cookies, $http, $window, $modal, $scope, $interval, dashboardService) {
	
	var self = this;
	this.testsSuites;
	this.retryText = "Retry"
	this.isLoadingRetry = false;
	this.isLoadingPage;
	
	this.getTestsSuites = function(){
		self.isLoadingPage = true;
		dashboardService.getTestsSuites(function(data){
			self.testsSuites = data;
			self.isLoadingPage = false
		});
	}
	
	this.getTestsSuites();
	
	this.openTestSuite = function(testSuite) {
		var modalInstance = $modal.open({
			animation : true,
			templateUrl : 'testSuite', 
			backdrop: true,
			controller : 'TestSuiteController',
			controllerAs : 'controller',
			windowClass: 'edit-parameter-modal',
			size : 'lg',
			resolve : {
				testDetails: function () {
			          return testSuite
			    }
	       }
		});
	}
	
	this.retry = function(testSuite) {
		testSuite.isLoadingRetry = true;
		dashboardService.retry(testSuite.testSuiteID, function(data){
			testSuite.isLoadingRetry = false;
			var modalInstance = $modal.open({
				animation : true,
				templateUrl : 'testSuite', 
				backdrop: true,
				controller : 'TestSuiteController',
				controllerAs : 'controller',
				windowClass: 'edit-parameter-modal',
				size : 'lg',
				resolve : {
					testDetails: function () {
						return testSuite
					},
					index: function() {
						return data
					}
				}
			});
		}, function(data){
			testSuite.isLoadingRetry = false;
			testSuite.error = true;
		});
	}
});
