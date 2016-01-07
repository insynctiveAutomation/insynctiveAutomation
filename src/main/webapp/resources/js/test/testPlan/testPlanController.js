'use strict';

var app = angular.module('testPlanApp', [ 'ngAnimate', 'ui.bootstrap', 'ngCookies', 'testSuiteApp']).config(function($locationProvider) {
	 $locationProvider.html5Mode({
	   	  enabled: true,
	   	  requireBase: false
	   	})
	});

app.controller('testPlanController', function($cookies, $scope, $window, $modal, $location, testPlanService) {

	var self = this;
	this.testPlan = {}
	this.testPlanID = $location.search().id
	this.environments = Environment.getEnvironments();
	
	this.message = '';
	
	/*On Load Methods*/
	this.getTestPlanByID = function(id) {
		if(id){
			testPlanService.findTestPlan(id, function(data){
				self.testPlan = data;
			}, function(data){
				self.message = 'Error =>'+data;
			});
		} else {
			self.testPlan = new TestPlan();
		}
	};
	this.getTestPlanByID(this.testPlanID);
	
	this.save = function(){
		testPlanService.saveTestPlan(self.testPlan, function(data){
			self.message = 'success';
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
			template : '<test-suite-view controller="controller"></test-suite-view>',
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
	
});