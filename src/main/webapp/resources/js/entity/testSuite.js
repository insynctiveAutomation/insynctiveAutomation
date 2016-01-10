function TestSuite() {
	this.testSuiteID;
	this.testSuiteName = '';
	this.tests = [];
}

TestSuite.makeNew = function(tsRunner){
	tsRunner.testSuiteRunnerID = undefined;
	tsRunner.testSuite.testSuiteID = undefined;
	tsRunner.testSuite.dependsTestSuite = undefined;
	tsRunner.testSuite.dependsTestSuiteIndex = undefined;
	
	for(var testIndex in tsRunner.testSuite.tests){
		
		tsRunner.testSuite.tests[testIndex].paramObject.paramObjectID = undefined;
		
		if(tsRunner.testSuite.tests[testIndex].paramObject.emergencyContact){
			tsRunner.testSuite.tests[testIndex].paramObject.emergencyContact.emergencyID = undefined;
		}
		
		if(tsRunner.testSuite.tests[testIndex].paramObject.usAddress){
			tsRunner.testSuite.tests[testIndex].paramObject.usAddress.usAddressID = undefined;
		}
		
		tsRunner.testSuite.tests[testIndex].testID = undefined
	}
	
	return tsRunner;
}

TestSuite.asTestSuite = function (jsonTestSuite) {
	return angular.extend(new TestSuite(), jsonTestSuite);
};