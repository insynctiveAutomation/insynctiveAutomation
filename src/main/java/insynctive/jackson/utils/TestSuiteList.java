package insynctive.jackson.utils;

import java.util.ArrayList;
import java.util.List;

import insynctive.model.TestSuite;

public class TestSuiteList extends ArrayList<TestSuite> {

	private static final long serialVersionUID = 1L;
	private List<TestSuite> list;
	
	public TestSuiteList(List<TestSuite> list){
		this.list = list;
	}
	

}
