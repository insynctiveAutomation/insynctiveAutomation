package insynctive.jackson.utils;

import java.util.ArrayList;
import java.util.List;

import insynctive.model.test.run.TestSuiteRun;

public class TestSuiteList extends ArrayList<TestSuiteRun> {

	private static final long serialVersionUID = 1L;
	private List<TestSuiteRun> list;
	
	public TestSuiteList(List<TestSuiteRun> list){
		this.list = list;
	}
	

}
