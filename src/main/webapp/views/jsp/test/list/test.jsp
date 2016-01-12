<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en" ng-app="testApp">

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
	
	<!-- MY CSS -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/home_page/test.css">
	
</head>

<body data-ng-controller="testController as controller">
	<form ng-submit="controller.save()" name="form" id="form">
		<test controller="controller"></test>
		<button class="center-block btn btn-primary btn-block" ng-disabled="form.$invalid" type="submit">Save</button>
	</form>
</body>
</html>