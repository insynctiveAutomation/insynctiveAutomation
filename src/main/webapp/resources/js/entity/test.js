function Test(testSuite) {
	this.testID;
	this.className;
	this.testName;
	this.paramObject = {};
}

Test.asTest = function (jsonTest) {
	return angular.extend(new Test(), jsonTestSuite);
};