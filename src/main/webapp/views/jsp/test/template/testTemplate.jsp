<form ng-submit="controller.save()" name="form" id="form">
	<span>Class (Class where the test is located) </span> 
	<select name="className" ng-model="controller.test.className" data-ng-options="className as className for className in controller.classes" ng-change='controller.getTestFromClass()' required>
   		<option value=""></option>
 	</select>
	<br/>
	<span>Test Name</span> 
	<select name="testName" ng-model="controller.test.testName" data-ng-options="testName as testName for testName in controller.testsFromClass" required>
   		<option value=""></option>
 	</select>
 	<br/>
	<a id="editParameters" ng-click="controller.openEditParameters()">Edit Parameters of Test</a></br>
	<button class="center-block btn btn-primary btn-block" ng-disabled="form.$invalid" type="submit">Save</button>
</form>
<span ng-bind="controller.message"></span>
