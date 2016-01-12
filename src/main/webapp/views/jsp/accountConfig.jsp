<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html lang="en" ng-app="accountApp">

<head>
<meta charset="utf-8">
<title>Recetas en Angular</title>
<head>
	
	<%@include file="/views/jsp/import/import-jquery.jsp" %>
	<%@include file="/views/jsp/import/import-angular.jsp" %>
	<%@include file="/views/jsp/import/import-bootstrap.jsp" %>
	<%@include file="/views/jsp/import/import-entities.jsp" %>
	<%@include file="/views/jsp/import/module/import-configuration_modal.jsp" %>
	
	<!-- MY CSS -->
	<link rel="stylesheet" href="resources/css/account_config/accountConfig.css">
	
</head>
<body data-ng-controller="AccountController as accountCtrl">
	<account-config-content/>
</body>
</html>