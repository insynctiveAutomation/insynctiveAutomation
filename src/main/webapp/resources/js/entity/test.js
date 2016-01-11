function Test(testSuite) {
	this.testID;
	this.className;
	this.testName;
	this.paramObject = {};
}

Test.asTest = function (jsonTest) {
	return angular.extend(new Test(), jsonTestSuite);
};

Test.makeNew = function(test){
	Test.removeID(test);
	Test.removeParamObjectID(test);
	Test.removeEmergencyContactID(test);
	Test.removeUsAddressID(test);
	
	return test;
}

Test.removeID = function(test){
	if(test){ test.testID = undefined; }
}

Test.removeParamObjectID = function(test){
	if(test.paramObject){ test.paramObject.paramObjectID = undefined }
}

Test.removeEmergencyContactID = function(test){
	if(test.paramObject.emergencyContact){ test.paramObject.emergencyID = undefined }
}

Test.removeUsAddressID = function(test){
	if(test.paramObject.usAddress){ test.paramObject.usAddress.usAddressID = undefined }
}