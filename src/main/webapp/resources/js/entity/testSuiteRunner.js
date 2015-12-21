function TestSuiteRunner(testPlan) {
	this.testSuiteRunnerID;
	this.testSuite = new TestSuite();
	this.browser = '';
	this.environment = '';
	this.testPlanID = testPlan.testPlanID;
}