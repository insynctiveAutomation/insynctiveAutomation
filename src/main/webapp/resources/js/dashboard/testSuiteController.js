'use strict';

var app = angular.module('dashboardApp');

app.controller('TestSuiteController', function($cookies, $http, $window, $modal, $scope, $interval, testDetails) {
	var self = this;
	this.testDetails = testDetails;
	$scope.testDetails = testDetails;
	
	
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
				}
	       }
		});
	}
	
});
