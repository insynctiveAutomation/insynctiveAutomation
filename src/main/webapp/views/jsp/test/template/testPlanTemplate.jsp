<span>Test Plan Name: </span>
<input type="text" ng-model="controller.testPlan.name" />
<table class="table table-striped table-hover table-condensed">
	<tbody>
		<tr>
			<th>Test Suite Name</th>
			<th>Browser</th>
			<th>Environment</th>
			<th>Edit</th>
			<th>Remove</th>
		</tr>
		<tr data-ng-repeat="testSuiteRunner in controller.testPlan.testSuiteRunners" class="animate-repeat">
			<td class="col-sm-2"><input type="text" ng-model="testSuiteRunner.testSuite.testSuiteName"></input></td>
			<td class="col-sm-2"><select name="environment" ng-model="testSuiteRunner.environment" data-ng-options="environment as environment for environment in controller.environments" required>
				<option value="">Select an Environment</option>
			</select></td>
			<td class="col-sm-2"><select name="browser"
				ng-model="testSuiteRunner.browser" required>
					<option value="">Select a Browser</option>
					<option value="FIREFOX">Firefox</option>
					<option value="CHROME">Chrome</option>
					<option value="IE_10">IE 10</option>
					<option value="IE_11">IE 11</option>
					<option value="IPAD">iPad</option>
			</select></td>
			<td class="col-sm-2"><a id="edit" ng-click="controller.openEditTestSuite(testSuiteRunner.testSuite)">Edit Test Suite</a></td>
			<td class="col-sm-2"><a id="edit" ng-click="controller.removeTest(testSuiteRunner.testSuite)">Remove Test Suite</a></td>
		</tr>
	</tbody>
</table>
<a class="btn" ng-click="controller.addTestSuite()">add Test Suite</a>