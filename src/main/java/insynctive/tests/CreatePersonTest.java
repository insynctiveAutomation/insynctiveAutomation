package insynctive.tests;

import static org.junit.Assert.assertTrue;
import insynctive.pages.insynctive.HomeForAgentsPage;
import insynctive.pages.insynctive.LoginPage;
import insynctive.utils.Debugger;
import insynctive.utils.PersonData;
import insynctive.utils.data.TestEnvironment;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreatePersonTest extends TestMachine {

	PersonData person;

	@Override
	@BeforeClass
	public void tearUp() throws Exception {
		super.tearUp();
		this.sessionName = "Create Person Test";
	}
	
	@DataProvider(name = "hardCodedBrowsers", parallel = true)
	public static Object[][] sauceBrowserDataProvider(Method testMethod) {
		return new Object[][] { new Object[] { TestEnvironment.IPAD }
		};
	}
	
	@Test(dataProvider = "hardCodedBrowsers")
	public void loginTest(TestEnvironment testEnvironment, ITestContext ctx) throws Exception {
			
		this.testName = ctx.getCurrentXmlTest().getName().equals("Default test") ? "Default" : ctx.getCurrentXmlTest().getName();
		this.suiteName =  ctx.getCurrentXmlTest().getSuite().getName().equals("Default suite") ? "Default" : ctx.getCurrentXmlTest().getName();
		System.out.println("Test Name: "+testName +" Suite Name: "+suiteName);

		startTest(testEnvironment);

		try{
			LoginPage loginPage = login();
			boolean result = loginPage.isLoggedIn();
			setResult(result, "Login Test");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Login", ex, isSaucelabs);
			assertTrue(false);
		}
	}

	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="loginTest")
	public void createPersonTest(TestEnvironment testEnvironment) throws Throwable {
		try{
			HomeForAgentsPage homePage = new HomeForAgentsPage(driver, properties.getEnviroment());
			person = new PersonData(properties);
			
			homePage.createPersonCheckingInviteSS(person);
			homePage.sendInviteEmail(person);
			
			boolean result = homePage.checkIfPersonIsCreated(person);
			
			setResult(result, "Create Person");
			Debugger.log("createPerson => "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Create Person", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
//	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
//	public void firstLogin(TestEnvironment testEnvironment)
//			throws Exception {
//		
//		String firstLoginToken = MailManager.getAuthLink(properties.getNewEmployeeEmail(), properties.getGmailPassword(), "Invitation to signup to Alpha 6 HR Portal");
//		ResetPasswordPage resetPasswordPage = new ResetPasswordPage(driver, properties.getEnviroment(), firstLoginToken);
//		
//		resetPasswordPage.loadPage();
//		resetPasswordPage.changePassword(properties.getNewEmployeePassword());
//		
//		assertTrue(resetPasswordPage.checkIfEmployeePasswordWasChange());
//	}
}
