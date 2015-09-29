<div class="lgContent">
	<form ng-submit="accountCtrl.saveConfig()" class="form-signin"
		name="sendTest">

		<label for="lastname">Login UserName: </label><input
			ng-required="true"
			ng-model="accountCtrl.accountConfig.loginUsername" /><br /> <label
			for="lastname">Login Password: </label><input ng-required="true"
			ng-model="accountCtrl.accountConfig.loginPassword" /><br /> <label
			for="lastname">Notification?</label> <select name="notification"
			ng-model="accountCtrl.accountConfig.notification"
			ng-options="o.v as o.n for o in [{ n: 'True', v: true }, { n: 'False', v: false }]"
			required>
		</select> <br /> <label for="lastname">Remote? </label> <select name="remote"
			ng-model="accountCtrl.accountConfig.remote" required>
			<option value="true">True</option>
			<option value="false">False</option>
		</select> <br />

		<button ng-disabled="" class="btn btn-lg btn-primary" type="submit">Save</button>
		<span ng-bind="accountCtrl.saved"></span>
	</form>
</div>

<div class="alert alert-danger animate-repeat" ng-repeat="error in accountCtrl.errors">{{error}}</div>