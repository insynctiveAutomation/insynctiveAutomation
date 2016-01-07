<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en" ng-app="testPlanApp">

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
	src="${pageContext.request.contextPath}/resources/js/home_page/homeService.js"></script>
	src="${pageContext.request.contextPath}/resources/js/home_page/homeDirectives.js"></script>
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
	
</head>

<body data-ng-controller="testPlanController as controller">
		
		<form ng-submit="controller.save()" name="form" id="form">
			<span>Test Plan Name</span> <input type="text" ng-model="controller.testPlan.name"/>
			<table class="table table-striped table-hover table-condensed">
				<tbody>
					<tr>
						<th>Test Suite Name</th>
						<th>Browser</th>
						<th>Environment</th>
						<th>Edit</th>
						<th>Remove</th>
					</tr>
					<tr data-ng-repeat="testSuiteRunner in controller.testPlan.testSuiteRunners" class="animate-repeat">
						<td class="col-sm-2"><input type="text" ng-model="testSuiteRunner.testSuite.testSuiteName"></input></td>
						<td class="col-sm-2"><select name="environment" ng-model="testSuiteRunner.environment" data-ng-options="environment as environment for environment in controller.environments" required>
					    	<option value="">Select an Environment</option>
					 	</select> 
					 	</td>
					  	<td class="col-sm-2"><select name="browser" ng-model="testSuiteRunner.browser" required>
					    	<option value="">Select a Browser</option>
					    	<option value="FIREFOX">Firefox</option>
					    	<option value="CHROME">Chrome</option>
					    	<option value="IE_10">IE 10</option>
					    	<option value="IE_11">IE 11</option>
					    	<option value="IPAD">iPad</option>
					 	</select> 
					 	</td>
						<td class="col-sm-2"><a id="edit" ng-click="controller.openEditTestSuite(testSuiteRunner.testSuite)">Edit Test Suite</a></td>
						<td class="col-sm-2"><a id="edit" ng-click="controller.removeTest(testSuiteRunner.testSuite)">Remove Test Suite</a></td>
					</tr>
				</tbody>
			</table>
			<a class="btn" ng-click="controller.addTestSuite()">add Test Suite</a>
			<button class="center-block btn btn-primary btn-block" ng-disabled="form.$invalid" type="submit">Save</button>
		</form>
			
</body>

</html>