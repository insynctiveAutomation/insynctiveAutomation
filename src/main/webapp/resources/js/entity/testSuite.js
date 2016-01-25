function TestSuite() {
	this.testSuiteID;
	this.testSuiteName = '';
	this.tests = [];
}

TestSuite.asTestSuite = function (jsonTestSuite) {
	return angular.extend(new TestSuite(), jsonTestSuite);
};

TestSuite.makeNew = function(ts){
	TestSuite.removeID(ts);
	TestSuite.removeDependsTestSuite(ts);
	TestSuite.removeDependsTestSuiteIndex(ts);
	
	for(var testIndex in ts.tests){
		Test.makeNew(ts.tests[testIndex]);
	}
	
	return ts;
}

TestSuite.removeID = function(ts){
	if(ts){ ts.testSuiteID = undefined }
}

TestSuite.removeDependsTestSuite = function(ts){
	if(ts){ ts.dependsTestSuite = undefined }
}

TestSuite.removeDependsTestSuiteIndex = function(ts){
	if(ts){ ts.dependsTestSuiteIndex = undefined }
}

TestSuite.isEqual = function(ts1, ts2){
	var aux1 = ts1
	var aux2 = ts2
	
	delete aux1.dependsTestSuiteIndex
	delete aux2.dependsTestSuiteIndex
	
	return _.isEqual(aux1, aux2)
}