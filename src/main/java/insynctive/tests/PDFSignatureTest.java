package insynctive.tests;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import support.utils.externaltesting.TestEnvironment;
import insynctive.pages.insynctive.MyTasksPage;
import insynctive.pages.insynctive.agent.hr.CheckListsPage;
import insynctive.pages.insynctive.agent.hr.HomeForAgentsPage;
import insynctive.utils.Sleeper;

@Deprecated
public class PDFSignatureTest extends TestMachine {
	
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
	public void PDF(TestEnvironment testEnvironment)
			throws Exception {

		startTest();
		
		login();
		new HomeForAgentsPage(driver, environment).waitPageIsLoad();
		
		CheckListsPage checkListPage = new CheckListsPage(driver, environment);
		checkListPage.loadPage();
		checkListPage.startChecklist("PDF", "Eugenio Valeiras");
		
		Sleeper.sleep(3000, driver);
		MyTasksPage myTasksPage = new MyTasksPage(driver, environment);
		myTasksPage.loadPage();
		myTasksPage.openJustNowTask();
		
		assertTrue(myTasksPage.SingPDF());
	}
		
}
	

