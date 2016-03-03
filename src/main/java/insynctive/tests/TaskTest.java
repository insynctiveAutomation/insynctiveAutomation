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
	@Parameters({"environment", "browser", "isRemote", "isNotification", "testSuiteID", "testName"})
	public void tearUp(String environment, String browser, String isRemote, String isNotification, String testSuiteID, String testName) throws Exception {
		tearUp(browser, environment, isRemote, isNotification, testSuiteID);
		this.sessionName = testName;
		this.sessionName = "Assign Task Test ("+ paramObject.getEmail()+")";
	}
	
	@Override
	@AfterClass(alwaysRun = true)
	public void teardown() throws ConfigurationException, MalformedURLException, IOException, JSONException {
		super.teardown();
	}
	
	@Test()
	public void loginTest()
			throws Exception {
		startTest();

		try{ 
			LoginPage loginPage = login();
			boolean result = loginPage.isLoggedIn();
			setResult(result, "Login Test");
			Debugger.log("loginTest => "+result, isRemote);
			assertTrue(result);
		} catch(Exception ex){
			failTest("Login",  ex, isRemote);
			assertTrue(false);
		}
	}
	
//	//OPEN PERSON FILE NOT CREATE
	@Test(dependsOnMethods="loginTest")
	public void createPersonTest() throws Exception {
		try{ 
			HomeForAgentsPage homePage = new HomeForAgentsPage(driver, environment);
			homePage.openPersonFile("insynctiveapps+task");

			boolean result = homePage.isPersonFileOpened();
			Sleeper.sleep(5000, driver);
			setResult(result, "Open Person File");
			Debugger.log("createPersonTest => "+result, isRemote);
			assertTrue(result);
		} catch(Exception ex){
			failTest("Open Person File", ex, isRemote);
			assertTrue(false);
		}
	} 
	
	@Test(dependsOnMethods="createPersonTest")
	public void assignTask() throws Exception{
		PersonFilePage personFilePage = new PersonFilePage(driver, environment);
		try{ 
			personFilePage.assignTask();
			
			boolean result = personFilePage.isTaskAssigned();
			Debugger.log("asssignTask => "+result, isRemote);
			setResult(result, "Assign Task");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Assign Task", ex, isRemote);
			assertTrue(false);
		}
	}

}
