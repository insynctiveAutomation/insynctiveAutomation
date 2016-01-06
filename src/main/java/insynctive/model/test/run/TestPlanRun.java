package insynctive.model.test.run;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import insynctive.model.test.TestSuite;
import insynctive.model.test.TestSuiteRunner;

@Entity
@Table(name = "test_plan_run")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestPlanRun {
	
	@Id
	@GeneratedValue
	@Column(name = "test_plan_run_id")
	public Integer testPlanRunID;
	
	@Column(name = "name")
	public String name;

	@Column(name = "status")
	private String status;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade={CascadeType.ALL})
	@JoinTable(
			name = "test_plan_run_x_test_suite_run",
			joinColumns = @JoinColumn(name = "test_plan_run_id"),
			inverseJoinColumns = @JoinColumn(name = "test_suite_run_id")
	)
	public Set<TestSuiteRun> testSuiteRuns = new HashSet<>();

	public TestPlanRun() {
		// TODO Auto-generated constructor stub
	}
	
	public Integer getTestPlanRunID() {
		return testPlanRunID;
	}

	public void setTestPlanRunID(Integer testPlanRunID) {
		this.testPlanRunID = testPlanRunID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<TestSuiteRun> getTestSuiteRuns() {
		return testSuiteRuns;
	}

	public void setTestSuiteRuns(Set<TestSuiteRun> testSuiteRuns) {
		this.testSuiteRuns = testSuiteRuns;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void addTestSuiteRun(TestSuiteRun tsRun) {
		testSuiteRuns.add(tsRun);
	}
	
	public void addTestSuiteRuns(Set<TestSuiteRunner> testSuitesRunners){
		for(TestSuiteRunner tsRunner : testSuitesRunners){
			TestSuiteRun tsRun = tsRunner.toTestSuiteRun();
			this.addTestSuiteRun(tsRun);
		}
	}

}
