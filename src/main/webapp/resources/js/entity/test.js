function TestSuite() {
	this.testName;
	this.className;
	this.includeMethods;
}

TestSuite.asTestSuite = function (jsonTestSuite) {
	return angular.extend(new TestSuite(), jsonTestSuite);
};