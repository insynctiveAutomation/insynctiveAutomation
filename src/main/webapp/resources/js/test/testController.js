'use strict';

var app = angular.module('testEntityApp', [ 'ngAnimate', 'ui.bootstrap', 'ngCookies', 'parameterApp', 'testApp', 'directiveApp']).config(function($locationProvider) {
	 $locationProvider.html5Mode({
   	  enabled: true,
   	  requireBase: false
   	})
});

app.controller('testController', function($cookies, $scope, $window, $modal, $location, $injector, $modalInstance, testService, test) {

	var self = this;
	this.injectTestID;
	this.test = test;
	
	this.classes = []
	
	this.testsFromClass = [];

	
	this.getTestFromClass = function(){
		testService.getTestFromClass(self.test.className, function(data){
			if(self.test.className) self.testsFromClass = data.sort();
		})
	}
	
	this.getClasses = function(){
		testService.getClasses(function(data){
			self.classes = data.sort()
			self.getTestFromClass(self.test.testName)
		})
	}
	
	//Init Methods
	this.getClasses();
	
	this.save = function(){
		testService.saveTest(self.test, function(data){
			self.message = 'success';
			$modalInstance.close()
		}, function(data){
			self.message = 'Error => '+data;
		}); 
	};
	
	//On Edit Parameters click
	this.openEditParameters = function() {
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
			          return self.test
			    },
				defaulObject: function () {
					return self.test.paramObject
				},
				className: function() {
					return self.test.className;
				},
				editable: function() {
					return true;
				}
	       }
		});
	}
	
});