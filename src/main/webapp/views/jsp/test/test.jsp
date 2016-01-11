<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en" ng-app="testApp">

<head>
<meta charset="utf-8">
<title>Automated Tests Project</title>
<head>
	<!-- JQUERY -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/node_modules/jquery/dist/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/node_modules/jquery/dist/jquery-ui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/node_modules/jquery.browser/dist/jquery.browser.js"></script>
	
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/node_modules/angular/sortable.js"></script>
	
	<!-- Entities -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/entity/testSuite.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/entity/environment.js"></script>
	
	<!-- Directive -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/directive/directiveApp.js"></script>
	
	<!-- Login -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/login/loginController.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/login/loginService.js"></script>
	
	<!-- Configuration modal -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/entity/accountConfig.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/account_config/accountController.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/account_config/accountService.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/account_config/accountDirectives.js"></script>
	
	<!-- Login -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/login/loginController.js"></script>
	
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
	
</head>

<body data-ng-controller="testController as controller">
	<form ng-submit="controller.save()" name="form" id="form">
		<test controller="controller"></test>
		<button class="center-block btn btn-primary btn-block" ng-disabled="form.$invalid" type="submit">Save</button>
	</form>
	<span ng-bind="controller.message"></span>
</body>
</html>