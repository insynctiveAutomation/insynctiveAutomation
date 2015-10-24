'use strict';

var app = angular.module('testApp', [ 'ngAnimate', 'ui.bootstrap', 'ngCookies', 'accountApp', 'parameterApp']);

app.controller('TestController', function($cookies, $http, $window, $modal, $scope, $interval, testService, accountService) {
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
		testService.getTestsSuites(function(data) {
			self.testsSuites = _.map(data,self.split);
		});
	};
	this.getTestsSuites();
	
	this.addParamObject = function(includeMethod) {
		includeMethod.paramObject = self.paramObject;
	}
	
	/* On change TestSuite Combo */
	this.getTestDetails = function(testSuiteValue){
		testService.getTestDetails(testSuiteValue, function(data) {
			self.testDetails = data;
			_.map(self.testDetails.includeMethods, self.addParamObject);
			self.getTestsStatus();
		});
	};
	
	/* On Start Button */
	this.startTest = function(testSuiteValue, selectedEnvironment, selectedBrowser) {
		self.start = true;
		self.runStatus = "Running..";
		self.videoLink = "";
		self.loaderVisible = "visible";
		testService.startTest(self.testDetails, testSuiteValue, selectedEnvironment, selectedBrowser, function(data) {
			
			self.runStatus = "The test Start!";
			self.tlaIndex = data.index;
//			self.loaderVisible = "hidden";
			
			testService.getVideoLink(self.tlaIndex, function(data) {
				self.videoLink = data;
			});
			
		}, function(data){
			data;
		});
	};
	
	/* Get Status of the Test */
	this.getTestsStatus = function(){
		if(self.testDetails && self.testSuiteValue){
			testService.getTestsStatus(self.testSuiteValue, function(data) {
				self.testStatus = data;
				self.updateStatus();
			});
		}
	};
	
	/* On Clear Button */
	this.clearTests = function(){
		if(self.tlaIndex){
			testService.clearTests(self.tlaIndex, function(data) {
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
		testService.logout(function(data) {
			$cookies.remove('userID');
			$window.location.href = '/login';
		});
	}
	
	/* Private Methods */
	this.transformarATestSuite = function(jsonTestSuite) {
		return TestSuite.asTestSuite(jsonTestSuite);
	};
	
	this.split = function(jsonTestSuiteName) {
		return jsonTestSuiteName.split(".")[0];
	};
	
	this.updateStatus = function(){
		_.map(self.testDetails.includeMethods, self.getMethodStatus)
	};
	
	this.getMethodStatus = function(method){
		var passedTestsNames = self.testStatus.passedTests.map(function(test){return test.name});
		var failedTestsNames = self.testStatus.failedTests.map(function(test){return test.name});
		var skipedTestsNames = self.testStatus.skipedTests.map(function(test){return test.name});
		
		if(passedTestsNames.indexOf(method.name) != -1){
			method.status = "SUCCESS"
		} else if(failedTestsNames.indexOf(method.name) != -1){
			method.status = "FAILED"
		} else if(skipedTestsNames.indexOf(method.name) != -1){
			method.status = "SKIPED"
		} else {
			method.status = "NOT RUN"
		}
	};

	/* Intervar 3 segs */
	this.getTestsStatus();
	$interval(function() {
		if(self.testDetails && self.tlaIndex) {
			testService.getTestsStatus(self.tlaIndex, function(data) {
				self.testStatus = data;
				self.updateStatus();
			})
		}
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
					return self.testDetails.className;
				}
	       }
		});
	}
	
});
