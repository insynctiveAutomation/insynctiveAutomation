package insynctive.model.test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import insynctive.model.test.run.TestPlanRun;
import junit.textui.TestRunner;

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
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade={CascadeType.ALL})
	@JoinTable(
			name = "test_plan_x_test_suite_runner",
			joinColumns = @JoinColumn(name = "test_plan_id"),
			inverseJoinColumns = @JoinColumn(name = "test_suite_runner_id")
	)
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
		testSuiteRunners.add(testSuiteRunner);
	}

	public List<TestSuiteRunner> getTestSuiteRunners() {
		return testSuiteRunners;
	}

	public void setTestSuiteRunners(List<TestSuiteRunner> testSuiteRunners) {
		this.testSuiteRunners = testSuiteRunners;
	}
	
	public TestPlanRun toTestPlanRun() throws IllegalArgumentException, IllegalAccessException, Exception{
		TestPlanRun tpRun = new TestPlanRun();
		tpRun.setName(name);
		tpRun.addTestSuiteRuns(testSuiteRunners);
		return tpRun;
	}

	public TestPlanRun run() throws IllegalArgumentException, IllegalAccessException, Exception {
		TestPlanRun tpRun = toTestPlanRun();
		tpRun.setStatus("Running");
		return tpRun;
	}
	
	public void setNewEnvironmentInTests(String environment){
		for(TestSuiteRunner tsRunner : testSuiteRunners){
			tsRunner.setEnvironment(environment);
		}
	}
}
