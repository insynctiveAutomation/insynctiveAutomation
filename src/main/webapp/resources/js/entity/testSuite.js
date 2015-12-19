function Test(testSuite) {
	this.testID;
	this.className;
	this.testName;
	this.testSuiteID = testSuite.testSuiteID;
	this.paramObject = {};
}

Test.asTest = function (jsonTest) {
	return angular.extend(new Test(), jsonTestSuite);
};