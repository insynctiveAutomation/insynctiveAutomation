package insynctive.tests;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import insynctive.pages.insynctive.LoginPage;
import insynctive.pages.insynctive.employee.EmployeeDashboardPage;
import insynctive.utils.Debugger;
import insynctive.utils.data.TestEnvironment;

public class EmployeeDashboardInterfaceTest extends TestMachine {

	@Override
	@BeforeClass
	public void tearUp() throws Exception {
		super.tearUp();
		this.sessionName = "Employee Dashboard Test";
	}
	
	@DataProvider(name = "hardCodedBrowsers", parallel = true)
	public static Object[][] sauceBrowserDataProvider(Method testMethod) {
		return new Object[][] { new Object[] { TestEnvironment.CHROME }
		};
	}
	
	@Test(dataProvider = "hardCodedBrowsers")
	public void loginTest(TestEnvironment testEnvironment)
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
	
	@Test(dataProvider = "hardCodedBrowsers")
	public void updatePersonalInformation(TestEnvironment testEnvironment) throws Exception {

		long startTime = System.nanoTime();
		try{ 
			EmployeeDashboardPage employeeDashboard = new EmployeeDashboardPage(driver, properties.getEnvironment());
			employeeDashboard.loadPage();
			employeeDashboard.updatePersonalInformation(person, account.getRunIDString());
			long endTime = System.nanoTime();
			
			setResult(true, "Update Personal Information", endTime - startTime);
			Debugger.log("updatePersonalInformation => "+true, isSaucelabs);
			assertTrue(true);
			
		} catch(Exception ex){
			long endTime = System.nanoTime();
			failTest("Update Personal Information",  ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	
}
