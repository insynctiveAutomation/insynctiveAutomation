'use strict';

var app = angular.module('testSuiteApp', [ 'ngAnimate', 'ui.bootstrap', 'ngCookies', 'parameterApp', 'homeApp', 'testApp']).config(function($locationProvider) {
	 $locationProvider.html5Mode({
   	  enabled: true,
   	  requireBase: false
   	})
});

app.controller('testSuiteController', function($cookies, $scope, $window, $modal, $location, testSuiteService, testService, testSuite) {

	var self = this;
	
	this.testSuite = testSuite;
	
	this.save = function(){
		testSuiteService.saveTestSuite(self.testSuite, function(data){
			self.message = 'success';
		}, function(data){
			self.message = 'Error => '+data;
		}); 
	};
	
	this.addTest = function(){
		self.testSuite.tests.push(new Test(self.testSuite));
	}
	
	this.removeTest = function(test){
		self.testSuite.tests.pop(test);
	}
	
	//On Edit Parameters click
	this.openEditTest = function(test) {
		var modalInstance = $modal.open({
			animation : true,
			controller: 'testController',
			controllerAs: 'controller',
			template : '<test-view controller="controller"></test-view>',
			backdrop: true,
			windowClass: 'edit-parameter-modal',
			size : 'lg',
			resolve : {
				test: function () {
			          return test
				}
			}
		});
	}
	
});