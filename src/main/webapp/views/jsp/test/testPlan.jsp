<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en" ng-app="testPlanApp">

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
	
	<!-- MY CSS -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/home_page/test.css">
	
</head>

<body data-ng-controller="testPlanController as controller">
	<div ng-hide="controller.isLoading">
		<form name="form" id="form">
			<test-plan controller="controller"></test-plan>
			<button ng-click="controller.save($event)" class="center-block btn btn-primary btn-block" ng-disabled="form.$invalid" type="submit"><span ng-if="controller.saveLabel">{{controller.saveLabel}}</span><span ng-if="!controller.saveLabel">Save</span></button>
		</form>
		<span ng-bind="controller.message"></span>
		<br>
		<button class="btn-danger" ng-show="controller.testPlan.testPlanID" ng-click="controller.remove()">Remove</button>
	</div>
	<img class="centered img-30" ng-hide="!controller.isLoading" alt="Loading" src="${pageContext.request.contextPath}/resources/gif/loader.gif">
</body>

</html>