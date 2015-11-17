'use strict';

var app = angular.module('TestSuiteApp',  [ 'ngAnimate', 'ui.bootstrap', 'ngCookies', 'testApp']).config(function($locationProvider) {
    $locationProvider.html5Mode({
    	  enabled: true,
    	  requireBase: false
    	});
});

app.controller('TestViewController', function($location, $cookies, $http, $window, $modal, $scope, $interval, testService) {
	
	var self = this
	this.testDetails
	this.tsService = testService
	
	this.getTestDetails = function(){
		self.tsService.getTestSuiteByID($location.search().id, function(data){
			self.testDetails = data;
		})
	};
	this.getTestDetails();
	
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
					return false;
				}
	       }
		});
	}
	
});
