<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en" ng-app="loginApp">
 
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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/login/login.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/account_config/accountConfig.css">

<!-- MODAL -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/entity/accountConfig.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/login/loginController.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/login/loginService.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/login/loginDirectives.js"></script>

</head>

<body data-ng-controller="LoginController as loginController">
	<div>
		<form  ng-submit="loginController.login()" class="form-signin" name="sendTest">
		<h2 class="form-signin-heading">Please, sign in.</h2>
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