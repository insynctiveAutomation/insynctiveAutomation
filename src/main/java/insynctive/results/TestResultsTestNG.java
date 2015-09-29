package insynctive.results;

import java.util.ArrayList;
import java.util.List;

public class TestResultsTestNG {

	private List<Result> passedTests = new ArrayList<Result>();
	private List<Result> failedTests = new ArrayList<Result>();
	private List<Result> skipedTests = new ArrayList<Result>();
	
	public List<Result> getSkipedTests() {
		return skipedTests;
	}
	public void setSkipedTests(List<Result> skipedTests) {
		this.skipedTests = skipedTests;
	}
	public List<Result> getFailedTests() {
		return failedTests;
	}
	public void setFailedTests(List<Result> failedTests) {
		this.failedTests = failedTests;
	}
	public List<Result> getPassedTests() {
		return passedTests;
	}
	public void setPassedTests(List<Result> passedTests) {
		this.passedTests = passedTests;
	}
	
	
}
