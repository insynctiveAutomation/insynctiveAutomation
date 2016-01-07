'use strict';

var app = angular.module('testApp');

app.service('testService', function($http) {
	
	this.findTest = function(id, callback, error) {
		$http.get('test/'+id).success(callback).error(error);
	};
	
	this.saveTest = function(test, callback, error) {
		$http.post('test/',test).success(callback).error(error);
	};
	
	this.getClasses = function(callback){
		$http.get('/testclasses').success(callback);
	}
	
	this.getTestFromClass = function(clazz, callback){
		$http.get('/view/test?className='+clazz).success(callback);
	}
});