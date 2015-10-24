package insynctive.results;

import java.util.ArrayList;
import java.util.List;

public class TestResultsTestNG {

	private List<IncludeMethod> passedTests = new ArrayList<IncludeMethod>();
	private List<IncludeMethod> failedTests = new ArrayList<IncludeMethod>();
	private List<IncludeMethod> skipedTests = new ArrayList<IncludeMethod>();
	
	public List<IncludeMethod> getSkipedTests() {
		return skipedTests;
	}
	public void setSkipedTests(List<IncludeMethod> skipedTests) {
		this.skipedTests = skipedTests;
	}
	public List<IncludeMethod> getFailedTests() {
		return failedTests;
	}
	public void setFailedTests(List<IncludeMethod> failedTests) {
		this.failedTests = failedTests;
	}
	public List<IncludeMethod> getPassedTests() {
		return passedTests;
	}
	public void setPassedTests(List<IncludeMethod> passedTests) {
		this.passedTests = passedTests;
	}
	
	
}
