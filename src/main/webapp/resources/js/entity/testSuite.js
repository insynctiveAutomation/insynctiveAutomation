function TestSuite() {
	this.testSuiteID;
	this.testSuiteName = '';
	this.tests = [];
}

TestSuite.asTestSuite = function (jsonTestSuite) {
	return angular.extend(new TestSuite(), jsonTestSuite);
};