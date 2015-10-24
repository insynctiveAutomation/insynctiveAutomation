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
	
	@OneToOne
	@Cascade({CascadeType.SAVE_UPDATE})
	@JoinColumn(name = "param_object_id")
	private ParamObject paramObject;
	
	
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
	
}
