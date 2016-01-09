<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en" ng-app="testPlanListApp">

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
	
	<!-- Entities -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/entity/testSuiteRunner.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/entity/testPlan.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/entity/testSuite.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/entity/environment.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/entity/test.js"></script>
	
	<!-- Directive -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/directive/directiveApp.js"></script>

	<!-- Configuration modal -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/entity/accountConfig.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/account_config/accountController.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/account_config/accountService.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/account_config/accountDirectives.js"></script>
	
	<!-- Login -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/login/loginController.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/login/loginService.js"></script>
	
	<!-- Advanced module -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/configuration/configurationController.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/configuration/configurationService.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/directive/fieldDirectives.js"></script>
	
	<!-- Parameters Modal -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/parameters/parametersController.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/parameters/parametersService.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/parameters/parametersDirectives.js"></script>
	
	<!-- Home -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/home_page/homeController.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/home_page/homeService.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/home_page/homeDirectives.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/directive/viewOfRun.js"></script>

	<!-- Test  -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/test/test/testController.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/test/test/testService.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/test/test/testDirectives.js"></script>
	
	<!-- Test Suite-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/test/testSuite/testSuiteController.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/test/testSuite/testSuiteService.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/test/testSuite/testSuiteDirectives.js"></script>
	
	<!-- Test Plan  -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/test/testPlan/testPlanController.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/test/testPlan/testPlanService.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/test/testPlan/testPlanDirectives.js"></script>
	
	<!-- Test Plan List  -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/test/testPlan/testPlanListController.js"></script>
	
</head>

<body data-ng-controller="testPlanListController as controller">
	<div ng-if="!controller.isLoading">	
	<table class="table table-striped table-hover table-condensed">
		<tbody>
			<tr>
				<th>Test Plan Name</th>
				<th>View</th>
			</tr>
			<tr data-ng-repeat="testPlan in controller.testPlans" class="animate-repeat">
				<td class="col-sm-1"><span ng-bind="testPlan.name"></span></td>
				<td class="col-sm-4"><a ng-click="controller.goTo('testPlan?id='+testPlan.testPlanID)" id="viewTestPlanLink">View Test Plan</a></td>
			</tr>
		</tbody>
	</table>
	<a ng-click="controller.goTo('/testPlan')" id="createPersonPlanLink">Create Test Plan</a>
	</div>
	<img class="centered img-30" ng-if="controller.isLoading" alt="Loading" src="${pageContext.request.contextPath}/resources/gif/loader.gif">
</body>
</html>