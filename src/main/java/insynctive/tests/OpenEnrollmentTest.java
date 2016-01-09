package insynctive.tests;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import insynctive.model.ParamObject;
import insynctive.pages.insynctive.OpenEnrollmentPage;
import insynctive.utils.data.TestEnvironment;

@Deprecated
public class OpenEnrollmentTest extends TestMachine {

	ParamObject person;

	@BeforeClass
	@Parameters({"environment", "browser", "isRemote", "isNotification", "testSuiteID", "testName"})
	public void tearUp(String environment, String browser, String isRemote, String isNotification, String testSuiteID, String testName) throws Exception {
		tearUp(browser, environment, isRemote, isNotification, testSuiteID);
		this.sessionName = testName;
	}

	@DataProvider(name = "hardCodedBrowsers", parallel = true)
	public static Object[][] sauceBrowserDataProvider(Method testMethod) {
		return new Object[][] { new Object[] { TestEnvironment.CHROME }
		};
	}
	
	@Test(dataProvider = "hardCodedBrowsers")
	public void updatePersonalInformation(TestEnvironment testEnvironment) throws Throwable {
		
		startTest();
		login("InsynctiveTestNG+206@gmail.com", "password", null);
		
		OpenEnrollmentPage openEnrollmentPage = new OpenEnrollmentPage(driver, environment);
		openEnrollmentPage.waitPageIsLoad();

		assertTrue(openEnrollmentPage.startUpdateInfo());
	}
	
	
}
