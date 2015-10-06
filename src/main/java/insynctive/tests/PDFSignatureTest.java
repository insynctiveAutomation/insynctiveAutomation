package insynctive.tests;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import insynctive.pages.insynctive.MyTasksPage;
import insynctive.pages.insynctive.agent.hr.CheckListsPage;
import insynctive.pages.insynctive.agent.hr.HomeForAgentsPage;
import insynctive.utils.Sleeper;
import insynctive.utils.data.TestEnvironment;

public class PDFSignatureTest extends TestMachine {
	
	@BeforeClass(alwaysRun = true)
	public void tearUp() throws Exception {
		super.tearUp();
		this.sessionName = "PDF Signature";
	}

	@DataProvider(name = "hardCodedBrowsers", parallel = true)
	public static Object[][] sauceBrowserDataProvider(Method testMethod) {
		return new Object[][] { new Object[] { TestEnvironment.CHROME }
		};
	}
	
	@Test(dataProvider = "hardCodedBrowsers")
	public void PDF(TestEnvironment testEnvironment)
			throws Exception {

		startTest(testEnvironment);
		
		login();
		new HomeForAgentsPage(driver, properties.getEnvironment()).waitPageIsLoad();
		
				CheckListsPage checkListPage = new CheckListsPage(driver, properties.getEnvironment());
				checkListPage.loadPage();
				checkListPage.startChecklist("PDF", "Eugenio Valeiras");
				
				Sleeper.sleep(3000, driver);
				MyTasksPage myTasksPage = new MyTasksPage(driver, properties.getEnvironment());
				myTasksPage.loadPage();
				myTasksPage.openJustNowTask();
				
				assertTrue(myTasksPage.SingPDF());
		}
		
	}
	

