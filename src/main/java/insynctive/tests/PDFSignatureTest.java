package insynctive.tests;

import static org.junit.Assert.assertTrue;
import insynctive.pages.insynctive.MyTasksPage;
import insynctive.pages.insynctive.hr.CheckListsPage;
import insynctive.pages.insynctive.hr.HomeForAgentsPage;
import insynctive.utils.Sleeper;
import insynctive.utils.data.TestEnvironment;
import insynctive.utils.reader.InsynctivePropertiesReader;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PDFSignatureTest extends TestMachine {
	
	@BeforeClass(alwaysRun = true)
	public void tearUp() throws Exception {
		properties = InsynctivePropertiesReader.getAllProperties(driver);
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
		new HomeForAgentsPage(driver, properties.getEnviroment()).waitPageIsLoad();
		
				CheckListsPage checkListPage = new CheckListsPage(driver, properties.getEnviroment());
				checkListPage.loadPage();
				checkListPage.startChecklist("PDF", "Eugenio Valeiras");
				
				Sleeper.sleep(3000, driver);
				MyTasksPage myTasksPage = new MyTasksPage(driver, properties.getEnviroment());
				myTasksPage.loadPage();
				myTasksPage.openJustNowTask();
				
				assertTrue(myTasksPage.SingPDF());
		}
		
	}
	

