<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en" ng-app="configuration">
<head>
	<!-- MY CSS -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/configuration/configuration.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/home_page/test.css">

	<%@include file="/views/jsp/import/import-jquery.jsp" %>
	<%@include file="/views/jsp/import/import-angular.jsp" %>
	<%@include file="/views/jsp/import/import-bootstrap.jsp" %>
	<%@include file="/views/jsp/import/import-entities.jsp" %>
	<%@include file="/views/jsp/import/module/import-configuration_modal.jsp" %>
	
	<%@include file="/views/jsp/import/module/import-login.jsp" %>
	<%@include file="/views/jsp/import/module/import-advanced_configuration.jsp" %>	
</head>

	<body data-ng-controller="configurationController as configCtl">
    <div ng-if="configCtl.isLoading" class="center-div">
    	<img alt="loading" src="${pageContext.request.contextPath}/resources/gif/loader.gif" class="loading-img">
    	<span class="loading-span">Loading</span>
    </div>
    <div ng-show="!configCtl.isLoading" class="configuration-div">
		<div class="col-xs-4 col-md-4 h4"><a id="logoutLink" ng-click="configCtl.logout()">Logout</a></div>
		<div class="col-xs-4 col-md-4 text-center h4"><span class="" id="have-changes"></span></div>
	    <div class="col-xs-4 col-md-4 text-right h4"><a href="/" id="configurationLink">Home</a></div>
		<br/>
	    <form ng-submit="configCtl.saveConfiguration()" name="form" ng-model="configCtl.form" ng-change="configCtl.haveChanges()" id="form">
			<div class="col-xs-12 col-md-6">
				<h3>Account Login</h3>
				<!-- <label for="">RUN ID: </label>
				<input ng-change="configCtl.addFinalsLabels()" ng-required="true" ng-model="configCtl.configuration.runID" /><br/> -->     
				
				<label for="">Username: </label>
				<input ng-required="true" ng-model="configCtl.configuration.username" /><br/>     
				
				<label for="">Password: </label>
				<input ng-required="true" ng-model="configCtl.configuration.password" /><br/>     
			</div>
			<div class="col-xs-12 col-md-6">
				<h3>Insynctive Properties</h3>
				<label for="">Gmail Password: </label>
				<input ng-required="true" ng-model="configCtl.configuration.accountProperty.gmailPassword" /><br/>     
				
				<label for="">Notification: </label>
				<select name="notification" ng-model="configCtl.configuration.accountProperty.notification" ng-options="o.v as o.n for o in [{ n: 'Notify in Slack', v: true }, { n: 'Do not notify', v: false }]" required></select><br/><br/>      
				
				<label for="">Remote: </label>
				<select name="remote" ng-model="configCtl.configuration.accountProperty.remote" ng-options="o.v as o.n for o in [{ n: 'Remote', v: true }, { n: 'Local', v: false }]" required></select>   <br/>   
			</div>
			<div class="col-xs-12 col-md-12">
				<h3 class="text-center">Parameters Object Template</h3>
			</div>
			<div class="col-xs-12 col-md-6">
				<h4>Personal Info</h4>
				<label for="">Login Username: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.loginUsername" /><br/>     
				
				<label for="">Login Password: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.loginPassword" /><br/>    
				
				<label for="">Name: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.name" /><br/>     
				
				<label for="">Middle Name: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.middleName" /><br/>     
				
				<label for="">Last Name: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.lastName" /><br/>     
				
				<label for="">Maiden Name: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.maidenName" /><br/>     
				
				<label for="">Birth Date: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.birthDate" /><br/>     
				
				<label for="">Gender: </label>
				<gender ng-model="configCtl.configuration.paramObject.gender"></gender></br>
				
				<label for="">Email: </label>
				<input ng-change="configCtl.changeEmailLabel()" ng-required="true" ng-model="configCtl.configuration.paramObject.email" />    
				<span>Final Email:</span><span style="font-weight: bold;"> {{configCtl.finalEmail}}</span>
				<br/> 
				
				<label for="">Employee's title: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.titleOfEmployee" /><br/>     
				
				<label for="">Employee's department: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.departamentOfEmployee" /><br/>     
				
				<label for="">Primary Phone: </label>
				<input ng-change="configCtl.addFinalsLabels()" ng-required="true" ng-model="configCtl.configuration.paramObject.primaryPhone" pattern=".{10}" title="Phone (Person) need 10 characters"/>
				<span>Final Primary Phone:</span><span style="font-weight: bold;"> {{configCtl.finalPrimaryPhone}}</span>
				<br/>     
				
				<label for="">SSN: </label>
				<input ng-change="configCtl.changeSSNLabel()" ng-required="true" ng-model="configCtl.configuration.paramObject.ssn" pattern=".{9}" title="SSN need 9 characters"/>
				<span>Final SSN:</span><span style="font-weight: bold;"> {{configCtl.finalSSN}}</span>
				<br/>     
				
				<label for="">Marital Status: </label>
				<marital-status ng-model="configCtl.configuration.paramObject.maritalStatus"></marital-status><br/>   

				<label for="">Checklist Name</label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.checklistName" /><br/> 

				<label for="">Min loading Time (segs)</label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.loadingTime" /><br/> 

				<label for="">Time to wait for elements to be present (segs)</label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.waitTime" /><br/> 
			</div>
			<div class="col-xs-12 col-md-6">
				<h4>Emergency Contact</h4>
				<label for="">Name: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.emergencyContact.name" /><br/>     
				
				<label for="">Email: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.emergencyContact.email" />
				<br/>     
				
				<label for="">Phone Number: </label>
				<input ng-change="configCtl.addFinalsLabels()" ng-required="true" ng-model="configCtl.configuration.paramObject.emergencyContact.phone" pattern=".{10}" title="Phone (Emergency Contact) need 10 characters"/>
				<span>Final Phone Number:</span><span style="font-weight: bold;"> {{configCtl.finalPhoneEmergency}}</span>
				<br/>     
				
				<label for="">Relationship: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.emergencyContact.relationship" /><br/>    
			</div>
			<div class="col-xs-12 col-md-6">
				<h4>US Address</h4>
				<label for="">Street: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.usaddress.street" /><br/>     
				
				<label for="">APT: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.usaddress.apt" /><br/>     
				
				<label for="">Second Street: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.usaddress.secondStreet" /><br/>     
				
				<label for="">City: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.usaddress.city" /><br/>     
				
				<label for="">State: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.usaddress.state" /><br/>     
				
				<label for="">Zip code: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.usaddress.zipCode" pattern=".{5}" title="Zip Code need 5 characters"/><br/>     
				
				<label for="">County: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.usaddress.county" /><br/>     
				
				<label for="">Short Description: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.usaddress.shortDescription" /><br/>     
				
				<label for="">Same As Home: </label>
				<yes-no name="same as home" ng-model="configCtl.configuration.paramObject.usaddress.sameAsHome"></yes-no>
			</div>
			<div class="col-md-6 col-md-offset-6">
				<h4>SMB BENEFITS</h4>
				<label for="">Medical Benefit Name: </label>
				<input ng-model="configCtl.configuration.paramObject.medicalBenefit.name" />
				<benefit-company ng-model="configCtl.configuration.paramObject.medicalBenefit.company"></benefit-company>
				<br/>     
				<label for="">Dental Benefit Name: </label>
				<input ng-model="configCtl.configuration.paramObject.dentalBenefit.name" />
				<benefit-company ng-model="configCtl.configuration.paramObject.dentalBenefit.company"></benefit-company>
		 		<br/>     
				<label for="">Vision Benefit Name: </label>
				<input ng-model="configCtl.configuration.paramObject.visionBenefit.name" />
				<benefit-company ng-model="configCtl.configuration.paramObject.visionBenefit.company"></benefit-company>
		 		<br/>     
			</div>

			<div class="col-md-6 col-md-offset-6">
				<h4>Documents</h4>
				<label for="">Document Name: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.docName" /><br/>

				<label for="">Document Category: </label>
				<input ng-required="true" ng-model="configCtl.configuration.paramObject.docCategory" />	<br/>
			</div>

			<div class="col-md-offset-5 col-md-2" style="padding-top: 20px;"><button class="center-block btn btn-primary btn-block" ng-disabled="form.$invalid" type="submit">Save</button></div>
	    </form>
    </div>
    </body>
</html>