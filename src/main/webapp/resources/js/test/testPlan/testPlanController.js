'use strict';

var app = angular.module('testPlanApp', [ 'ngAnimate', 'ui.bootstrap', 'ngCookies', 'testSuiteApp']).config(function($locationProvider) {
	 $locationProvider.html5Mode({
	   	  enabled: true,
	   	  requireBase: false
	   	})
	});

app.controller('testPlanController', function($cookies, $scope, $window, $modal, $location, testPlanService) {

	var self = this;
	this.isLoading = true;
	this.testPlanID = $location.search().id
	this.environments = Environment.getEnvironments();
	this.message = '';
	
	
	
	/*On Load Methods*/
	this.getTestPlanByID = function(id) {
		if(id){
			testPlanService.findTestPlan(id, function(data){
				self.testPlan = data;
				self.addTestSuiteDependentIndex();
				self.isLoading = false;
			}, function(data){
				self.message = 'Error =>'+data;
			});
		} else {
			self.isLoading = false;
			self.testPlan = new TestPlan();
		}
	};
	this.getTestPlanByID(this.testPlanID);
	
	this.save = function(){
		testPlanService.saveTestPlan(self.testPlan, function(data){
			self.message = 'Saved!';
		}, function(data){
			self.message = 'Error => '+data;
		}); 
	};
	
	this.remove = function(){
		testPlanService.removeTestPlan(self.testPlan, function(data){
			$window.location.href = '/';
		}, function(data){
			self.message = 'Error => '+data;
		}); 
	};
	
	this.addTestSuite = function(){
		self.testPlan.testSuiteRunners.push(new TestSuiteRunner());
	}
	
	this.removeTestSuite = function(testSuite){
		self.testPlan.testSuites.pop(testSuite);
	}
	
	//On Edit Parameters click
	this.openEditTestSuite = function(testSuite) {
		var modalInstance = $modal.open({
			animation : true,
			controller: 'testSuiteController',
			controllerAs: 'controller',
			template : '<test-suite controller="controller"></test-suite>',
			backdrop: true,
			windowClass: 'edit-parameter-modal',
			size : 'lg',
			resolve : {
				testSuite: function () {
			          return testSuite
				}
			}
		});
	}
	
	this.getIndexOfTestSuites = function(index){
		var indexs = [];
		for (var i = 0; i < self.testPlan.testSuiteRunners.length; i++) {
			var tsIterate = self.testPlan.testSuiteRunners[i].testSuite;
			var ts = self.testPlan.testSuiteRunners[index].testSuite;
			
			if(i != index  && self.doNotHaveMeDepends(ts, tsIterate)){
				indexs.push(i);
			}
		}
		return indexs;
	}
	
	this.doNotHaveMeDepends = function(ts, tsIterate){
		return !tsIterate.dependsTestSuite || tsIterate.dependsTestSuite === null || tsIterate.dependsTestSuite.testSuiteID != ts.testSuiteID;  
	}
	
	this.addDependTS = function(ts){
		ts.dependsTestSuite = ts.dependsTestSuiteIndex ? self.testPlan.testSuiteRunners[ts.dependsTestSuiteIndex].testSuite : undefined;
	}
	
	this.findIndexOf = function(ts){
		if(ts){
			return self.testPlan.testSuiteRunners.findIndex(function(elem){return elem.testSuite.testSuiteID === ts.testSuiteID})
		} else {
			return null
		}
	}
	
	this.addTestSuiteDependentIndex = function(){
		for (var i = 0; i < self.testPlan.testSuiteRunners.length; i++) {
			var index = self.findIndexOf(self.testPlan.testSuiteRunners[i].testSuite.dependsTestSuite)
			self.testPlan.testSuiteRunners[i].testSuite.dependsTestSuiteIndex = index; 
		}
	}
});