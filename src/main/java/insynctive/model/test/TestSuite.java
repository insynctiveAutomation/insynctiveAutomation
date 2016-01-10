package insynctive.model.test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import insynctive.model.ParamObject;
import insynctive.model.test.run.TestSuiteRun;

/**
 * @author Eugenio
 *
 */
@Entity
@Table(name = "test_suite")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestSuite {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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
	
	@ManyToOne()
	@JoinColumn(name = "depends_test_suite")
	private TestSuite dependsTestSuite;
	
	@Transient
	public TestSuiteRun run;
	
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

	public TestSuite getDependsTestSuite() {
		return dependsTestSuite;
	}

	public void setDependsTestSuite(TestSuite dependsTestSuite) {
		this.dependsTestSuite = dependsTestSuite;
	}

	@JsonIgnore
	public TestSuiteRun toTestSuiteRun() throws IllegalArgumentException, IllegalAccessException, Exception {
		TestSuiteRun tsRun = new TestSuiteRun();
		tsRun.setName(testSuiteName);
		tsRun.addTestsRuns(tests);
		if(dependsTestSuite != null){
			tsRun.setDependsTestSuiteRun(dependsTestSuite.run);
		}
		run = tsRun;
		return tsRun;
	}

	@JsonIgnore
	public TestSuiteRun run() throws IllegalArgumentException, IllegalAccessException, Exception {
		TestSuiteRun tsRun = toTestSuiteRun();
		tsRun.setStatus("Running");
		return tsRun;
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
			if(paramObject.getUsAddress() != null) paramObject.getUsAddress().setUsAddressID(null);
		}
		return this;
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
}
