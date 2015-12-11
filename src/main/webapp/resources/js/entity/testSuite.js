function Test(testSuite) {
	this.testSuiteID = testSuite.testSuiteID;
	this.testSuiteName;
	this.tests;
}

Test.asTest = function (jsonTest) {
	return angular.extend(new Test(), jsonTestSuite);
};