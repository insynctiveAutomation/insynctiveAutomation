<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en" ng-app="testPlanListApp">

<head>
<meta charset="utf-8">
<title>Automated Tests Project</title>
<head>
	
	<%@include file="/views/jsp/import/import-jquery.jsp" %>
	<%@include file="/views/jsp/import/import-angular.jsp" %>
	<%@include file="/views/jsp/import/import-bootstrap.jsp" %>
	<%@include file="/views/jsp/import/import-entities.jsp" %>

	<%@include file="/views/jsp/import/module/import-directive.jsp" %>
	<%@include file="/views/jsp/import/module/import-configuration_modal.jsp" %>
	<%@include file="/views/jsp/import/module/import-login.jsp" %>
	<%@include file="/views/jsp/import/module/import-advanced_configuration.jsp" %>
	<%@include file="/views/jsp/import/module/import-parameters.jsp" %>
	<%@include file="/views/jsp/import/module/import-home.jsp" %>
	
	<%@include file="/views/jsp/import/module/import-test.jsp" %>
	<%@include file="/views/jsp/import/module/import-test_suite.jsp" %>
	<%@include file="/views/jsp/import/module/import-test_plan.jsp" %>
	<%@include file="/views/jsp/import/module/import-test_plan_list.jsp" %>
	
	<!-- MY CSS -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/home_page/test.css">
	
</head>

<body data-ng-controller="testPlanListController as controller">
	<div ng-if="!controller.isLoading">	
	<table class="table table-striped table-hover table-condensed">
		<tbody>
			<tr>
				<th>Test Plan Name</th>
				<th>View</th>
				<th>Run</th>
				<th></th>
			</tr>
			<tr data-ng-repeat="testPlan in controller.testPlans" class="animate-repeat">
				<td class="col-sm-1"><span ng-bind="testPlan.name"></span></td>
				<td class="col-sm-1"><a ng-click="controller.goTo($event, 'testPlan?id='+testPlan.testPlanID)" id="viewTestPlanLink">View Test Plan</a></td>
				<td class="col-sm-1"><a ng-click="controller.runTestPlan(testPlan)" id="runTP">Run</a></td>
				<td class="col-sm-1"><span ng-bind="testPlan.message"></span></td>
			</tr>
		</tbody>
	</table>
	<a ng-click="controller.goTo($event, '/testPlan')" id="createPersonPlanLink">Create Test Plan</a>
	</div>
	<img class="centered img-30" ng-if="controller.isLoading" alt="Loading" src="${pageContext.request.contextPath}/resources/gif/loader.gif">
</body>
</html>