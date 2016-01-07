'use strict';

var app = angular.module('loginApp', [ 'ngAnimate', 'ui.bootstrap', 'ngCookies']);

app.controller('loginController', function($cookies, $scope, $window, loginService) {

	var self = this;
	this.username;
	this.password;
	this.loginNotification;
	$scope.disabled = false;
	
	this.login = function() {
		$scope.disabled = true;
		loginService.login({username : self.username, password : self.password}, function(data) {
			$cookies.put('userID', data.accID);
			$window.location.href = $window.location.pathname === '/login' ? '/' : $window.location.href;
		}, function(data){
			self.loginNotification = 'Error on log in'
			self.notification = data;
			$scope.disabled = false;
		});
	};
	
	this.logout = function(){
		loginService.logout(function(data) {
			$cookies.remove('userID');
			$window.location.href = '/login';
		});
	}
});