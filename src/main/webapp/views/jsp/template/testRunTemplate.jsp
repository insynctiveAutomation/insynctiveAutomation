<table class="table table-striped table-hover table-condensed">
	<tbody ng-if="testDetails">
		<tr>
			<th>Methods</th>
			<th>Test Parameters</th>
			<th>Status</th>
		</tr>
		<tr data-ng-repeat="method in testDetails.testsRuns" class="animate-repeat">
			<td class="col-sm-5"><span ng-bind="method.testName"></span></td>
			<td class="col-sm-5"><a id="editParameters" ng-click="controller.openEditParameters(method)">{{testParameterText}}</a></td>
			<td class="col-sm-2"><result ng-model="method.status" bind="method.status" value="{{method.status}}"/></td>
		</tr>
	</tbody>
</table>
	