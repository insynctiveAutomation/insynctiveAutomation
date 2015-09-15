package insynctive.tests;

import insynctive.pages.insynctive.OpenEnrollmentPage;
import insynctive.utils.data.TestEnvironment;
import insynctive.utils.reader.InsynctivePropertiesReader;

import java.lang.reflect.Method;
import static org.junit.Assert.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OpenEnrollmentTest extends TestMachine {

	//	@AfterClass(alwaysRun = true)
//	public void teardown() {
//		this.driver.quit();
//	}

	@BeforeClass(alwaysRun = true)
	public void tearUp() throws Exception {
		properties = InsynctivePropertiesReader.getAllAccountsProperties();
		properties.addCheckLists(driver);
		this.sessionName = "Open Enrollment";
	}

	@DataProvider(name = "hardCodedBrowsers", parallel = true)
	public static Object[][] sauceBrowserDataProvider(Method testMethod) {
		return new Object[][] { new Object[] { TestEnvironment.CHROME }
		};
	}
	
	@Test(dataProvider = "hardCodedBrowsers")
	public void updatePersonalInformation(TestEnvironment testEnvironment) throws Throwable {
		
		startTest(testEnvironment);
		login("InsynctiveTestNG+206@gmail.com", "password");
		
		OpenEnrollmentPage openEnrollmentPage = new OpenEnrollmentPage(driver, properties.getEnviroment());
		openEnrollmentPage.waitPageIsLoad();

		assertTrue(openEnrollmentPage.startUpdateInfo());
	}
	
	
}
