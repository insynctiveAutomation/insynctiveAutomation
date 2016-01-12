'use strict';

var app = angular.module('homeApp', [ 'ngAnimate', 'ui.bootstrap', 'ngCookies', 'directiveApp', 'accountApp', 'parameterApp', 'configuration', 'loginApp']);

app.controller('HomeController', function($cookies, $http, $window, $modal, $scope, $interval, homeService, loginService, accountService) {
	var self = this;
	this.isLogin = false;
	this.paramObject = {};
	this.tlaIndex;
	this.errors = [];
	this.testsSuites = [];
	this.runStatus;
	this.testDetails;
	this.environments = Environment.getEnvironments();
	this.testStatus = [];
	this.start = false;
	this.videoLink;
	this.loaderVisible = "hidden";
	
	this.checkIfIsLogin = function(){
		if(!$cookies.get('userID')){
			$window.location.href = '/login';
		} else {
			self.isLogin = true;
		}
	}
	this.checkIfIsLogin();
	
	/* On Load Methods */
	this.getTestsSuites = function() {
		homeService.getTestsSuites(function(data) {
			self.testsSuites = data.sort();
		});
	};
	this.getTestsSuites();
	
	/* On change TestSuite Combo */
	this.getTestDetails = function(testSuiteValue){
		homeService.getTestDetails(testSuiteValue, function(data) {
			self.testDetails = data;
			self.getTestsStatus();
		});
	};
	
	/* On Start Button */
	this.startTest = function(testSuiteValue, selectedEnvironment, selectedBrowser, isNotification, remote) {
		self.start = true;
		self.runStatus = "Running..";
		self.videoLink = "";
		self.loaderVisible = "visible";
		homeService.startTest(self.testDetails, selectedEnvironment, selectedBrowser, isNotification, remote, function(data) {
			
			self.runStatus = "The Test is Running...";
			self.tlaIndex = data.index;
			self.resetTestsStatus();
			
			homeService.getVideoLink(self.tlaIndex, function(data) {
				self.videoLink = data;
			});
			
		}, function(data){
			data;
		});
	};
	
	/* On Clear Button */
	this.clearTests = function(){
		if(self.tlaIndex){
			homeService.clearTests(self.tlaIndex, function(data) {
				self.start = false;
				self.loaderVisible = "hidden";
				self.getTestsStatus();
				self.runStatus = undefined;
				self.videoLink = '';
				self.tlaIndex = undefined;
			}); 
		}
	}; this.clearTests();
	
	this.logout = function(){
		loginService.logout(function(data) {
			$cookies.remove('userID');
			$window.location.href = '/login';
		});
	}
	
	/* Private Methods */
	this.transformarATestSuite = function(jsonTestSuite) {
		return TestSuite.asTestSuite(jsonTestSuite);
	};
	
	this.updateStatus = function(){
		_.map(self.testDetails.tests, self.getMethodStatus)
	};
	
	this.getMethodStatus = function(method){
		var passedTestsNames = self.testStatus.passedTests.map(function(test){return test.testName});
		var failedTestsNames = self.testStatus.failedTests.map(function(test){return test.testName});
		var skipedTestsNames = self.testStatus.skipedTests.map(function(test){return test.testName});
		
		if(passedTestsNames.indexOf(method.testName) != -1){
			method.status = "SUCCESS"
		} else if(failedTestsNames.indexOf(method.testName) != -1){
			method.status = "FAILED"
		} else if(skipedTestsNames.indexOf(method.testName) != -1){
			method.status = "SKIPED"
		} else {
			method.status = "NOT RUN"
		}
	};

	this.resetTestsStatus = function(){
		_.map(self.testDetails.tests, function(test){
			test.status = "-";
		})
	}
	
	this.isOneInNotRun = function(){
		var statuses = self.testDetails.tests.map(function(test){return test.status})
		return statuses.indexOf("NOT RUN") != -1 || statuses.indexOf("-") != -1
	}
	
	this.checkStatus = function(){
		homeService.getTestsStatus(self.tlaIndex, function(data) {
			self.testStatus = data;
			self.updateStatus();
		})
	}
	
	/* Get Status of the Test */
	this.getTestsStatus = function(){
		if(self.testDetails && self.tlaIndex && self.isOneInNotRun()){
			self.checkStatus();
		}
	};
	
	/* Intervar 3 segs */
	this.getTestsStatus();
	$interval(function() {
		self.getTestsStatus();
	}, 3000);
	
	this.showPanel = function(){
		return !self.runStatus 
	};
	
	/* Modal Manager */
	this.openConfig = function() {
		var modalInstance = $modal.open({
			animation : true,
			templateUrl : 'accountConfigContent',
			controller:  'AccountController',
			controllerAs: 'accountCtrl',
			windowClass: 'app-modal-window',
			backdrop: true,
			
			size : 'sm',
			resolve : {
			}
		});
	};
	
	this.getConfig = function() {
		self.isLoading = true;
		accountService.getAccountConfig(function(data) {
			self.paramObject = data.paramObject;
			self.paramObject.paramObjectID = null;
		});
	};
	this.getConfig();
	
	//On View/Edit Parameters click
	this.openEditParameters = function(test) {
		var modalInstance = $modal.open({
			animation : true,
			templateUrl : 'editParameters',
			 controller: 'modalParametersController',
			 controllerAs: 'parametersCtrl',
			backdrop: true,
			windowClass: 'edit-parameter-modal',
			size : 'lg',
			resolve : {
				selectedTest: function () {
			          return test
			    },
				defaulObject: function () {
					return self.paramObject
				},
				className: function() {
					return test.className;
				},
				editable: function() {
					return true;
				}
	       }
		});
	}
});
