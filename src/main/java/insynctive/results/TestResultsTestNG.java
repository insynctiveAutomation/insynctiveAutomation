package insynctive.results;

import java.util.ArrayList;
import java.util.List;

import insynctive.model.test.Test;

public class TestResultsTestNG {

	private List<TestResult> passedTests = new ArrayList<TestResult>();
	private List<TestResult> failedTests = new ArrayList<TestResult>();
	private List<TestResult> skipedTests = new ArrayList<TestResult>();
	
	public List<TestResult> getSkipedTests() {
		return skipedTests;
	}
	public void setSkipedTests(List<TestResult> skipedTests) {
		this.skipedTests = skipedTests;
	}
	public List<TestResult> getFailedTests() {
		return failedTests;
	}
	public void setFailedTests(List<TestResult> failedTests) {
		this.failedTests = failedTests;
	}
	public List<TestResult> getPassedTests() {
		return passedTests;
	}
	public void setPassedTests(List<TestResult> passedTests) {
		this.passedTests = passedTests;
	}
	
	
}
