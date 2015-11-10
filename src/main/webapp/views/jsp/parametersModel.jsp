<div class="modal-body" ng-if="parametersCtrl.loading">
	<img alt="loading" src="${pageContext.request.contextPath}/resources/gif/loader.gif" class="loading-img">
	<span class="loading-span">Loading</span>
</div>

<div class="modal-body row" ng-if="!parametersCtrl.loading">
		
		<h4 class="text-center">Parameters for test: {{parametersCtrl.testName}}</h4>
		
		
		<div ng-class="{'col-md-5 col-xs-5' : parametersCtrl.parameters.length > 0, 'col-md-6 col-xs-6' : parametersCtrl.parameters.length === 0}" class="col-md-offset-1">
			<div style="height: 25px; margin-bottom: 10px; text-align: right" ng-repeat="label in parametersCtrl.labels track by $index">
				<label ng-bind="label"></label><br>
			</div>
		</div>
		
		<div ng-if="parametersCtrl.parameters.length > 0" class="col-xs-5 col-md-5">
			<div style="height: 25px; margin-bottom: 10px;" ng-repeat="param in parametersCtrl.parameters track by $index">
				<parameter editable="parametersCtrl.editable" style="text-align: left" ng-if="param.split('.').length == 2" param="{{param}}" model="parametersCtrl.selectedTest.paramObject[param.split('.')[0]][param.split('.')[1]]"></parameter>
				<parameter editable="parametersCtrl.editable" style="text-align: left" ng-if="param.split('.').length == 1" param="{{param}}" model="parametersCtrl.selectedTest.paramObject[param.split('.')[0]]"></parameter>
			</div>
		</div>
</div>
