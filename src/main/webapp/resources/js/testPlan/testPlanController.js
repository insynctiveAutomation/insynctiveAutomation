'use strict';

var app = angular.module('testPlanApp', [ 'ngAnimate', 'ui.bootstrap', 'ngCookies']);

app.controller('testPlanController', function($cookies, $scope, $window, testPlanService) {

	var self = this;
	this.testSuites = [];
	this.selectTestSuit
	
	/*On Load Methods*/
	this.getTestSuites = function() {
		testPlanService.findTestSuites(function(data){
			self.testSuites = data;
		});
	};
	this.getTestSuites();
});