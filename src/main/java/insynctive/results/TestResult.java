package insynctive.results;

public class TestResult {

	private String testName;
	private String detail;
	
	public TestResult(String testName) {
		this.testName = testName;
	}
	
	public TestResult(String testName, String detail) {
		this.testName = testName;
		this.detail = detail;
	}
	
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
	
}
