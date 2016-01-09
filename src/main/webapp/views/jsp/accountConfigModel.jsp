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
		<label for="notification">Notification: </label> 
		<select name="notification" ng-model="accountCtrl.accountConfig.notification" ng-options="o.v as o.n for o in [{ n: 'Notify in Slack', v: true }, { n: 'Do not notify', v: false }]" required></select><br/> 
		<label for="runIn">Run in:  </label> 
		<select name="remote" ng-if="accountCtrl.isLocalhost" ng-model="accountCtrl.accountConfig.remote" ng-options="o.v as o.n for o in [{ n: 'Crossbrowser', v: true }, { n: 'Local', v: false }]" required></select>
		<button ng-disabled="" class="btn btn-lg btn-primary" type="submit">Save</button>
		<span ng-bind="accountCtrl.saved"></span>
	</form>
	<a href="/configuration">Advanced Configuration</a></br>
</div>

<div class="alert alert-danger animate-repeat" ng-repeat="error in accountCtrl.errors">{{error}}</div>