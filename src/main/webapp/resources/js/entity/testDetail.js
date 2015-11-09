function TestDetail() {
	this.testName = '';
	this.className = '';
	this.includeMethods = [];
}

TestDetail.asTestDetail = function (jsonTestDetails) {
	return angular.extend(new TestDetail(), jsonTestDetails);
};