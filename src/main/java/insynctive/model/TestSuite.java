package insynctive.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Testsuite")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestSuite {

	@Id
	@GeneratedValue
	@Column(name = "test_suite_id")
	private Integer testSuiteID;
	
	@Column(name = "test_suite_name")
	private String testSuiteName;

	@OneToMany(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
	@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name="testSuite_id")
	private List<Test> tests;

	@Column(name = "status")
	private String status;
	
	@Column(name = "class_name")
	private String className;

	@Column(name = "browser")
	private String browser;
	
	@Column(name = "remote")
	private Boolean remote;

	@Column(name = "tester")
	private String tester;
	
	@Column(name = "environment")
	private String environment;
	
	public TestSuite() {
		this.setStatus(null);
		this.tests = new ArrayList<>();
	}


	public List<Test> getTests() {
		return tests;
	}


	public void setTests(List<Test> tests) {
		this.tests = tests;
	}


	public String getTestSuiteName() {
		return testSuiteName;
	}


	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}


	public Integer getTestSuiteID() {
		return testSuiteID;
	}


	public void setTestSuiteID(Integer testSuiteID) {
		this.testSuiteID = testSuiteID;
	}


	public void addMethod(Test newTest) {
		tests.add(newTest);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public Boolean getRemote() {
		return remote;
	}

	public void setRemote(Boolean remote) {
		this.remote = remote;
	}

	public String getTester() {
		return tester;
	}

	public void setTester(String tester) {
		this.tester = tester;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	@JsonIgnore
	public TestSuite getNewWithOutIDs() {
		TestSuite newTestSuite = new TestSuite();
		
		newTestSuite.setBrowser(browser);
		newTestSuite.setClassName(className);
		newTestSuite.setEnvironment(environment);
		newTestSuite.setRemote(remote);
		newTestSuite.setStatus("-");
		newTestSuite.setTester(tester);
		newTestSuite.setTestSuiteID(null);
		newTestSuite.setTestSuiteName(testSuiteName);
		
		for(Test test : tests){
			Test newTest = new Test();
			newTest.setStatus("-");
			newTest.setTestID(null);
			newTest.setTestName(test.getTestName());
			newTest.setTestSuiteID(null);
			if(test.getParamObject() != null) test.getParamObject().setParamObjectID(null);
			newTest.setParamObject(test.getParamObject());
			
			newTestSuite.addMethod(newTest);
		}
		
		return newTestSuite;
	}
	

	public TestSuite resetTestSuite() {
		for(Test test : this.getTests()){
			test.resetTest();
			test.setTestSuiteID(null);
			//Reset the paramObject IDS
			ParamObject paramObject = test.getParamObject();
			if(paramObject.getEmergencyContact() != null) paramObject.getEmergencyContact().setEmergencyID(null);
			if(paramObject.getUSAddress() != null) paramObject.getUSAddress().setUsAddressID(null);
		}
		return this;
	}
}
