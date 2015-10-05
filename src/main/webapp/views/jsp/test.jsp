<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en" ng-app="testApp">

<head>
<meta charset="utf-8">
<title>Automated Tests Projectr</title>
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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/account_config/accountConfig.css">

<!-- MODAL -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/entity/accountConfig.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/account_config/accountController.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/account_config/accountService.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/account_config/accountDirectives.js"></script>

<!-- Test Page -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/entity/testSuite.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/entity/environment.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/home_page/testController.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/home_page/testService.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/home_page/testDirectives.js"></script>

</head>

<body data-ng-controller="TestController as testCtrl">
	<div class="topcorner h4"><a id="configurationLink" ng-click="testCtrl.openConfig()">Configuration</a></div>
	<div ng-show="testCtrl.showPanel()" class="container">
		<form  ng-submit="testCtrl.startTest(testSuiteName, selectedEnvironment)" class="form-signin" name="sendTest">
	
	  	<select name="testSuite" ng-model="testSuiteName" data-ng-options="testSuite as testSuite for testSuite in testCtrl.testsSuites" ng-change="testCtrl.getTestDetails(testSuiteName)" required>
	    	<option value="">Select a Test Suite</option>
	 	</select>
	  	<select name="environment" ng-model="selectedEnvironment" data-ng-options="environment as environment for environment in testCtrl.environments" required>
	    	<option value="">Select an Environment</option>
	 	</select> 
	 	
		<button ng-disabled="sendTest.testSuite.$error.required || sendTest.environment.$error.required" class="btn btn-lg btn-primary" type="submit">Start Test!</button>
		</form>	
	</div>
	
	<div class="testContainer">
		
		<!-- VIEW OF RUN -->
		<div class="testRunning">
			<h3>{{testCtrl.testDetails.testName}} <small>{{testCtrl.testDetails.className}}</small></h3>
			<span class="started"> {{testCtrl.runStatus}}</span>
			<img alt="loading" src="${pageContext.request.contextPath}/resources/gif/loader.gif" ng-class="[testCtrl.loaderVisible]"><br/>
			<a ng-if="testCtrl.videoLink" ng-href="{{testCtrl.videoLink}}" class="video" target="_blank"> Watch Video </a>
			<table class="table table-striped table-hover table-condensed">
				<tbody ng-if="testCtrl.testDetails">
					<tr>
						<th>Methods</th>
						<th>Status</th>
					</tr>
					<tr data-ng-repeat="method in testCtrl.testDetails.includeMethods" class="animate-repeat">
						<td class="col-sm-5"><span ng-bind="method.name"></span></td>
						<td class="col-sm-2"><result ng-model="method.status" bind="method.status" value="{{method.status}}"/></td>
					</tr>
				</tbody>
			</table>
			<button ng-if="testCtrl.runStatus" ng-click="testCtrl.clearTests()" class="btn btn-lg btn-primary">Clear Test!</button>
		</div>
	</div>
	
	<div class="alert alert-danger animate-repeat" ng-repeat="error in testCtrl.errors">{{error}}</div>
</body>
</html>
