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

	<%@include file="/views/jsp/import/import-jquery.jsp" %>
	<%@include file="/views/jsp/import/import-angular.jsp" %>
	<%@include file="/views/jsp/import/import-bootstrap.jsp" %>
	<%@include file="/views/jsp/import/import-entities.jsp" %>

	<%@include file="/views/jsp/import/module/import-directive.jsp" %>
	<%@include file="/views/jsp/import/module/import-login.jsp" %>
	<%@include file="/views/jsp/import/module/import-configuration_modal.jsp" %>
	<%@include file="/views/jsp/import/module/import-advanced_configuration.jsp" %>
	<%@include file="/views/jsp/import/module/import-parameters.jsp" %>
	<%@include file="/views/jsp/import/module/import-home.jsp" %>
	<%@include file="/views/jsp/import/module/import-dashboard.jsp" %>

	<!-- MY CSS -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/home_page/test.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dashboard/dashboard.css">
	
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