package insynctive.model.test;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import insynctive.model.test.run.TestPlanRun;

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
	public Set<TestSuiteRunner> testSuiteRunners = new HashSet();

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

	public Set<TestSuiteRunner> getTestSuiteRunners() {
		return testSuiteRunners;
	}

	public void setTestSuiteRunners(Set<TestSuiteRunner> testSuiteRunners) {
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
