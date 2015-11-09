'use strict';

var app = angular.module('dashboardApp');

app.controller('ViewController', function($cookies, $http, $window, $modal, $scope, $interval, dashboardService, testSuite) {
	
	var self = this;
	this.testsSuites;
	
	this.isOneInNotRun = function(){
		var statuses = self.testDetails.tests.map(function(test){return test.status})
		return statuses.indexOf("NOT RUN") != -1 || !statuses.indexOf("-") != -1
	}

	dashboardService.retry(testSuite.testSuiteID, function(data){
		self.index = data.index;
	});
	
	/* Intervar 3 segs */
	$interval(function() {
		if(self.testDetails && self.index && self.isOneInNotRun()) {
			testService.getTestsStatus(self.index, function(data) {
				self.testStatus = data;
				self.updateStatus();
			})
		}
	}, 3000);
	
});