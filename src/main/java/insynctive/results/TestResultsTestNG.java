package insynctive.results;

import java.util.ArrayList;
import java.util.List;

import insynctive.model.test.Test;

public class TestResultsTestNG {

	private List<Test> passedTests = new ArrayList<Test>();
	private List<Test> failedTests = new ArrayList<Test>();
	private List<Test> skipedTests = new ArrayList<Test>();
	
	public List<Test> getSkipedTests() {
		return skipedTests;
	}
	public void setSkipedTests(List<Test> skipedTests) {
		this.skipedTests = skipedTests;
	}
	public List<Test> getFailedTests() {
		return failedTests;
	}
	public void setFailedTests(List<Test> failedTests) {
		this.failedTests = failedTests;
	}
	public List<Test> getPassedTests() {
		return passedTests;
	}
	public void setPassedTests(List<Test> passedTests) {
		this.passedTests = passedTests;
	}
	
	
}
