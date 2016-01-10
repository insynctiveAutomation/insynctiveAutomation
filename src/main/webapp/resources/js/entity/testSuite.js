function TestSuite() {
	this.testSuiteID;
	this.testSuiteName = '';
	this.tests = [];
}

TestSuite.makeNew = function(ts){
	ts.testSuiteRunnerID = undefined;
	ts.testSuite.testSuiteID = undefined;
	ts.testSuite.dependsTestSuite = undefined;
	ts.testSuite.dependsTestSuiteIndex = undefined;
	for(var testIndex in ts.testSuite.tests){
		ts.testSuite.tests[testIndex].testID = undefined
	}
	return ts;
}

TestSuite.asTestSuite = function (jsonTestSuite) {
	return angular.extend(new TestSuite(), jsonTestSuite);
};