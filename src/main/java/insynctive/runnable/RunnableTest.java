package insynctive.runnable;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import insynctive.dao.test.TestDao;
import insynctive.dao.test.TestSuiteRunDao;
import insynctive.model.test.Test;
import insynctive.model.test.run.TestSuiteRun;
import insynctive.utils.Sleeper;
import insynctive.utils.TestResults;
 
public class RunnableTest implements Runnable {

	private TestNG testNG;
	private TestSuiteRun testSuite;
	private TestListenerAdapter testListenerAdapter;  
	private TestSuiteRunDao testSuiteDao;
	private TestDao testDao;
	private Thread[] threadsToJoin;

	public RunnableTest(TestNG  testNG, TestSuiteRun testSuite, TestListenerAdapter testListenerAdapter, TestSuiteRunDao testSuiteDao, TestDao testDao, Thread[] threadsToJoin){
		  this.testNG = testNG;
		  this.testSuite = testSuite;
		  this.testListenerAdapter = testListenerAdapter;
		  this.testSuiteDao = testSuiteDao;
		  this.testDao = testDao;
		  this.threadsToJoin = threadsToJoin;
	}
	public RunnableTest(TestNG  testNG){
		this.testNG = testNG;
	}
	
	@Override
	public void run() {
		if(threadsToJoin != null){
			try {
				for(Thread threadToJoin : threadsToJoin){
					threadToJoin.join();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		testNG.setPreserveOrder(true);
		testNG.run();
		setResult();
		Sleeper.threadSleep(3500);
		TestResults.removeScopeVars(testSuite.getTestSuiteRunID());
	}

	@Deprecated
	private void setResult() {
//		for(Test test : testSuite.getTests()){
//			test.setStatus(getTestStatus(test));
//		}
//		testSuite.setStatus(getTestSuiteStatus());
//		testSuiteDao.saveOrUpdate(testSuite);
	}

	private String getTestSuiteStatus() {
		if(testListenerAdapter.getFailedTests().size() == 0 && testListenerAdapter.getSkippedTests().size() == 0){
			return "SUCCESS";
		} else {
			return "FAILED";
		}
	}

	private String getTestStatus(Test test) {
		if(testListenerAdapter.getPassedTests().stream().anyMatch(res -> res.getName().equals(test.getTestName()))){
			return "SUCCESS";
		}
		if(testListenerAdapter.getFailedTests().stream().anyMatch(res -> res.getName().equals(test.getTestName()))){
			return "FAILED";
		}
		if(testListenerAdapter.getSkippedTests().stream().anyMatch(res -> res.getName().equals(test.getTestName()))){
			return "SKIPPED";
		}
		return "-";
	}

	public TestNG getTestNG() {
		return testNG;
	}

	public void setTestNG(TestNG testNG) {
		this.testNG = testNG;
	}
}