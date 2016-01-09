'use strict';

var app = angular.module('testPlanListApp', [ 'ngAnimate', 'ui.bootstrap', 'ngCookies', 'testPlanApp']).config(function($locationProvider) {
	 $locationProvider.html5Mode({
	   	  enabled: true,
	   	  requireBase: false
	   	})
	});

app.controller('testPlanListController', function($cookies, $scope, $window, $modal, $location, testPlanService) {

	var self = this;
	this.isLoading = true;
	this.testPlans = [];
	
	this.getAllTestPlans = function(){
		testPlanService.getAllTestPlan(function(data){
			self.isLoading = false;
			self.testPlans = data;
		});
	}
	this.getAllTestPlans();
	
	this.goTo = function(path){
		$window.location.href = path;
	}
	
});