package insynctive.model.test;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	@JoinColumn(name = "test_suite_id")
	private TestSuite testSuite;

	@Column(name = "browser")
	private String browser;

	@Column(name = "environment")
	private String environment;

	public TestSuiteRunner() {	}
	
	public TestSuiteRunner(TestSuite newTestSuite, String environment, String browser) {
		this.testSuite = newTestSuite;
		this.environment = environment;
		this.browser = browser;
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

	@JsonIgnore
	public TestSuiteRun run(boolean isRemote, String tester) throws IllegalArgumentException, IllegalAccessException, Exception {
		TestSuiteRun tsRun = new TestSuiteRun(this, isRemote, tester);
		tsRun.setStatus("Running");
		return tsRun;
	}

	@JsonIgnore
	public TestSuiteRun run(boolean isRemote) throws IllegalArgumentException, IllegalAccessException, Exception {
		return run(isRemote, "");
	}
}
