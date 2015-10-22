package insynctive.tests;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import insynctive.pages.insynctive.LoginPage;
import insynctive.pages.insynctive.employee.EmployeeDashboardPage;
import insynctive.utils.Debugger;
import insynctive.utils.data.TestEnvironment;

public class EmployeeDashboardInterfaceTest extends TestMachine {

	@BeforeClass
	@Parameters({"accountID", "bowser", "testID"})
	public void tearUp(String accountID, String bowser, String testID) throws Exception {
		super.testID = Integer.parseInt(testID);
		testEnvironment = TestEnvironment.valueOf(bowser);
		super.tearUp(Integer.valueOf(accountID));
		this.sessionName = "Open Enrollment Test";
	}
	
	@Test
	public void loginTest()
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
	
	@Test(dependsOnMethods="loginTest")
	public void isAllDataOK() throws Exception {

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
	
	@Test(dependsOnMethods="loginTest")
	public void updatePersonalInformationHappyPath() throws Exception {
		
		long startTime = System.nanoTime();
		try{ 
			EmployeeDashboardPage employeeDashboard = new EmployeeDashboardPage(driver, properties.getEnvironment());
			long endTime = System.nanoTime();
			employeeDashboard.updatePersonalInformationHappyPath(person, account.getRunIDString());
			
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
	
	@Test(dependsOnMethods="loginTest")
	public void updatePersonalInformatioWithErrors() throws Exception {
		
		long startTime = System.nanoTime();
		try{ 
			EmployeeDashboardPage employeeDashboard = new EmployeeDashboardPage(driver, properties.getEnvironment());
			long endTime = System.nanoTime();
			employeeDashboard.updatePersonalInformationWithErrors(person, account.getRunIDString());
			
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
	
	@Test(dependsOnMethods="loginTest")
	public void electBenefits() throws Exception {

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
	
	@Test(dependsOnMethods="loginTest")
	public void fillAndSignBenefit() throws Exception {
		
		long startTime = System.nanoTime();
		try{ 
			EmployeeDashboardPage employeeDashboard = new EmployeeDashboardPage(driver, properties.getEnvironment());
			long endTime = System.nanoTime();
			Set<String> benefits = new HashSet<String>();
			
			benefits.add(person.getMedicalBenefit().getCompany());
			benefits.add(person.getDentalBenefit().getCompany());
			benefits.add(person.getVisionBenefit().getCompany());
			
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
