'use strict';

var app = angular.module('dashboardApp', [ 'ngAnimate', 'ui.bootstrap', 'ngCookies', 'testApp']);

app.controller('DashboardController', function($cookies, $http, $window, $modal, $scope, $interval, dashboardService, testService) {
	
	$scope.currentPage = 1;
	$scope.numPerPage = 10;
	
	var self = this;
	this.testsSuites;
	this.retryText = "Retry"
	this.isLoadingRetry = false;
	this.isLoadingPage;
	this.testService = testService;
	this.testSuiteCount = 0;
	
	$scope.$watch('currentPage', function() {
	    self.getTestsSuites();
	  });
	
	this.countTestSuites = function(){
		dashboardService.countTestSuites(function(data){
			self.testSuiteCount = data; 
		});
	}
	this.countTestSuites();
	
	this.getTestsSuites = function(){
		self.isLoadingPage = true;
		dashboardService.getTestsSuites($scope.currentPage, $scope.numPerPage, function(data){
			self.testsSuites = data;
			self.isLoadingPage = false
		});
	}
	
	this.getTestsSuites();
	
	this.openTestSuite = function(testSuite, $event) {
		if($event.ctrlKey || $event.which === 2){
			$window.open('/testSuite?id='+testSuite.testSuiteID,'_blank')
		} else {
			var modalInstance = $modal.open({
				animation : true,
				templateUrl : '/testSuiteTemplate', 
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
					},
					testParameterText: function() {
						return 'View Parameters'
					},
					editable: function() {
						return false
					}
		       }
			});
		}
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
						},
						testParameterText: function() {
							return 'View Parameters'
						},
						editable: function() {
							return false
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
