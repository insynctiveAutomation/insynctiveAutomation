<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en" ng-app="loginApp">
 
<head>
<meta charset="utf-8">
<title>Automated Tests Project</title>
<head>

	<%@include file="/views/jsp/import/import-jquery.jsp" %>
	<%@include file="/views/jsp/import/import-angular.jsp" %>
	<%@include file="/views/jsp/import/import-bootstrap.jsp" %>
	<%@include file="/views/jsp/import/import-entities.jsp" %>

	<%@include file="/views/jsp/import/module/import-login.jsp" %>
	
	<!-- MY CSS -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/login/login.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/account_config/accountConfig.css">

</head>

<body data-ng-controller="loginController as loginController">
	<div>
		<form  ng-submit="loginController.login()" class="form-signin" name="sendTest">
		<h2 class="form-signin-heading">Sign in</h2>
		 	<label for="username" class="sr-only">Username: </label>
			<input ng-required="true" ng-model="loginController.username" class="form-control" placeholder="Username"/><br/> 
		 	<label for="username" class="sr-only">Password: </label>
			<input ng-required="true" ng-model="loginController.password" type="password" class="form-control" placeholder="Password"/><br/> 
			<button id="w" type="submit" class="ladda-button" data-color="blue" data-style="zoom-in" ng-disabled="disabled">
     	 		<span class="ladda-label">Log in</span>
    		</button>
    		<img alt="loading" src="${pageContext.request.contextPath}/resources/gif/loader.gif" ng-if="disabled"><br/>
			<span style="color : red;">{{loginController.loginNotification}}</span>
		</form>
	</div>	
</body>
</html>