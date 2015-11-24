package insynctive.tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import insynctive.exception.ConfigurationException;
import insynctive.pages.insynctive.LoginPage;
import insynctive.pages.insynctive.PersonFilePage;
import insynctive.pages.insynctive.agent.hr.HomeForAgentsPage;
import insynctive.utils.Debugger;
import insynctive.utils.Sleeper;
import insynctive.utils.data.TestEnvironment;

public class TaskTest extends TestMachine {

	@BeforeClass
	@Parameters({"accountID", "runID", "bowser", "testID", "environment"})
	public void tearUp(String accountID, String runID, String bowser, String testSuiteID, String environment) throws Exception {
		super.testSuiteID = Integer.parseInt(testSuiteID);
		super.tearUp(Integer.valueOf(accountID), Integer.valueOf(runID));
		testEnvironment = TestEnvironment.valueOf(bowser);
		this.sessionName = "Assign Task Test ("+ paramObject.getEmail()+")";
		properties.setEnvironment(environment);
	}
	
	@Override
	@AfterClass(alwaysRun = true)
	public void teardown() throws ConfigurationException, MalformedURLException, IOException, JSONException {
		super.teardown();
	}
	
	@Test()
	public void loginTest()
			throws Exception {
		startTest(testEnvironment);

		long startTime = System.nanoTime();
		try{ 
			LoginPage loginPage = login();
			boolean result = loginPage.isLoggedIn();
			long endTime = System.nanoTime();
			setResult(result, "Login Test", endTime - startTime);
			Debugger.log("loginTest => "+result, isSaucelabs);
			assertTrue(result);
		} catch(Exception ex){
			long endTime = System.nanoTime();
			failTest("Login",  ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
//	//OPEN PERSON FILE NOT CREATE
	@Test(dependsOnMethods="loginTest")
	public void createPersonTest() throws Exception {
		long startTime = System.nanoTime();
		try{ 
			HomeForAgentsPage homePage = new HomeForAgentsPage(driver, properties.getEnvironment());
			homePage.openPersonFile("insynctiveapps+task");

			boolean result = homePage.isPersonFileOpened();
			Sleeper.sleep(5000, driver);
			long endTime = System.nanoTime();
			setResult(result, "Open Person File", endTime - startTime);
			Debugger.log("createPersonTest => "+result, isSaucelabs);
			assertTrue(result);
		} catch(Exception ex){
			long endTime = System.nanoTime();
			failTest("Open Person File", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	} 
	
	@Test(dependsOnMethods="createPersonTest")
	public void assignTask() throws Exception{
		long startTime = System.nanoTime();
		PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
		try{ 
			personFilePage.assignTask();
			
			boolean result = personFilePage.isTaskAssigned();
			Debugger.log("asssignTask => "+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Assign Task", endTime - startTime);
			assertTrue(result);
		}catch (Exception ex){ 
			long endTime = System.nanoTime();
			failTest("Assign Task", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}

}
