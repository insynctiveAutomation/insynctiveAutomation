package insynctive.model.test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.testng.xml.XmlSuite;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import insynctive.model.Account;
import insynctive.model.test.run.TestPlanRun;
import insynctive.model.test.run.TestSuiteRun;

@Entity
@Table(name = "test_plan")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestPlan {
	
	@Id
	@GeneratedValue
	@Column(name = "test_plan_id")
	public Integer testPlanID;
	
	@Column(name = "name")
	public String name;
	
	@OneToMany(cascade={CascadeType.ALL}, mappedBy = "testPlan")
	public List<TestSuiteRunner> testSuiteRunners = new ArrayList<>();

	public TestPlan() {
		// TODO Auto-generated constructor stub
	}

	public Integer getTestPlanID() {
		return testPlanID;
	}

	public void setTestPlanID(Integer testPlanID) {
		this.testPlanID = testPlanID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addTestSuiteRunner(TestSuiteRunner testSuiteRunner){
		testSuiteRunner.setTestPlan(this);
		testSuiteRunners.add(testSuiteRunner);
	}

	public List<TestSuiteRunner> getTestSuiteRunners() {
		return testSuiteRunners;
	}

	public void setTestSuiteRunners(List<TestSuiteRunner> testSuiteRunners) {
		this.testSuiteRunners = testSuiteRunners;
	}
	
	public TestPlanRun toTestPlanRun(){
		TestPlanRun tpRun = new TestPlanRun();
		tpRun.setName(name);
		tpRun.addTestSuiteRuns(testSuiteRunners);
		return tpRun;
	}

	public TestPlanRun run() {
		TestPlanRun tpRun = toTestPlanRun();
		tpRun.setStatus("Running");
		return tpRun;
	}
	
	
}
