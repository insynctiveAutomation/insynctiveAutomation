package insynctive.model.test;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import insynctive.model.test.run.TestSuiteRun;
import insynctive.utils.data.TestEnvironment;

@Entity
@Table(name = "test_suite_runner")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestSuiteRunner {

	@Id
	@GeneratedValue
	@Column(name = "test_suite_runner_id")
	public Integer testSuiteRunnerID;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "test_suite_id", nullable=true, insertable=true, updatable=true)
	private TestSuite testSuite;

	@Column(name = "browser")
	private String browser;

	@Column(name = "environment")
	private String environment;

	@Column(name = "test_plan_id")
	private Integer testPlanID;
	
	public TestSuiteRunner() {
		// TODO Auto-generated constructor stub
	}
	
	public TestSuiteRunner(TestSuite newTestSuite, String environment, TestEnvironment browser) {
		this.testSuite = newTestSuite;
		this.environment = environment;
		this.browser = browser.name();
	}

	public Integer getTestSuiteRunnerID() {
		return testSuiteRunnerID;
	}

	public void setTestSuiteRunnerID(Integer testSuiteRunnerID) {
		this.testSuiteRunnerID = testSuiteRunnerID;
	}

	public TestSuite getTestSuite() {
		return testSuite;
	}

	public void setTestSuite(TestSuite testSuite) {
		this.testSuite = testSuite;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public Integer getTestPlanID() {
		return testPlanID;
	}

	public void setTestPlanID(Integer testPlanID) {
		this.testPlanID = testPlanID;
	}

	public TestSuiteRun toTestSuiteRun() {
		TestSuiteRun tsRun = testSuite.toTestSuiteRun();
		tsRun.setEnvironment(environment);
		tsRun.setBrowser(browser);
		tsRun.setName(testSuite.getTestSuiteName());
//		tsRun.setDate(new Date());
//		tsRun.setTester("tester");
		return tsRun;
	}
}
