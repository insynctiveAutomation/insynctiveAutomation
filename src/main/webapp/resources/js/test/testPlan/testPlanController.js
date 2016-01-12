'use strict';

var app = angular.module('testPlanApp', [ 'ngAnimate', 'ui.sortable', 'ui.bootstrap', 'ngCookies', 'testSuiteApp', 'utilApp']).config(function($locationProvider) {
	 $locationProvider.html5Mode({
	   	  enabled: true,
	   	  requireBase: false
	   	})
	});

app.controller('testPlanController', function($cookies, $scope, $window, $modal, $location, $compile, testPlanService, testSuiteService, bootboxService) {

	var self = this;
	this.isLoading = true;
	this.testPlanID = $location.search().id
	this.environments = Environment.getEnvironments();
	this.message = '';
	this.isImporting = false;
	
	this.importLabel = undefined;
	this.saveLabel = undefined;
	
	
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
		self.saveLabel = 'Saving...';
		testPlanService.saveTestPlan(self.testPlan, function(data){
			self.saveLabel = 'Saved!';
			setTimeout(function(){
					self.saveLabel = undefined;
					$scope.$apply();
				}, 3000);
			self.testPlan = data;
		}, function(data){
			self.message = 'Error => '+data;
		}); 
	};
	
	this.remove = function(){
		bootboxService.removeDialog("Remove Test Plan", "Are you sure you want to remove the Test Plan "+self.testPlan.name+"?", function(result){
			if(result){
				testPlanService.removeTestPlan(self.testPlan, function(data){
					$window.location.href = '/';
				}, function(data){
					self.message = 'Error => '+data;
				}); 
			}
		})
	};
	
	this.copy = function(index){
		var copyTSRunner = {}
		angular.copy(self.testPlan.testSuiteRunners[index], copyTSRunner)
		
		self.testPlan.testSuiteRunners.push(TestSuiteRunner.makeNew(copyTSRunner))
		
	};
	
	this.addNewTestSuite = function(){
		self.testPlan.testSuiteRunners.push(new TestSuiteRunner());
	}
	
	this.importTestSuite = function(){
		self.importLabel = "Importing..";
		testSuiteService.findAllTestSuites(function(data) {
			self.importLabel = undefined;
			bootboxService.listSelection($scope, data, "Select a Test Suite", function(ts){
				var tsRunner = new TestSuiteRunner();
				tsRunner.testSuite = ts;
				self.testPlan.testSuiteRunners.push(TestSuiteRunner.makeNew(tsRunner))
				$scope.$apply();
			});
		});
	}
	
	this.removeTestSuite = function(index){
		bootboxService.removeDialog("Remove Test Suite", "Are you sure you want to remove "+self.testPlan.testSuiteRunners[index].testSuite.testSuiteName+"?", function(result){
			if(result) {
				self.testPlan.testSuiteRunners.splice(index, 1)
				$scope.$apply();
			}
		})
	}
	
	this.getPossibleDependentIndex = function(index){
		var indexs = [];
		for (var i = 0; i < self.testPlan.testSuiteRunners.length; i++) {
			var tsIterate = self.testPlan.testSuiteRunners[i].testSuite;
			var ts = self.testPlan.testSuiteRunners[index].testSuite;
			if(i != index  && self.doNotHaveCicleDependent(ts, tsIterate)){
				indexs.push(i);
			}
		}
		return indexs;
	}
	
	this.doNotHaveMeDepends = function(ts, tsIterate){
		return (tsIterate === undefined || tsIterate === null ||  tsIterate.dependsTestSuite === undefined || tsIterate.dependsTestSuite === null) || (tsIterate.dependsTestSuite.testSuiteID != ts.testSuiteID);  
	}
	
	this.doNotHaveCicleDependent = function(ts, tsIterate){
		return self.doNotHaveMeDepends(ts, tsIterate) && ((tsIterate.dependsTestSuite === undefined || tsIterate.dependsTestSuite === null) || self.doNotHaveCicleDependent(ts, tsIterate.dependsTestSuite));
	}
	
	this.addDependTS = function(ts){
		var isNull = ts.dependsTestSuiteIndex === null || ts.dependsTestSuiteIndex === undefined
		
		ts.dependsTestSuite = !isNull ? self.testPlan.testSuiteRunners[ts.dependsTestSuiteIndex].testSuite : undefined;
	}
	
	this.findIndexOf = function(ts){
		if(ts){
			return self.testPlan.testSuiteRunners.findIndex(function(elem){return elem.testSuite.testSuiteID === ts.testSuiteID})
		} else {
			return null
		}
	}
	
	this.addTestSuiteDependentIndex = function(){
		if(self.testPlan){
			for (var i = 0; i < self.testPlan.testSuiteRunners.length; i++) {
				var index = self.findIndexOf(self.testPlan.testSuiteRunners[i].testSuite.dependsTestSuite)
				self.testPlan.testSuiteRunners[i].testSuite.dependsTestSuiteIndex = index; 
			}
		}
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
});