<span>Test Suite Name: </span> <input type="text" ng-model="controller.testSuite.testSuiteName"/>
	<table class="table table-striped table-hover table-condensed">
		<tbody>
			<tr>
				<th>Test Name</th>
				<th>Test Class Name</th>
				<th>Edit</th>
				<th>Remove</th>
			</tr>
			<tr data-ng-repeat="test in controller.testSuite.tests" class="animate-repeat">
				<td class="col-sm-2"><span ng-bind="test.testName"/></td>
				<td class="col-sm-2"><span ng-bind="test.className"/></td>
				<td class="col-sm-2"><a id="edit" ng-click="controller.openEditTest(test)">Edit Test</a></td>
				<td class="col-sm-3"><a id="edit" ng-click="controller.removeTest(test)">Remove Test</a></td>
			</tr>
		</tbody>
	</table>
	<a class="btn" ng-click="controller.addTest()">Add Test</a>
