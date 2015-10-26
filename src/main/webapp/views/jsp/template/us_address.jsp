</br>
<div ng-repeat="(attribute, value) in usAddress">
	<span>US Address attribute</span>
	<input ng-required="true" type="text" ng-model="usAddress[attribute]"></input>
	</br>
</div>