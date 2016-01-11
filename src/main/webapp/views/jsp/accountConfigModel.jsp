<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/account_config/accountConfig.css">
<div id="panel" class="lgContent width-panel" ng-if="accountCtrl.isLoading">
	 <div class="center-div">
		<img alt="loading" src="${pageContext.request.contextPath}/resources/gif/loader.gif" class="loading-img">
		<span class="loading-span">Loading</span>
	</div>
</div>

<div ng-if="!accountCtrl.isLoading" class="lgContent width-panel">
	<form ng-submit="accountCtrl.saveConfig()" class="form-signin" name="sendTest">
		<label for="username">Login Username: </label>
		<input ng-required="true" ng-model="accountCtrl.paramObject.loginUsername" /><br/> 
		<label for="password">Login Password: </label>
		<input ng-required="true" ng-model="accountCtrl.paramObject.loginPassword" /><br/> 
		<button ng-disabled="" class="btn btn-lg btn-primary" type="submit">Save</button>
		<span ng-bind="accountCtrl.saved"></span>
	</form>
	<a href="/configuration">Advanced Configuration</a></br>
</div>

<div class="alert alert-danger animate-repeat" ng-repeat="error in accountCtrl.errors">{{error}}</div>