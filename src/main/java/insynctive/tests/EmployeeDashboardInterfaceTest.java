package insynctive.tests;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
			login("/Insynctive.Hub/Protected/Dashboard.aspx");
			long endTime = System.nanoTime();
			setResult(true, "Login Test", endTime - startTime);
			Debugger.log("loginTest => "+true, isSaucelabs);
			assertTrue(true);
		} catch(Exception ex){
			long endTime = System.nanoTime();
			failTest("Login",  ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="loginTest")
	public void isAllDataOK(TestEnvironment testEnvironment) throws Exception {

		long startTime = System.nanoTime();
		try{ 
			EmployeeDashboardPage employeeDashboard = new EmployeeDashboardPage(driver, properties.getEnvironment());
			long endTime = System.nanoTime();
			Boolean result = employeeDashboard.isAllDataOK(person);
			
			Debugger.log("isAllDataOK => "+result, isSaucelabs);
			setResult(result, "Is all Data Ok in Dashboard?", endTime - startTime);
			assertTrue(result);
		
		} catch(Exception ex){
			long endTime = System.nanoTime();
			failTest("Is all Data Ok in Dashboard?",  ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="loginTest")
	public void updatePersonalInformation(TestEnvironment testEnvironment) throws Exception {
		
		long startTime = System.nanoTime();
		try{ 
			EmployeeDashboardPage employeeDashboard = new EmployeeDashboardPage(driver, properties.getEnvironment());
			long endTime = System.nanoTime();
			employeeDashboard.updatePersonalInformation(person, account.getRunIDString());
			
			boolean result = true;
			
			setResult(result, "Update Personal Information", endTime - startTime);
			Debugger.log("updatePersonalInformation => "+result, isSaucelabs);
			assertTrue(result);
			
		} catch(Exception ex){
			long endTime = System.nanoTime();
			failTest("Update Personal Information",  ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="loginTest")
	public void electBenefits(TestEnvironment testEnvironment) throws Exception {

		long startTime = System.nanoTime();
		try{ 
			EmployeeDashboardPage employeeDashboard = new EmployeeDashboardPage(driver, properties.getEnvironment());
			long endTime = System.nanoTime();
			employeeDashboard.electBenefits(person, account.getRunIDString());
			
			boolean result = true;
			
			setResult(result, "Elect Benefits", endTime - startTime);
			Debugger.log("electBenefits => "+result, isSaucelabs);
			assertTrue(result);
			
		} catch(Exception ex){
			long endTime = System.nanoTime();
			failTest("Elect Benefits",  ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="loginTest")
	public void fillAndSignBenefit(TestEnvironment testEnvironment) throws Exception {
		
		long startTime = System.nanoTime();
		try{ 
			EmployeeDashboardPage employeeDashboard = new EmployeeDashboardPage(driver, properties.getEnvironment());
			long endTime = System.nanoTime();
			List<String> benefits = new ArrayList<>();
			benefits.add("VSP");
			benefits.add("Blue Shield");
			
			for(String benefitName : benefits){
				employeeDashboard.fillAndSignBenefit(benefitName);
			}
			
			boolean result = true;
			
			setResult(result, "Fill and Sign Benefits", endTime - startTime);
			Debugger.log("fillAndSignBenefit => "+result, isSaucelabs);
			assertTrue(result);
			
		} catch(Exception ex){
			long endTime = System.nanoTime();
			failTest("Fill and Sign Benefits",  ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	
}
