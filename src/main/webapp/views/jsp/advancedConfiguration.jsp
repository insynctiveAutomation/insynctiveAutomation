<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en" ng-app="configuration">
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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/configuration/configuration.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/home_page/test.css">
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/configuration/configurationController.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/configuration/configurationService.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/configuration/configurationDirectives.js"></script>
</head>

	<body data-ng-controller="configurationController as configCtl">
    <div ng-if="configCtl.isLoading" class="center-div">
    	<img alt="loading" src="${pageContext.request.contextPath}/resources/gif/loader.gif" class="loading-img">
    	<span class="loading-span">Loading</span>
    </div>
    <div ng-show="!configCtl.isLoading" class="configuration-div">
	    <div class="top-right-corner h4"><a href="/" id="configurationLink">Home</a></div>
		<div class="top-left-corner h4"><a id="logoutLink" ng-click="testCtrl.logout()">Logout</a></div>
		<br/>
	    <form ng-submit="configCtl.saveConfiguration()" name="form">
			<div class="col-xs-12 col-md-6">
				<h3>Account Login</h3>
				<label for="">RUN ID: </label>
				<input ng-required="true" ng-model="configCtl.configuration.runID" /><br/>     
				
				<label for="">Username: </label>
				<input ng-required="true" ng-model="configCtl.configuration.username" /><br/>     
				
				<label for="">Password: </label>
				<input ng-required="true" ng-model="configCtl.configuration.password" /><br/>     
			</div>
			<div class="col-xs-12 col-md-6">
				<h3>Insynctive Properties</h3>
				<label for="">Login Username: </label>
				<input ng-required="true" ng-model="configCtl.configuration.accountProperty.loginUsername" /><br/>     
				
				<label for="">Login Password: </label>
				<input ng-required="true" ng-model="configCtl.configuration.accountProperty.loginPassword" /><br/>     
				
				<label for="">Gmail Password: </label>
				<input ng-required="true" ng-model="configCtl.configuration.accountProperty.gmailPassword" /><br/>     
				
				<label for="">Notification: </label>
				<select name="notification" ng-model="configCtl.configuration.accountProperty.notification" ng-options="o.v as o.n for o in [{ n: 'Notify in Slack', v: true }, { n: 'No notify', v: false }]" required></select><br/><br/>      
				
				<label for="">Remote: </label>
				<select name="remote" ng-model="configCtl.configuration.accountProperty.remote" ng-options="o.v as o.n for o in [{ n: 'Crossbrowser', v: true }, { n: 'Local', v: false }]" required></select>   <br/>   
			</div>
			<div class="col-xs-12 col-md-12">
				<h3 class="text-center">Person Template</h3>
			</div>
			<div class="col-xs-12 col-md-3">
				<h4>Personal Info</h4>
				<label for="">Name: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.name" /><br/>     
				
				<label for="">Middle Name: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.middleName" /><br/>     
				
				<label for="">Last Name: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.lastName" /><br/>     
				
				<label for="">Maiden Name: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.maidenName" /><br/>     
				
				<label for="">Birth Date: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.birthDate" /><br/>     
				
				<label for="">Gender: </label>
				<select name="gender" ng-model="configCtl.configuration.person.gender" required>
			    	<option value="UNKNOWN">Unknown</option>
			    	<option value="MALE">Male</option>
			    	<option value="FEMALE">Female</option>
		 		</select><br/>
				
				<label for="">Email: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.email" /><br/>     
				
				<label for="">Title of Employee: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.titleOfEmployee" /><br/>     
				
				<label for="">Department of Employee: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.departamentOfEmployee" /><br/>     
				
				<label for="">Primary Phone: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.primaryPhone" pattern=".{10}" title="Phone (Person) need 10 characters"/><br/>     
				
				<label for="">SSN: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.ssn" pattern=".{9}" title="SSN need 9 characters"/><br/>     
				
				<label for="">Marital Status: </label>
				<select name="marital-status" ng-model="configCtl.configuration.person.maritalStatus" required>
			    	<option value="UNKNOWN">Unknown</option>
			    	<option value="SINGLE">Single</option>
			    	<option value="MARRIED">Married</option>
			    	<option value="DIVORCED">Divorced</option>
			    	<option value="WIDOWER">Widower</option>
			    	<option value="PARTNER">Partner</option>
		 		</select><br/>
			</div>
			<div class="col-xs-12 col-md-3">
				<h4>Emergency Contact</h4>
				<label for="">Name: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.emergencyContact.name" /><br/>     
				
				<label for="">Email: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.emergencyContact.email" /><br/>     
				
				<label for="">Phone Number: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.emergencyContact.phone" pattern=".{10}" title="Phone (Emergency Contact) need 10 characters"/><br/>     
				
				<label for="">Relationship: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.emergencyContact.relationship" /><br/>     
			</div>
			<div class="col-xs-12 col-md-3">
				<h4>US Address</h4>
				<label for="">Street: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.usaddress.street" /><br/>     
				
				<label for="">APT: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.usaddress.apt" /><br/>     
				
				<label for="">Second Street: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.usaddress.secondStreet" /><br/>     
				
				<label for="">City: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.usaddress.city" /><br/>     
				
				<label for="">State: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.usaddress.state" /><br/>     
				
				<label for="">Zip code: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.usaddress.zipCode" pattern=".{5}" title="Zip Code need 5 characters"/><br/>     
				
				<label for="">County: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.usaddress.county" /><br/>     
				
				<label for="">Short Description: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.usaddress.shortDescription" /><br/>     
				
				<label for="">Same As Home: </label>
				<select name="remote" ng-model="configCtl.configuration.person.usaddress.sameAsHome" ng-options="o.v as o.n for o in [{ n: 'Yes', v: true }, { n: 'No', v: false }]" required></select><br/>  
			</div>
			<div class="col-md-3">
				<h4>SMB BENEFIT</h4>
				<label for="">Medical Benefit Name: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.medicalBenefit" /><br/>     
				<label for="">Dental Benefit Name: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.dentalBenefit" /><br/>     
				<label for="">Vision Benefit Name: </label>
				<input ng-required="true" ng-model="configCtl.configuration.person.visionBenefit" /><br/>     
			</div>
			<div class="col-md-12 center-block"><button class="btn btn-lg btn-primary" ng-disabled="form.$invalid" type="submit">Save</button></div>
	    </form>
    </div>
    </body>
</html>