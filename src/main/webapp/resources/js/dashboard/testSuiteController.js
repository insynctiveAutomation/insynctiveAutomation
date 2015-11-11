'use strict';

var app = angular.module('dashboardApp');

app.controller('TestSuiteController', function($cookies, $http, $window, $modal, $scope, $interval, testDetails, refreshGrid, testService, testParameterText, editable) {
	var self = this;
	this.testDetails = testDetails;
	this.refreshGrid = refreshGrid;
	this.editable = editable;
	$scope.testDetails = testDetails;
	$scope.testParameterText = testParameterText;
	
	
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
					return self.testDetails.className;
				},
				editable: function() {
					return self.editable;
				}
	       }
		});
	}
	
	//DUPLICATED CODE
	this.updateStatus = function(){
		_.map(self.testDetails.tests, self.getMethodStatus)
	};
	
	//DUPLICATED CODE
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
	
	//DUPLICATED CODE
	this.isOneInNotRun = function(){
		var statuses = self.testDetails.tests.map(function(test){return test.status})
		return statuses.indexOf("NOT RUN") != -1 || statuses.indexOf("-") != -1
//		return true
	};
	
	this.checkStatus = function(){
		testService.getTestsStatus(self.testDetails.testSuiteID, function(data) {
			self.testStatus = data;
			self.updateStatus();
		}
	)};
		
	this.getTestsStatus = function(){
		if(self.refreshGrid && self.isOneInNotRun()){
			self.checkStatus();
		}
	};
		
	/* Intervar 3 segs DUPLICATED CODE*/
	$interval(function() {
		self.getTestsStatus();
	}, 3000);
	
});
