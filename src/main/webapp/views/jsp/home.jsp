<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en" ng-app="homeApp">

<head>
<meta charset="utf-8">
<title>Automated Tests Project</title>
<head>
	<!-- JQUERY -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/node_modules/jquery/dist/jquery.min.js"></script>
	
	<!-- BOOTSTRAP -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/node_modules/bootstrap/dist/css/bootstrap.min.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/node_modules/bootstrap/dist/css/bootstrap.css" rel="stylesheet">
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/node_modules/underscore/underscore-min.js"></script>
	
	<!-- ANGULAR -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/node_modules/angular/angular.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/node_modules/angular-animate/angular-animate.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/node_modules/angular-cookies/angular-cookies.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/node_modules/angular-ui-bootstrap/ui-bootstrap-tpls.js"></script>
	
	<!-- MY CSS -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/home_page/test.css">
	
	<!-- Login -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/login/loginController.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/login/loginService.js"></script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/test/test/testController.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/test/test/testDirectives.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/test/test/testService.js"></script>
	
	<!-- Configuration modal -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/entity/accountConfig.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/account_config/accountController.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/account_config/accountService.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/account_config/accountDirectives.js"></script>

	<!-- Advanced module -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/configuration/configurationController.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/configuration/configurationService.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/directive/fieldDirectives.js"></script>

	<!-- Parameters Modal -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/parameters/parametersController.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/parameters/parametersService.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/parameters/parametersDirectives.js"></script>
	
	<!-- Home Page -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/entity/testSuite.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/entity/environment.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/home_page/homeController.js"></script>
	<script type="text/javascript"src="${pageContext.request.contextPath}/resources/js/home_page/homeService.js"></script>
	<script type="text/javascript"src="${pageContext.request.contextPath}/resources/js/home_page/homeDirectives.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/directive/directiveApp.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/directive/viewOfRun.js"></script>
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
		  	<select name="browser" ng-model="selectedBrowser" required>
		    	<option value="">Select a Browser</option>
		    	<option value="FIREFOX">Firefox</option>
		    	<option value="CHROME">Chrome</option>
		    	<option value="IE_10">IE 10</option>
		    	<option value="IE_11">IE 11</option>
		    	<option value="IPAD">iPad</option>
		 	</select> 
			<select name="notification" ng-model="isNotification" ng-options="o.v as o.n for o in [{ n: 'Notify in Slack', v: true }, { n: 'Do not notify', v: false }]" required>
				<option value="">Notification</option>
			</select>
			<select name="remote" ng-model="remote" ng-options="o.v as o.n for o in [{ n: 'Crossbrowser', v: true }, { n: 'Local', v: false }]" required>
				<option value="">Run in</option>
			</select>
		
			<button ng-disabled="sendTest.testSuite.$error.required || sendTest.environment.$error.required" class="btn btn-lg btn-primary" type="submit">Run Test</button>
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

