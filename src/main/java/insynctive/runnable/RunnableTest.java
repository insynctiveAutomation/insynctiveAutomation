package insynctive.runnable;

import org.testng.TestNG;

public class RunnableTest implements Runnable {

	private TestNG testNG;
	  
	public RunnableTest(TestNG  testNG){
		  this.testNG = testNG;
	}
	
	@Override
	public void run() {
		testNG.run();
	}

	public TestNG getTestNG() {
		return testNG;
	}

	public void setTestNG(TestNG testNG) {
		this.testNG = testNG;
	}
	}