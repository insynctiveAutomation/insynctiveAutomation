package insynctive.tests;

import static org.junit.Assert.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import insynctive.annotation.ParametersFront;
import insynctive.pages.insynctive.LoginPage;
import insynctive.pages.insynctive.PersonFilePage;
import insynctive.pages.insynctive.agent.hr.CheckListsPage;
import insynctive.pages.insynctive.employee.EmployeeDocumentsPage;
import insynctive.pages.insynctive.employee.EmployeeTaskPage;
import insynctive.pages.pdf.I9SectionOne;
import insynctive.pages.pdf.I9SectionTwo;
import insynctive.pages.pdf.W4PDF;
import insynctive.utils.Debugger;
import insynctive.utils.ParamObjectField;
import insynctive.utils.Sleeper;
import insynctive.utils.data.Checklist;

public class NewPDFFillAndSignTest extends TestMachine {
	
	@BeforeClass
	@Parameters({"environment", "browser", "isRemote", "isNotification", "testSuiteID", "testName"})
	public void tearUp(String environment, String browser, String isRemote, String isNotification, String testSuiteID, String testName) throws Exception {
		tearUp(browser, environment, isRemote, isNotification, testSuiteID);
		this.sessionName = testName;
	}

	@Test
	@Parameters({"TestID"})
	@ParametersFront(attrs={ParamObjectField.LOGIN_USERNAME, ParamObjectField.LOGIN_PASSWORD}, 
	labels={"Login Username", "Login Password"})
	public void loginTest1(@Optional("TestID") Integer testID) throws Exception {
		changeParamObject(testID);
		startTest();
		try{ 
			LoginPage loginPage = login("/Insynctive.Hub/Protected/Tasks.aspx");
			Sleeper.sleep(1500, driver);
			boolean result = loginPage.isLoggedIn(50);
			Debugger.log("loginTest => "+result, isRemote);
			setResult(result, "Login Test");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Login Test", ex, isRemote);
			assertTrue(false);
		}
	}

	@Test
	@Parameters({"TestID"})
	@ParametersFront(attrs={ParamObjectField.LOGIN_USERNAME, ParamObjectField.LOGIN_PASSWORD}, 
	labels={"Login Username", "Login Password"})
	public void loginTest2(@Optional("TestID") Integer testID) throws Exception {
		changeParamObject(testID);
		try{ 
			LoginPage loginPage = login("/Insynctive.Hub/Protected/Tasks.aspx");
			Sleeper.sleep(1500, driver);
			boolean result = loginPage.isLoggedIn(50);
			Debugger.log("loginTest => "+result, isRemote);
			setResult(result, "Login Test");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Login Test", ex, isRemote);
			assertTrue(false);
		}
	}

	@Test
	@Parameters({"TestID"})
	@ParametersFront(attrs={ParamObjectField.LOGIN_USERNAME, ParamObjectField.LOGIN_PASSWORD}, 
	labels={"Login Username", "Login Password"})
	public void loginTest3(@Optional("TestID") Integer testID) throws Exception {
		changeParamObject(testID);
		try{ 
			LoginPage loginPage = login("/Insynctive.Hub/Protected/Tasks.aspx");
			Sleeper.sleep(1500, driver);
			boolean result = loginPage.isLoggedIn(50);
			Debugger.log("loginTest => "+result, isRemote);
			setResult(result, "Login Test");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Login Test", ex, isRemote);
			assertTrue(false);
		}
	}

	@Test
	@Parameters({"TestID"})
	@ParametersFront(attrs={ParamObjectField.LOGIN_USERNAME, ParamObjectField.LOGIN_PASSWORD}, 
	labels={"Login Username", "Login Password"})
	public void loginTest4(@Optional("TestID") Integer testID) throws Exception {
		changeParamObject(testID);
		try{ 
			LoginPage loginPage = login("/Insynctive.Hub/Protected/Tasks.aspx");
			Sleeper.sleep(1500, driver);
			boolean result = loginPage.isLoggedIn(50);
			Debugger.log("loginTest => "+result, isRemote);
			setResult(result, "Login Test");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Login Test", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={}, 
			labels={"No parameters needed"})
	public void logOut1(@Optional("TestID") Integer testID) throws Exception{
		logOut();
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={}, 
			labels={"No parameters needed"})
	public void logOut2(@Optional("TestID") Integer testID) throws Exception{
		logOut();
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={}, 
			labels={"No parameters needed"})
	public void logOut3(@Optional("TestID") Integer testID) throws Exception{
		logOut();
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.CHECKLIST_NAME, ParamObjectField.NAME}, 
			labels={"Checklist Name", "Employee name"})
	public void startChecklist(@Optional("TestID") Integer testID) throws Throwable{
		changeParamObject(testID);
		CheckListsPage checklistPage = new CheckListsPage(driver, environment);
		checklistPage.loadPage();
		Checklist checklist = new Checklist(paramObject.getChecklistName());
		
		try{ 
			checklistPage.assignChecklist(checklist, paramObject.name, false);
			Sleeper.sleep(5000, driver);
			//TODO CHECK IF IS ASSIGNED
			boolean result = checklistPage.isChecklistAssigned(checklist);
			Debugger.log("startChecklist => "+result, isRemote);
			setResult(result, "Start Checklist");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Start Checklist", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(attrs={}, labels={"No parameters needed"})
	public void fillAndSignSectionOneI9(@Optional("TestID") Integer testID) throws Exception {
		changeParamObject(testID);
		try {
			EmployeeTaskPage taskPage = new EmployeeTaskPage(driver, environment);
			taskPage.openTask("Fill and Sign Section 1 of the I-9 form");
			
			I9SectionOne i9Form = new I9SectionOne(driver);
			i9Form.completeForm();
			
		} catch(Exception ex){
			failTest("Fill and Sign Section one I9", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(attrs={}, labels={"No parameters needed"})
	public void fillAndSignSectionTwoI9(@Optional("TestID") Integer testID) throws Exception {
		changeParamObject(testID);
		try {
			EmployeeTaskPage taskPage = new EmployeeTaskPage(driver, environment);
			taskPage.openTask("Fill and Sign Section 2");
			
			I9SectionTwo i9SectionTwoForm = new I9SectionTwo(driver);
			i9SectionTwoForm.checkInformationOfSectionOne();
			i9SectionTwoForm.fillInformation();
			i9SectionTwoForm.waitLoadingBar();
			
			Boolean result = !taskPage.isTaskPresent("Fill and Sign Section 2");
			
			Debugger.log("fillAndSignSectionTwoI9 => "+result, isRemote);
			setResult(result, "Section 2");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Fill and Sign Section Twoe I9", ex, isRemote);
			assertTrue(false);
		}
	}

	@Test
	@Parameters({"TestID"})
	@ParametersFront(attrs={ParamObjectField.NAME}, labels={"Document name"})
	public void checkDocumentInDocuments(@Optional("TestID") Integer testID) throws Exception {
		changeParamObject(testID);
		try {
			EmployeeDocumentsPage documentPage = new EmployeeDocumentsPage(driver, environment);
			documentPage.loadPage();
			documentPage.viewDocument(paramObject.name);
			Boolean result = documentPage.isDocumentOpened();
			
			Debugger.log("checkDocumentInDocuments => "+result, isRemote);
			setResult(result, "checkDocumentInDocuments");
			assertTrue(result);
			
		} catch(Exception ex){
			failTest("Check Document in Documents", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(attrs={}, labels={"No parameters needed"})
	public void fillAndSignW4Task(@Optional("TestID") Integer testID) throws Exception {
		changeParamObject(testID);
		try {
			EmployeeTaskPage taskPage = new EmployeeTaskPage(driver, environment);
			taskPage.openTask("Fill and Sign the Federal W-4 Form");
			
			W4PDF w4Pdf = new W4PDF(driver);
			w4Pdf.fillInformation();
			w4Pdf.waitLoadingBar();
			
			Boolean result = !taskPage.isTaskPresent("Fill and Sign the Federal W-4 Form");
			
			Debugger.log("fillAndSignW4Task => "+result, isRemote);
			setResult(result, "Fill and Sign W4 Task");
			assertTrue(result);
			
		} catch(Exception ex){
			failTest("Fill and Sign W4 Task", ex, isRemote);
			assertTrue(false);
		}
	}
}
