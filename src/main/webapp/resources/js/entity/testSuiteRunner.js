function TestSuiteRunner() {
	this.testSuiteRunnerID;
	this.testSuite = new TestSuite();
	this.browser = '';
	this.environment = '';
}

TestSuiteRunner.asTestSuiteRunner = function (jsonTestSuiteRunner) {
	return angular.extend(new TestSuiteRunner(), jsonTestSuiteRunner);
};