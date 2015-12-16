package insynctive.tests;

import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import insynctive.annotation.ParametersFront;
import insynctive.pages.insynctive.AutoSignTaskPage;
import insynctive.pages.insynctive.LoginPage;
import insynctive.pages.insynctive.employee.EmployeeTaskPage;
import insynctive.utils.Debugger;
import insynctive.utils.ParamObjectField;
import insynctive.utils.Sleeper;
import insynctive.utils.data.TestEnvironment;

public class AutoSingFormTest extends TestMachine {
	
	@BeforeClass
	@Parameters({"accountID", "runID", "bowser", "testID", "testName"})
	public void tearUp(String accountID, String runID, String bowser, String testSuiteID, String testName) throws Exception {
		super.testSuiteID = Integer.parseInt(testSuiteID);
		super.tearUp(Integer.valueOf(accountID), Integer.valueOf(runID));
		testEnvironment = TestEnvironment.valueOf(bowser);
		this.sessionName = testName;
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(attrs={ParamObjectField.LOGIN_USERNAME, ParamObjectField.LOGIN_PASSWORD}, 
	labels={"Login Username", "Login Password"})
	public void loginTest(@Optional("TestID") Integer testID)
			throws Exception {
		changeParamObject(testID);
		
		startTest(testEnvironment);
		try{ 
			LoginPage loginPage = login();
			Sleeper.sleep(1500, driver);
			boolean result = loginPage.isLoggedIn();
			Debugger.log("loginTest => "+result, isSaucelabs);
			setResult(result, "Login Test");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Login Test", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="loginTest")
	@Parameters({"TestID"})
	public void directDepositFormTest (@Optional("TestID") Integer testID) throws Exception 
	{
		WebElement DirectDepostiTask = null;
		try{
			//open the tasks page
			EmployeeTaskPage taskPage  = new EmployeeTaskPage(driver, properties.getEnvironment());
			taskPage.loadPage();
			Sleeper.sleep(1500, driver);
			//look for the specific task 
			DirectDepostiTask = taskPage.findTask("Direct Deposit");
			
			//open the task
			boolean result = false;
			if(DirectDepostiTask != null)
			{
				DirectDepostiTask.click();
				AutoSignTaskPage signPage = new AutoSignTaskPage(driver);
				if(signPage.SignDocument())
					result = true;
			}
			
			Debugger.log("openDirectDepositFormTest => "+result, isSaucelabs);
			setResult(result, "Open Direct Deposit Form Test");
			assertTrue(result);
			
		    } catch(Exception ex){
			failTest("Open Direct Deposit Form Test", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	

}