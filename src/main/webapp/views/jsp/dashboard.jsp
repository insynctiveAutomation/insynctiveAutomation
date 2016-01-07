<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en" ng-app="dashboardApp">

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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dashboard/dashboard.css">

<!-- Login -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/login/loginController.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/login/loginService.js"></script>
	
<!-- Entities -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/entity/testSuite.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/entity/environment.js"></script>

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
 	
<!-- Account Modal -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/account_config/accountController.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/account_config/accountService.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/account_config/accountDirectives.js"></script>

<!-- Test -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/home_page/homeController.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/home_page/homeService.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/home_page/homeDirectives.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/directive/directiveApp.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/directive/viewOfRun.js"></script>

<!-- Dashboard -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/dashboard/dashboardController.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/dashboard/dashboardTestSuiteController.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/dashboard/dashboardService.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/dashboard/dashboardDirectives.js"></script>

</head>
<body data-ng-controller="DashboardController as testCtrl">
	<img ng-if="testCtrl.isLoadingPage" alt="loading" src="${pageContext.request.contextPath}/resources/gif/loader.gif">
	<div ng-hide="testCtrl.isLoadingPage">
		<table class="table table-striped table-hover table-condensed">
			<tbody>
				<tr>
					<th>ID</th>
					<th>Test Suite Run</th>
					<th>Status</th>
					<th>Browsers</th>
					<th>Environment</th>
					<th>Tester</th>
					<th>View</th>
					<th>Retry</th>
				</tr>
				<tr data-ng-repeat="testSuite in testCtrl.testsSuites" class="animate-repeat">
					<td class="col-sm-1"><span ng-bind="testSuite.testSuiteRunID"></span></td>
					<td class="col-sm-4"><span ng-bind="testSuite.name"></span></td>
					<td class="col-sm-1"><result ng-model="testSuite.status" bind="testSuite.status" value="{{testSuite.status}}" /></td>
					<td class="col-sm-1"><span ng-bind="testSuite.browser"></span></td>
					<td class="col-sm-1"><span ng-bind="testSuite.environment"></span></td>
					<td class="col-sm-1"><span ng-bind="testSuite.tester"></span></td>
					<td class="col-sm-3"><a id="" ng-click="testCtrl.openTestSuite(testSuite, $event)">View TestSuite</a></td>
					<td ng-if="!testSuite.isLoadingRetry && !testSuite.error" class="col-sm-3"><a id="" ng-click="testCtrl.retry(testSuite)">Retry</a></td>
					<td ng-if="testSuite.error" class="col-sm-3">ERROR ON SERVER</td>
					<td ng-if="testSuite.isLoadingRetry" class="col-sm-3"><img alt="loading" src="${pageContext.request.contextPath}/resources/gif/loader.gif"></td>
				</tr>
			</tbody>
		</table>
		<pagination ng-model="currentPage" total-items="testCtrl.testSuiteCount" max-size="numPerPage" boundary-links="true"></pagination>
	</div>
</body>
</html>