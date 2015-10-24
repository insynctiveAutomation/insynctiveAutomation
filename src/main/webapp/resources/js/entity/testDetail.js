function TestDetail() {
	this.className = '';
	this.testName = '';
	this.includeMethods = [];
}

TestDetail.asTestDetail = function (jsonTestDetails) {
	return angular.extend(new TestDetail(), jsonTestDetails);
};