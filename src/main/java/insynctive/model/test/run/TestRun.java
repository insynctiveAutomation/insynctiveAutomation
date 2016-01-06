package insynctive.model.test.run;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import insynctive.model.ParamObject;
import insynctive.model.test.Test;
import insynctive.model.test.TestSuite;

@Entity
@Table(name = "test_run")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestRun {

	@Id
	@GeneratedValue
	@Column(name = "test_run_id")
	private Integer testRunID;
	
	@Column(name = "test_name")
	private String testName;
	
	@Column(name = "class_name")
	private String className;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "param_object_id", nullable=true, insertable=true, updatable=true)
	private ParamObject paramObject;
	
	@Column(name = "status")
	public String status;

	public TestRun() {
		// TODO Auto-generated constructor stub
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTestRunID() {
		return testRunID;
	}

	public void setTestRunID(Integer testRunID) {
		this.testRunID = testRunID;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public ParamObject getParamObject() {
		return paramObject;
	}

	public void setParamObject(ParamObject paramObject) {
		this.paramObject = paramObject;
	}
	
}
