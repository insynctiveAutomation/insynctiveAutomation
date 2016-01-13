'use strict';

var app = angular.module('testPlanListApp', [ 'ngAnimate', 'ui.bootstrap', 'ngCookies', 'testPlanApp', 'utilApp']).config(function($locationProvider) {
	 $locationProvider.html5Mode({
	   	  enabled: true,
	   	  requireBase: false
	   	})
	});

app.controller('testPlanListController', function($cookies, $scope, $window, $modal, $location, testPlanService, bootboxService) {

	var self = this;
	this.isLoading = true;
	this.testPlans = [];
	this.message = '';
	
	this.getAllTestPlans = function(){
		testPlanService.getAllTestPlan(function(data){
			self.isLoading = false;
			self.testPlans = data.sort(function(a,b) { return (b.name < a.name) });
		});
	}
	this.getAllTestPlans();
	
	this.runTestPlan = function(tp){
		bootboxService.runTestPlanBootbox($scope, "Run "+tp.name, function(isNotification, isRemote){
			tp.message = "Running.."
			$scope.$apply();
			testPlanService.runTestPlan(tp.testPlanID, isNotification, isRemote, function(data){
				tp.message = "Runned!"
			}, function(error){
				tp.message = error;
			})
		})
	}
	
	this.goTo = function(event, path){
		Util.goTo(path, event, $window);
	}
});