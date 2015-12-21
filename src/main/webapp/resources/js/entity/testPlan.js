function TestPlan(testSuite) {
	this.testPlanID;
	this.name = '';
	this.testSuiteRunners = [];
}

TestPlan.asTestPlan = function (jsonTestPlan) {
	return angular.extend(new TestPlan(), jsonTestSuite);
};