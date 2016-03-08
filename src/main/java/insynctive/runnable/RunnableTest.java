package insynctive.runnable;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import insynctive.dao.test.TestDao;
import insynctive.dao.test.TestSuiteRunDao;
import insynctive.model.test.run.TestRun;
import insynctive.model.test.run.TestSuiteRun;
import insynctive.utils.Sleeper;
import insynctive.utils.TestResults;
 
public class RunnableTest implements Runnable {

	private TestNG testNG;
	private TestSuiteRun testSuiteRun;
	private TestListenerAdapter testListenerAdapter;  
	private TestSuiteRunDao testSuiteDao;
	private TestDao testDao;
	private Thread[] threadsToJoin;

	public RunnableTest(TestNG  testNG, TestSuiteRun testSuite, TestListenerAdapter testListenerAdapter, TestSuiteRunDao testSuiteDao, TestDao testDao, Thread[] threadsToJoin){
		  this.testNG = testNG;
		  this.testSuiteRun = testSuite;
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
		Sleeper.sleep(3500);
		
		try {
			TestResults.removeScopeVars(testSuiteRun.getTestSuiteRunID());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	private void setResult() {
		for(TestRun testRun : testSuiteRun.getTestsRuns()){
			testRun.setStatus(getTestStatus(testRun));
		}
		testSuiteRun.setStatus(getTestSuiteStatus());
		testSuiteDao.saveOrUpdate(testSuiteRun);
	}

	private String getTestSuiteStatus() {
		if(testListenerAdapter.getFailedTests().size() == 0 && testListenerAdapter.getSkippedTests().size() == 0){
			return "SUCCESS";
		} else {
			return "FAILED";
		}
	}

	private String getTestStatus(TestRun testRun) {
		if(testListenerAdapter.getPassedTests().stream().anyMatch(res -> res.getName().equals(testRun.getTestName()))){
			return "SUCCESS";
		}
		if(testListenerAdapter.getFailedTests().stream().anyMatch(res -> res.getName().equals(testRun.getTestName()))){
			return "FAILED";
		}
		if(testListenerAdapter.getSkippedTests().stream().anyMatch(res -> res.getName().equals(testRun.getTestName()))){
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