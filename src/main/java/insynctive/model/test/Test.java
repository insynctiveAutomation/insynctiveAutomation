package insynctive.model.test;

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

import org.hibernate.validator.internal.util.IgnoreJava6Requirement;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlTest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import insynctive.model.ParamObject;
import insynctive.model.test.run.TestRun;

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
	
	public Test(String testName, String status) {
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
	
	@Deprecated
	@JsonIgnore
	public static Test getNewWithOutIDs(Test test) throws IllegalArgumentException, IllegalAccessException, Exception{
		Test newtest = new Test();
		
		newtest.paramObject = ParamObject.getNewWithOutIDs(test.getParamObject());
		newtest.testName = test.getTestName();
		
		newtest.testID = null;
		return newtest;
	}

	public TestRun toTestRun() {
		TestRun testRun = new TestRun();
		testRun.setTestName(testName);
		testRun.setClassName(className);
		testRun.setParamObject(paramObject); //Check if this need to be a new ParamObject
		testRun.setStatus("Running");
		return testRun;
	}
}
