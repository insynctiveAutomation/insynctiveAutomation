<div class="modal-body" ng-if="parametersCtrl.loading">
	<img alt="loading" src="${pageContext.request.contextPath}/resources/gif/loader.gif" class="loading-img">
	<span class="loading-span">Loading</span>
</div>

<div class="modal-body row" ng-if="!parametersCtrl.loading">
		
		<h4 class="text-center">Parameters for test: {{parametersCtrl.testName}}</h4>
		<div ng-class="{'col-md-3 col-xs-3' : parametersCtrl.parameters.length > 0, 'col-md-6 col-xs-6' : parametersCtrl.parameters.length === 0}" class="col-md-offset-3">
			<div ng-repeat="label in parametersCtrl.labels track by $index">
				<label ng-bind="label"></label><br>
			</div>
		</div>
		<div ng-if="parametersCtrl.parameters.length > 0" class="col-xs-3 col-md-3">
			<div ng-repeat="param in parametersCtrl.parameters track by $index">
				<!-- <input type="text" ng-model="parametersCtrl.selectedTest.paramObject[param]"/> -->
				<parameter param="{{param}}" model="parametersCtrl.selectedTest.paramObject[param]"></parameter>
			</div>
		</div>
</div>
