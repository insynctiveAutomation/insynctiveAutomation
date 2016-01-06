package insynctive.model.test.run;

import java.sql.Date;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import insynctive.model.test.Test;
import insynctive.model.test.TestSuite;

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
	private Set<TestRun> testsRuns = new HashSet();

	public TestSuiteRun() {
		this.setStatus(null);
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

	public Set<TestRun> getTestsRuns() {
		return testsRuns;
	}

	public void setTestsRuns(Set<TestRun> testsRuns) {
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

	public void addTestRun(TestRun testRun){
		this.testsRuns.add(testRun);
	}

	public void addTestsRuns(Set<Test> tests) {
		for(Test test : tests){
			TestRun testRun = test.toTestRun();
			testRun.setStatus("Running");
			this.addTestRun(testRun);
		}
	}
}
 