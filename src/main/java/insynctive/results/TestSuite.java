package insynctive.results;

import java.util.ArrayList;
import java.util.List;

public class TestSuite {

	private String testName;
	private String className;
	private List<Result> includeMethods;
	
	public TestSuite(String testName, String className, List<Result> includeMethods){
		this.setTestName(testName);
		this.setClassName(className);
		this.setIncludeMethods(includeMethods);
	}
	
	public TestSuite(){
		this.setIncludeMethods(new ArrayList<Result>());
	}
	
	public void addMethod(Result method){
		getIncludeMethods().add(method);
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Result> getIncludeMethods() {
		return includeMethods;
	}

	public void setIncludeMethods(List<Result> includeMethods) {
		this.includeMethods = includeMethods;
	}
	
	
}
