package insynctive.tests;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import insynctive.model.PersonData;
import insynctive.pages.insynctive.OpenEnrollmentPage;
import insynctive.utils.data.TestEnvironment;

@Deprecated
public class OpenEnrollmentTest extends TestMachine {

	PersonData person;

	@BeforeClass(alwaysRun = true)
	public void tearUp() throws Exception {
		super.tearUp();
		person = new PersonData(String.valueOf(account.getRunIDString()));
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
		login("InsynctiveTestNG+206@gmail.com", "password", null);
		
		OpenEnrollmentPage openEnrollmentPage = new OpenEnrollmentPage(driver, properties.getEnvironment());
		openEnrollmentPage.waitPageIsLoad();

		assertTrue(openEnrollmentPage.startUpdateInfo());
	}
	
	
}
