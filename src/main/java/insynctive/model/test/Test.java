package insynctive.model.test;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.testng.xml.XmlInclude;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import insynctive.model.ParamObject;

@Entity
@Table(name = "test")
@JsonIgnoreProperties(ignoreUnknown = true) 
public class Test {
	
	@Id
	@GeneratedValue
	@Column(name = "test_id")
	private Integer testID;
	
	@Column(name = "test_name")
	private String testName;
	
	@Column(name = "class_name")
	private String className;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "param_object_id")
	private ParamObject paramObject;
	
	public Test(){
		this.paramObject = new ParamObject();
	}
	
	public Test(String testName) {
		this.testName = testName;
		this.paramObject = new ParamObject();
	}
	
	public Test(XmlInclude method, String className) {
		this.setTestName(method.getName());
		this.setClassName(className);
		this.setParamObject(new ParamObject());
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
	
	@Deprecated
	public void resetTest() {
		testID = null;
		paramObject.setParamObjectID(null);
	}
}
