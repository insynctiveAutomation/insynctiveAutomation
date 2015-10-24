package insynctive.tests;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import insynctive.annotation.ParametersFront;
import insynctive.pages.insynctive.employee.EmployeeDashboardPage;
import insynctive.utils.Debugger;
import insynctive.utils.ParamObjectField;
import insynctive.utils.data.TestEnvironment;

public class EmployeeDashboardInterfaceTest extends TestMachine {

	@BeforeClass
	@Parameters({"accountID", "bowser", "testID"})
	public void tearUp(String accountID, String bowser, String testID) throws Exception {
		super.testSuiteID = Integer.parseInt(testID);
		testEnvironment = TestEnvironment.valueOf(bowser);
		super.tearUp(Integer.valueOf(accountID));
		this.sessionName = "Open Enrollment Test";
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(attrs={ParamObjectField.LOGIN_USERNAME, ParamObjectField.LOGIN_PASSWORD}, labels={"Login Username", "Login Password"})
	public void loginTest(@Optional("TestID") Integer testID)
			throws Exception {
		changeParamObject(testID);
		startTest(testEnvironment);

		try{ 
			login("/Insynctive.Hub/Protected/Dashboard.aspx");
			setResult(true, "Login Test");
			Debugger.log("loginTest => "+true, isSaucelabs);
			assertTrue(true);
		} catch(Exception ex){
			failTest("Login",  ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="loginTest")
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.NAME, ParamObjectField.LAST_NAME, ParamObjectField.EMAIL, ParamObjectField.PRIMARY_PHONE}, 
			labels={"Employee Name", "Employee Last Name", "Employee Email", "Employee Primary Phone"})
	public void isAllDataOK(@Optional("TestID") Integer testID) throws Exception {
		changeParamObject(testID);
		try{ 
			EmployeeDashboardPage employeeDashboard = new EmployeeDashboardPage(driver, properties.getEnvironment());
			Boolean result = employeeDashboard.isAllDataOK(paramObject);
			
			Debugger.log("isAllDataOK => "+result, isSaucelabs);
			setResult(result, "Is all Data Ok in Dashboard?");
			assertTrue(result);
		
		} catch(Exception ex){
			failTest("Is all Data Ok in Dashboard?",  ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="loginTest")
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.BIRTH_DATE, ParamObjectField.GENDER, ParamObjectField.MARITAL_STATUS, ParamObjectField.SSN, ParamObjectField.PRIMARY_PHONE, ParamObjectField.US_ADDRESS}, 
			labels={"Birth Date", "Gender", "Marital Status", "SSN", "Primary Phone", "US Address"})
	public void updatePersonalInformationHappyPath(@Optional("TestID") Integer testID) throws Exception {
		changeParamObject(testID);
		try{ 
			EmployeeDashboardPage employeeDashboard = new EmployeeDashboardPage(driver, properties.getEnvironment());
			employeeDashboard.updatePersonalInformationHappyPath(paramObject, account.getRunIDString());
			
			boolean result = true;
			
			setResult(result, "Update Personal Information");
			Debugger.log("updatePersonalInformation => "+result, isSaucelabs);
			assertTrue(result);
			
		} catch(Exception ex){
			failTest("Update Personal Information",  ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="loginTest")
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.BIRTH_DATE, ParamObjectField.GENDER, ParamObjectField.MARITAL_STATUS, ParamObjectField.SSN, ParamObjectField.PRIMARY_PHONE, ParamObjectField.US_ADDRESS}, 
			labels={"Birth Date", "Gender", "Marital Status", "SSN", "Primary Phone", "US Address"})
	public void updatePersonalInformatioWithErrors(@Optional("TestID") Integer testID) throws Exception {
		changeParamObject(testID);
		try{ 
			EmployeeDashboardPage employeeDashboard = new EmployeeDashboardPage(driver, properties.getEnvironment());
			employeeDashboard.updatePersonalInformationWithErrors(paramObject, account.getRunIDString());
			
			boolean result = true;
			
			setResult(result, "Update Personal Information");
			Debugger.log("updatePersonalInformation => "+result, isSaucelabs);
			assertTrue(result);
			
		} catch(Exception ex){
			failTest("Update Personal Information",  ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="loginTest")
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.MEDICAL_BENEFIT_NAME, ParamObjectField.DENTAL_BENEFIT_NAME, ParamObjectField.VISION_BENEFIT_NAME}, 
			labels={"Medica Benefit Name", "Dental Benefit Name", "Vision Benefit Name"})
	public void electBenefits(@Optional("TestID") Integer testID) throws Exception {
		changeParamObject(testID);
		try{ 
			EmployeeDashboardPage employeeDashboard = new EmployeeDashboardPage(driver, properties.getEnvironment());
			employeeDashboard.electBenefits(paramObject, account.getRunIDString());
			
			boolean result = true;
			
			setResult(result, "Elect Benefits");
			Debugger.log("electBenefits => "+result, isSaucelabs);
			assertTrue(result);
			
		} catch(Exception ex){
			failTest("Elect Benefits",  ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="loginTest")
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.MEDICAL_BENEFIT_COMPANY, ParamObjectField.DENTAL_BENEFIT_COMPANY, ParamObjectField.VISION_BENEFIT_COMPANY}, 
			labels={"Medican Benefit Company", "Dental Benefit Company", "Vision Benefit Company"})
	public void fillAndSignBenefit(@Optional("TestID") Integer testID) throws Exception {
		changeParamObject(testID);
		try{ 
			EmployeeDashboardPage employeeDashboard = new EmployeeDashboardPage(driver, properties.getEnvironment());
			Set<String> benefits = new HashSet<String>();
			
			benefits.add(paramObject.getMedicalBenefit().getCompany());
			benefits.add(paramObject.getDentalBenefit().getCompany());
			benefits.add(paramObject.getVisionBenefit().getCompany());
			
			for(String benefitName : benefits){
				employeeDashboard.fillAndSignBenefit(benefitName);
			}
			
			boolean result = true;
			
			setResult(result, "Fill and Sign Benefits");
			Debugger.log("fillAndSignBenefit => "+result, isSaucelabs);
			assertTrue(result);
			
		} catch(Exception ex){
			failTest("Fill and Sign Benefits",  ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	
}
