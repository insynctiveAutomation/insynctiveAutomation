package insynctive.results;

import java.util.ArrayList;
import java.util.List;

public class TestSuite {

	private String testName;
	private String className;
	private List<IncludeMethod> includeMethods;
	
	public TestSuite(String testName, String className, List<IncludeMethod> includeMethods){
		this.setTestName(testName);
		this.setClassName(className);
		this.setIncludeMethods(includeMethods);
	}
	
	public TestSuite(){
		this.setIncludeMethods(new ArrayList<IncludeMethod>());
	}
	
	public void addMethod(IncludeMethod method){
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

	public List<IncludeMethod> getIncludeMethods() {
		return includeMethods;
	}

	public void setIncludeMethods(List<IncludeMethod> includeMethods) {
		this.includeMethods = includeMethods;
	}
	
	
}
