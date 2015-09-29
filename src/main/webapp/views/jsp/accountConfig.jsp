<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html lang="en" ng-app="accountApp">

<head>
<meta charset="utf-8">
<title>Recetas en Angular</title>
<head>

<!-- JQUERY -->
<script src="node_modules/jquery/dist/jquery.min.js"></script>

<!-- BOOTSTRAP -->
<link rel="stylesheet" href="node_modules/bootstrap/dist/css/bootstrap.min.css">
<script src="node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
<link href="node_modules/bootstrap/dist/css/bootstrap.css" rel="stylesheet">
<script src="node_modules/underscore/underscore-min.js"></script>

<!-- ANGULAR -->
<script src="node_modules/angular/angular.js"></script>
<script src="node_modules/angular-animate/angular-animate.js"></script>
<script src="node_modules/angular-cookies/angular-cookies.js"></script>

<script src="node_modules/angular-ui-bootstrap/ui-bootstrap-tpls.js"></script>

<!-- MY CSS -->
<link rel="stylesheet" href="resources/css/account_config/accountConfig.css">

</head>
<body data-ng-controller="AccountController as accountCtrl">
	<account-config-content/>
</body>
<script src="resources/js/entity/accountConfig.js"></script>
<script src="resources/js/account_config/accountController.js"></script>
<script src="resources/js/account_config/accountService.js"></script>
<script src="resources/js/account_config/accountDirectives.js"></script>
</html>