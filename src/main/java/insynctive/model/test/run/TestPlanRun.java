package insynctive.model.test.run;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import insynctive.model.test.TestPlan;
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
	public List<TestSuiteRun> testSuiteRuns = new ArrayList();

	public TestPlanRun() {	}
	
	public TestPlanRun(String name) {
		this.name = name;
		this.status = "New";
	}

	public TestPlanRun(TestPlan tp, Boolean isRemote) throws Exception {
		this.name = tp.name;
		this.status = "New";
		addTestSuiteRuns(tp.getTestSuiteRunners(), isRemote);
	}

	public TestPlanRun(TestPlan tp, Boolean isRemote, String tester) throws Exception {
		this.name = tp.name;
		this.status = "New";
		addTestSuiteRuns(tp.getTestSuiteRunners(), isRemote, tester);
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

	public List<TestSuiteRun> getTestSuiteRuns() {
		return testSuiteRuns;
	}

	public void setTestSuiteRuns(List<TestSuiteRun> testSuiteRuns) {
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
	
	public void addTestSuiteRuns(List<TestSuiteRunner> testSuitesRunners, Boolean isRemote, String tester) throws IllegalArgumentException, IllegalAccessException, Exception{
		addTestSuiteRuns(testSuitesRunners, new ArrayList<TestSuiteRunner>(), isRemote, tester);
	}
	
	private void addTestSuiteRuns(List<TestSuiteRunner> notRunned, List<TestSuiteRunner> runned, Boolean isRemote, String tester) throws IllegalArgumentException, IllegalAccessException, Exception{
		List<TestSuiteRunner> canRun = notRunned.stream().filter(tsRunner -> canBeAdded(tsRunner, runned)).collect(Collectors.toList());
		List<TestSuiteRunner> canNotRun = notRunned.stream().filter(tsRunner -> !canBeAdded(tsRunner, runned)).collect(Collectors.toList());
		
		
		for(TestSuiteRunner tsRunner : canRun){
			TestSuiteRun tsRun = new TestSuiteRun(tsRunner, isRemote, tester);
			tsRun.setDependsTestSuiteRun(tsRunner.getTestSuite().isDependingOnAnotherTS() ? tsRunner.getTestSuite().getDependsTestSuite().run : null); 
			addTestSuiteRun(tsRun);
		
			tsRunner.getTestSuite().run = tsRun;
			runned.add(tsRunner);
		}
		if(canNotRun.size() > 0){ addTestSuiteRuns(canNotRun, runned, isRemote, tester); }
	}
	
	private Boolean canBeAdded(TestSuiteRunner tsRunner, List<TestSuiteRunner> added) {
		return !tsRunner.getTestSuite().isDependingOnAnotherTS() || added.stream().anyMatch(tsRunnerAdded -> tsRunnerAdded.getTestSuite().equals(tsRunner.getTestSuite().getDependsTestSuite()));
	}

	public void addTestSuiteRuns(List<TestSuiteRunner> testSuitesRunners, Boolean isRemote) throws IllegalArgumentException, IllegalAccessException, Exception{
		addTestSuiteRuns(testSuitesRunners, isRemote, "");
	}

}
