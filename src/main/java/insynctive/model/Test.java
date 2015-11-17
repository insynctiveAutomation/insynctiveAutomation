package insynctive.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Test")
@JsonIgnoreProperties(ignoreUnknown = true) 
public class Test {
	
	@Id
	@GeneratedValue
	@Column(name = "test_id")
	private Integer testID;
	
	@Column(name = "test_name")
	private String testName;
	
	@Column(name = "status")
	private String status;

	@Column(name = "testSuite_id")
	private Integer testSuiteID;
	
	@OneToOne
	@Cascade({CascadeType.SAVE_UPDATE})
	@JoinColumn(name = "param_object_id")
	private ParamObject paramObject;
	
	public Test(){
		this.status = "-";
	}
	
	public Test(String testName) {
		this.testName = testName;
		this.status = "-";
	}
	
	public Test(String testName, String status) {
		this.testName = testName;
		this.status = "-";	
	}
	
	
	public Integer getTestID() {
		return testID;
	}

	public void setTestID(Integer testID) {
		this.testID = testID;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public ParamObject getParamObject() {
		return paramObject;
	}

	public void setParamObject(ParamObject paraObject) {
		this.paramObject = paraObject;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTestSuiteID() {
		return testSuiteID;
	}

	public void setTestSuiteID(Integer testSuiteID) {
		this.testSuiteID = testSuiteID;
	}

	public void resetTest() {
		this.testID = null;
		this.status = "-";
		this.paramObject.setParamObjectID(null);
	}
}
