package insynctive.model.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import insynctive.model.ParamObject;
import insynctive.model.test.run.TestRun;
import insynctive.model.test.run.TestSuiteRun;

@Entity
@Table(name = "test_suite")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestSuite {

	@Id
	@GeneratedValue
	@Column(name = "test_suite_id")
	private Integer testSuiteID; 
	
	@Column(name = "test_suite_name")
	private String testSuiteName;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade={CascadeType.ALL})
	@JoinTable(
			name = "test_suite_x_test",
			joinColumns = @JoinColumn(name = "test_suite_id"),
			inverseJoinColumns = @JoinColumn(name = "test_id")
	)
	private List<Test> tests = new ArrayList<Test>();
	
	public TestSuite() {
		// TODO Auto-generated constructor stub
	}
	
	public TestSuite(XmlClass clazz, String suiteName) {
		testSuiteName = suiteName;
		for(XmlInclude method : clazz.getIncludedMethods()){
			Test test = new Test(method, clazz.getName());
			this.addTest(test);
		}
	}

	public void addMethod(Test newTest) {
		tests.add(newTest);
	}
	
	public Integer getTestSuiteID() {
		return testSuiteID;
	}

	public void setTestSuiteID(Integer testSuiteID) {
		this.testSuiteID = testSuiteID;
	}

	public String getTestSuiteName() {
		return testSuiteName;
	}

	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}

	public List<Test> getTests() {
		return tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}
	
	public void removeTest(Test test){
		this.tests.remove(test);
	}

	public void addTest(Test test){
		tests.add(test);
	}

	public TestSuiteRun toTestSuiteRun() {
		TestSuiteRun tsRun = new TestSuiteRun();
		tsRun.setName(testSuiteName);
		tsRun.addTestsRuns(tests);
		return tsRun;
	}
	
	@JsonIgnore
	@Deprecated
	public static TestSuite getNewWithOutIDs(TestSuiteRun testSuite) throws Exception {
		TestSuite newTestSuite = new TestSuite();
		
		for(Test test : newTestSuite.getTests()){
			Test newTest = Test.getNewWithOutIDs(test);
			newTestSuite.addMethod(newTest);
		}
		
		newTestSuite.setTestSuiteID(null);
		return newTestSuite;
	}
	
	@JsonIgnore
	public Test getTestByName(String name){
		for(Test test : tests){
			if(test.getTestName().equals(name)){ return test; }
		}
		return null;
	}
	
	@Deprecated
	public TestSuite resetTestSuite() {
		for(Test test : this.getTests()){
			//Reset the paramObject IDS
			ParamObject paramObject = test.getParamObject();
			if(paramObject.getEmergencyContact() != null) paramObject.getEmergencyContact().setEmergencyID(null);
			if(paramObject.getUSAddress() != null) paramObject.getUSAddress().setUsAddressID(null);
		}
		return this;
	}
}
