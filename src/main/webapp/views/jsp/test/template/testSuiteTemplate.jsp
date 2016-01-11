<span>Test Suite Name: </span> <input type="text" ng-model="controller.testSuite.testSuiteName"/>
	<table class="table table-striped table-hover table-condensed">
		<thead>
			<tr>
				<th></th>
				<th>Test Name</th>
				<th>Test Class Name</th>
				<th>Edit</th>
			</tr>
		</thead>
		<tbody ui-sortable ng-model="controller.testSuite.tests">
			<tr data-ng-repeat="test in controller.testSuite.tests" class="animate-repeat">
				<td class="col-sm-0"><a class="red-cross" id="remove" ng-click="controller.removeTest($index)">&#10006;</a></td>
				<td class="col-sm-4"><span ng-bind="test.testName"/></td>
				<td class="col-sm-4"><span ng-bind="test.className"/></td>
				<td class="col-sm-4"><a id="edit" ng-click="controller.openEditTest(test)">Edit Test</a></td>
			</tr>
		</tbody>
	</table>
	<a class="btn" ng-click="controller.addTest()">Add Test</a>
