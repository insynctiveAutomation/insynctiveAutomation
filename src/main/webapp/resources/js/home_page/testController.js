'use strict';

var app = angular.module('testApp', [ 'ngAnimate', 'ui.bootstrap', 'accountApp']);

app.controller('TestController', function($http, $modal, $scope, $timeout, $interval, $compile, testService) {
	var self = this;
	this.errors = [];
	this.testsSuites = [];
	this.runStatus;
	this.testDetails;
	this.environments = Environment.getEnvironments();
	this.testStatus = [];
	this.start = false;
	this.videoLink;
	this.loaderVisible = "hidden";
	
	/* On Load Methods */
	this.getTestsSuites = function() {
		testService.getTestsSuites(function(data) {
			self.testsSuites = _.map(data,self.split);
		});
	};
	this.getTestsSuites();

	/* On change TestSuite Combo */
	this.getTestDetails = function(testSuiteValue){
		testService.getTestDetails(testSuiteValue, function(data) {
			self.testDetails = data;
			self.getTestsStatus();
		});
	};
	
	/* On Start Button */
	this.startTest = function(testSuiteValue, selectedEnvironment) {
		self.start = true;
		self.runStatus = "Running..";
		self.videoLink = "";
		self.loaderVisible = "visible";
		testService.startTest(testSuiteValue, selectedEnvironment, function(data) {
			self.runStatus = data;
//			self.loaderVisible = "hidden";
		}, function(data){
			data;
		});
		testService.getVideoLink(function(data) {
			self.videoLink = data;
		});
	};
	
	/* Get Status of the Test */
	this.getTestsStatus = function(){
		if(self.testDetails){
			testService.getTestsStatus(function(data) {
				self.testStatus = data;
				self.updateStatus();
			});
		}
	};
	
	/* On Clear Button */
	this.clearTests = function(){
		testService.clearTests(function(data) {
			self.start = false;
			self.loaderVisible = "hidden";
			self.getTestsStatus();
			self.runStatus = '';
			self.videoLink = '';
		});
	}; this.clearTests();
	
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
		if(self.testDetails) {
			testService.getTestsStatus(function(data) {
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
			templateUrl : '/accountConfigContent',
			controller:  'AccountController',
			controllerAs: 'accountCtrl',
			
			windowClass: 'app-modal-window',
			backdrop: true,
			
			size : 'sm',
			resolve : {
			}
		});
	};
});
