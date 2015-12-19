function TestSuite() {
	this.testSuiteID;
	this.testName;
	this.tests = [];
}

TestSuite.asTestSuite = function (jsonTestSuite) {
	return angular.extend(new TestSuite(), jsonTestSuite);
};