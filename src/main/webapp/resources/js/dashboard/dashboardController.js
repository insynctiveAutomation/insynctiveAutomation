'use strict';

var app = angular.module('dashboardApp', [ 'ngAnimate', 'ui.bootstrap', 'ngCookies', 'parameterApp', 'testApp']);

app.controller('DashboardController', function($cookies, $http, $window, $modal, $scope, $interval, dashboardService, testService) {
	
	var self = this;
	this.testsSuites;
	this.retryText = "Retry"
	this.isLoadingRetry = false;
	this.isLoadingPage;
	this.testService = testService;
	
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
			    },
				refreshGrid: function() {
					return true
				}
	       }
		});
	}
	
	this.retry = function(testSuite) {
		testSuite.isLoadingRetry = true;
		dashboardService.retry(testSuite.testSuiteID, function(data){
			self.testService.getTestSuiteByID(data.index, function(testSuiteByID){
				self.getTestsSuites();
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
							return testSuiteByID
						},
						refreshGrid: function() {
							return true
						}
					}
				});
			})
		}, function(data){
			testSuite.isLoadingRetry = false;
			testSuite.error = true;
		});
	}
});
