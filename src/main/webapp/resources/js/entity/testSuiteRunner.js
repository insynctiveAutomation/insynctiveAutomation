function TestSuiteRunner() {
	this.testSuiteRunnerID;
	this.testSuite = new TestSuite();
	this.browser = '';
	this.environment = '';
}

TestSuiteRunner.asTestSuiteRunner = function (jsonTestSuiteRunner) {
	return angular.extend(new TestSuiteRunner(), jsonTestSuiteRunner);
};

TestSuiteRunner.makeNew(tsRunner){
	TestSuiteRunner.removeID(tsRunner);
	TestSuiteRunner.removeBrowser(tsRunner);
	TestSuiteRunner.removeEnvironment(tsRunner);
	TestSuite.makeNew(tsRunner.testSuite);
	
	return tsRunner
}

TestSuiteRunner.removeID = function(tsRunner){
	if(tsRunner){ tsRunner.testSuiteRunnerID = undefined; }
}

TestSuiteRunner.removeBrowser = function(tsRunner){
	if(tsRunner){ tsRunner.browser = undefined; }
}

TestSuiteRunner.removeEnvironment = function(tsRunner){
	if(tsRunner){ tsRunner.environment = undefined; }
}
