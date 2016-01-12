package insynctive.model.test.run;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import insynctive.model.test.Test;
import insynctive.model.test.TestSuite;
import insynctive.model.test.TestSuiteRunner;

@Entity
@Table(name = "test_suite_run")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestSuiteRun {

	@Id
	@GeneratedValue
	@Column(name = "test_suite_run_id")
	private Integer testSuiteRunID;

	@Column(name="name")
	private String name;
	
	@Column(name = "browser")
	private String browser;
	
	@Column(name = "environment")
	private String environment;
	
	@Column(name = "remote")
	private Boolean remote;

	@Column(name = "tester")
	private String tester;
	
	@Column(name = "date")
	private Date date;

	@Column(name = "status")
	private String status;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade={CascadeType.ALL})
	@JoinTable(
			name = "test_suite_run_x_test_run",
			joinColumns = @JoinColumn(name = "test_suite_run_id"),
			inverseJoinColumns = @JoinColumn(name = "test_run_id")
	)
	private List<TestRun> testsRuns = new ArrayList<>();

	@ManyToOne()
	@JoinColumn(name = "depends_test_suite_run")
	private TestSuiteRun dependsTestSuiteRun;
	
	public TestSuiteRun() { }
	
	public TestSuiteRun(String name, String browser, String environment, Boolean remote, String tester) {
		this.name = name;
		this.browser = browser;
		this.environment = environment;
		this.remote = remote;
		this.tester = tester;
		this.setStatus("New");
	}

	public TestSuiteRun(TestSuiteRunner tsRunner, Boolean isRemote, String tester) throws Exception {
		this.name = tsRunner.getTestSuite().getTestSuiteName();
		this.browser = tsRunner.getBrowser();
		this.environment = tsRunner.getEnvironment();
		this.remote = isRemote;
		this.tester = tester;
		this.setStatus("New");
		addTestsRuns(tsRunner.getTestSuite().getTests());
	}

	public TestSuiteRun(TestSuite ts, String browser, String environment, Boolean isRemote, String tester) throws Exception {
		this.name = ts.getTestSuiteName();
		this.browser = browser;
		this.environment = environment;
		this.remote = isRemote;
		this.tester = tester;
		this.setStatus("New");
		addTestsRuns(ts.getTests());
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public Boolean isRemote() {
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
	
	public Integer getTestSuiteRunID() {
		return testSuiteRunID;
	}

	public void setTestSuiteRunID(Integer testSuiteRunID) {
		this.testSuiteRunID = testSuiteRunID;
	}

	public List<TestRun> getTestsRuns() {
		return testsRuns;
	}

	public void setTestsRuns(List<TestRun> testsRuns) {
		this.testsRuns = testsRuns;
	}
	
	public void removeTestRun(TestRun testRun){
		this.testsRuns.remove(testRun);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getRemote() {
		return remote;
	}

	public TestSuiteRun getDependsTestSuiteRun() {
		return dependsTestSuiteRun;
	}

	@JsonIgnoreProperties
	public boolean isDependingOnAnotherTS(){
		return dependsTestSuiteRun != null;
	}
	
	public Integer getDependsRunID() {
		if(dependsTestSuiteRun != null){
			return dependsTestSuiteRun.getTestSuiteRunID();
		}
		return null;
	}

	public void setDependsTestSuiteRun(TestSuiteRun dependsTestSuite) {
		this.dependsTestSuiteRun = dependsTestSuite;
	}

	public void addTestRun(TestRun testRun){
		this.testsRuns.add(testRun);
	}
	
	public void addTestsRuns(List<Test> tests) throws IllegalArgumentException, IllegalAccessException, Exception {
		for(Test test : tests){
			TestRun testRun = new TestRun(test);
			testRun.setStatus("Running");
			this.addTestRun(testRun);
		}
	}
}
 