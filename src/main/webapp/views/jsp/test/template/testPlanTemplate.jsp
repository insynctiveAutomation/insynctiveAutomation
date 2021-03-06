<span>Test Plan Name: </span>
<input type="text" ng-model="controller.testPlan.name" />
<table class="table table-striped table-hover table-condensed">
	<thead>
		<tr>
			<th></th>
			<th></th>
			<th>Test Suite Name</th>
			<th>Environment</th>
			<th>Browser</th>
			<th>Edit</th>
			<th>Copy</th>
			<th>Run After</th>
		</tr>
	</thead>
	<tbody ui-sortable="controller.addTestSuiteDependentIndex()" ng-model="controller.testPlan.testSuiteRunners">
		<tr data-ng-repeat="testSuiteRunner in controller.testPlan.testSuiteRunners" class="animate-repeat">
			<td class="col-sm-0"><a class="red-cross" id="remove" ng-click="controller.removeTestSuite($index)">&#10006;</a></td>
			<td class="col-sm-0">{{$index}}</td>
			<td class="col-sm-3"><input class="col-sm-12" type="text" ng-model="testSuiteRunner.testSuite.testSuiteName"></input></td>
			<td class="col-sm-3"><select name="environment" ng-model="testSuiteRunner.environment" data-ng-options="environment as environment for environment in controller.environments" required><option value="">Select an Environment</option></select></td>
			<td class="col-sm-2"><select name="browser" ng-model="testSuiteRunner.browser" data-ng-options="browser.value as browser.text for browser in controller.browsers" required><option value="">Select a Browser</option></select></td>
			<td class="col-sm-2"><a id="edit" ng-click="controller.openEditTestSuite(testSuiteRunner.testSuite)">Edit Test Suite</a></td>
			<td class="col-sm-1"><a id="edit" ng-click="controller.copy($index)">Copy</a></td>
			<td class="col-sm-1">
			<select name="depndsTestSuite" ng-model="testSuiteRunner.testSuite.dependsTestSuiteIndex" data-ng-options="index as index for index in controller.getPossibleDependentIndex($index)" ng-change="controller.addDependTS(testSuiteRunner.testSuite)">
				<option value="">None</option>
			</select>
			</td>
		</tr>
	</tbody>
</table>
<a class="btn" ng-click="controller.addNewTestSuite()">Add new Test Suite</a>
<span>    </span>
<a class="btn" ng-click="controller.importTestSuite()"><span ng-if="controller.importLabel">{{controller.importLabel}}</span><span ng-if="!controller.importLabel">Import existing Test Suite</span></a>