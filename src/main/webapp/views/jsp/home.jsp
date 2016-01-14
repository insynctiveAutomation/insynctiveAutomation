<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en" ng-app="homeApp">

<head>
<meta charset="utf-8">
<title>Automated Tests Project</title>
<head>
	<%@include file="/views/jsp/import/import-jquery.jsp" %>
	<%@include file="/views/jsp/import/import-angular.jsp" %>
	<%@include file="/views/jsp/import/import-bootstrap.jsp" %>
	<%@include file="/views/jsp/import/import-entities.jsp" %>

	<%@include file="/views/jsp/import/module/import-login.jsp" %>
	<%@include file="/views/jsp/import/module/import-test.jsp" %>
	<%@include file="/views/jsp/import/module/import-configuration_modal.jsp" %>
	<%@include file="/views/jsp/import/module/import-advanced_configuration.jsp" %>
	<%@include file="/views/jsp/import/module/import-parameters.jsp" %>
	<%@include file="/views/jsp/import/module/import-directive.jsp" %>
	<%@include file="/views/jsp/import/module/import-home.jsp" %>
	
	<!-- MY CSS -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/home_page/test.css">
</head>

<body data-ng-controller="HomeController as homeCtrl">
	<div ng-if="homeCtrl.isLogin">
		<div class="top-right-corner h4 float-right">
			<a id="configurationLink" ng-click="homeCtrl.openConfig()">Configuration</a></br>
			<a href="/dashboard" id="dashboardLink">Dashboard</a></br>
			<a href="/listTestPlan" id="testPlansLink">Test Plans</a></br>
		</div>
		<div class="top-left-corner h4"><a id="configurationLink" ng-click="homeCtrl.logout()">Logout</a></div>
		<div ng-show="homeCtrl.showPanel()" class="container">
			<form  ng-submit="homeCtrl.startTest(testSuiteName, selectedEnvironment, selectedBrowser, isNotification, remote)" class="form-signin" name="sendTest">
		
		  	<select name="testSuite" ng-model="testSuiteName" data-ng-options="testSuite as testSuite for testSuite in homeCtrl.testsSuites" ng-change="homeCtrl.getTestDetails(testSuiteName)" required>
		    	<option value="">Select a Test Suite</option>
		 	</select>
		  	<select name="environment" ng-model="selectedEnvironment" data-ng-options="environment as environment for environment in homeCtrl.environments" required>
		    	<option value="">Select an Environment</option>
		 	</select> 
		  	<select name="browser" ng-model="selectedBrowser" data-ng-options="browser.value as browser.text for browser in homeCtrl.browsers" required>
		    	<option value="">Select a Browser</option>
		 	</select> 
			<select name="notification" ng-model="isNotification" ng-options="o.v as o.n for o in [{ n: 'Notify in Slack', v: true }, { n: 'Do not notify', v: false }]" required>
				<option value="">Notification</option>
			</select>
			<select name="remote" ng-model="remote" ng-options="o.v as o.n for o in [{ n: 'Remote', v: true }, { n: 'Local', v: false }]" required>
				<option value="">Run in</option>
			</select>
		
			<button ng-disabled="sendTest.$error.required || !homeCtrl.isTestLoaded" class="btn btn-lg btn-primary" type="submit">Run Test</button>
			</form>	
		</div>
	</div>
	<div class="testContainer">
		
		<!-- VIEW OF RUN -->
		<div class="testRunning">
			<h3>{{testDetails.testSuiteName}} <small>{{testDetails.className}}</small></h3>
			<span class="started"> {{homeCtrl.runStatus}}</span>
			<img alt="loading" src="${pageContext.request.contextPath}/resources/gif/loader.gif" ng-class="[homeCtrl.loaderVisible]"><br/>
			<a ng-if="homeCtrl.videoLink" ng-href="{{homeCtrl.videoLink}}" class="video" target="_blank"> Watch Video </a>
			<view-of-Run model="homeCtrl.testDetails" controller="homeCtrl" test-parameter-text="View/Edit Parameters" ></view-of-Run>
			<button ng-if="homeCtrl.runStatus" ng-click="homeCtrl.clearTests()" class="btn btn-lg btn-primary">Clear Test!</button>	
		</div>
	<div class="alert alert-danger animate-repeat" ng-repeat="error in homeCtrl.errors">{{error}}</div>
</body>
</html>

