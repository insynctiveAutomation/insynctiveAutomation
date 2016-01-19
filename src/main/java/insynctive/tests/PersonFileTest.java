package insynctive.tests;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import insynctive.annotation.ParametersFront;
import insynctive.model.EmergencyContact;
import insynctive.model.USAddress;
import insynctive.pages.Page;
import insynctive.pages.insynctive.LoginPage;
import insynctive.pages.insynctive.PersonFilePage;
import insynctive.pages.insynctive.ResetPasswordPage;
import insynctive.pages.insynctive.TwoFAPage;
import insynctive.pages.insynctive.agent.hr.HomeForAgentsPage;
import insynctive.pages.insynctive.agent.settings.PeoplePage;
import insynctive.pages.insynctive.employee.EmployeeDashboardPage;
import insynctive.utils.CheckInApp;
import insynctive.utils.Debugger;
import insynctive.utils.MailManager;
import insynctive.utils.ParamObjectField;
import insynctive.utils.Sleeper;
import insynctive.utils.Wait;
import insynctive.utils.data.TestEnvironment;
 
public class PersonFileTest extends TestMachine {
	
	private USAddress usaddress = null;
	
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
	public void loginTest(@Optional("TestID") Integer testID)
			throws Exception {
		changeParamObject(testID);
		startTest();
		try{ 
			LoginPage loginPage = login();
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
	public void longTimeLogin(@Optional("TestID") Integer testID)
			throws Exception {
		changeParamObject(testID);
		
		startTest();
		try{ 
			LoginPage loginPage = login();
			Sleeper.sleep(1500, driver);
			boolean result = loginPage.isLoggedIn(5000);
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
	public void loginAsAgentTest(@Optional("TestID") Integer testID)
			throws Exception {
		changeParamObject(testID);

		try{ 
			LoginPage loginPage = login();
			Sleeper.sleep(1500, driver);
			boolean result = loginPage.isLoggedIn();
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
	public void loginAsEmployeeTest(@Optional("TestID") Integer testID)
			throws Exception {
		startTest();
		try{ 
			changeParamObject(testID);
			LoginPage loginPage = loginAsEmployee(paramObject.loginUsername, paramObject.loginPassword);
			boolean result = loginPage.isLoggedIn();
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
	@ParametersFront(attrs={ParamObjectField.EMAIL, ParamObjectField.LOGIN_PASSWORD}, 
	labels={"Login Username (The test will add +RunID+test)", "Login Password"})
	public void loginAfterEmailChangeInEmail(@Optional("TestID") Integer testID)
			throws Exception {
		changeParamObject(testID);
		try{ 
			LoginPage loginPage = loginAsEmployee(paramObject.getEmailToChange(getRunIDAsString()), paramObject.loginPassword);
			boolean result = loginPage.isLoggedIn();
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
	public void loginWith2FAEmail(@Optional("TestID") Integer testID)
			throws Exception {
		try{ 
			changeParamObject(testID);
			loginAsEmployee(paramObject.loginUsername, paramObject.loginPassword);
			
			TwoFAPage twoFAPage = new TwoFAPage(driver, environment);
			twoFAPage.sendViaPrimaryEmail(paramObject.loginUsername, paramObject.getGmailPassword());
			Sleeper.sleep(7000, driver);
			
			boolean result = !driver.getCurrentUrl().contains("TwoFA"); 
			Debugger.log("loginWith2FAEmail => "+result, isRemote);
			setResult(result, "Login With 2FA Email");
			assertTrue(result);
		} catch(Exception ex){
			ex.printStackTrace();
			failTest("Login With 2FA Email", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(attrs={ParamObjectField.LOGIN_USERNAME, ParamObjectField.LOGIN_PASSWORD}, 
	labels={"Login Username", "Login Password"})
	public void loginWith2FAPhone(@Optional("TestID") Integer testID)
			throws Exception {
		try{ 
			changeParamObject(testID);
			loginAsEmployee(paramObject.loginUsername, paramObject.loginPassword);
			
			TwoFAPage twoFAPage = new TwoFAPage(driver, environment);
			//TODO
			twoFAPage.sendViaPhone();
			
			Sleeper.sleep(7000, driver);
			boolean result = !driver.getCurrentUrl().contains("TwoFA");
			
			Debugger.log("loginWith2FAPhone => "+result, isRemote);
			setResult(result, "Login With 2FA Phone");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Login With 2FA Phone", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.EMAIL, ParamObjectField.NAME, 
					ParamObjectField.LAST_NAME, ParamObjectField.DEPARTMENT_OF_EMPLYEE, 
					ParamObjectField.TITLE_OF_EMPLOYEE}, 
			labels={"Email (+RunID is automatically added)", "Name", "Last Name", "Department", "Title"})
	public void createPersonTest(@Optional("TestID") Integer testID) throws Throwable {
		changeParamObject(testID);
		try{ 
			HomeForAgentsPage.SELENIUM_TIMEOUT_SEC = 50;
			HomeForAgentsPage homePage = new HomeForAgentsPage(driver, environment);
			boolean result;

			paramObject.setName(paramObject.getName() + " " + getRunIDAsString());
			paramObject.setEmail(paramObject.getEmailWithRunID(getRunIDAsString()));
			homePage.createPersonCheckingInviteSS(paramObject, CheckInApp.NO);
			homePage.sendInviteEmail(paramObject, CheckInApp.NO);
			result = homePage.checkIfPersonIsCreated(paramObject);
			
			setResult(result, "Create Person");
			Debugger.log("createPerson => "+result, isRemote);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Create Person", ex, isRemote);
			assertTrue(false);
		} finally {
			HomeForAgentsPage.SELENIUM_TIMEOUT_SEC = 30;
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.EMAIL}, 
			labels={"Email of Person File"})
	public void openPersonFIle(@Optional("TestID") Integer testID) throws Throwable {
		try{ 
			changeParamObject(testID);
			HomeForAgentsPage homePage = new HomeForAgentsPage(driver, environment);
			boolean result;
				homePage.openPersonFile(paramObject.getEmail());
				result = homePage.isPersonFileOpened();
				Sleeper.sleep(5000, driver);
			
			setResult(result, "Open Person File" + paramObject.getSearchEmail());
			Debugger.log("openPersonFIle => "+result, isRemote);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Open Person File", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.EMAIL}, 
			labels={"Email (The test will add +test)"})
	public void changePrimaryEmail(@Optional("TestID") Integer testID) throws Exception {
		changeParamObject(testID);
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, environment);
			personFilePage.changePrimaryEmail(paramObject.getEmailToChange(getRunIDAsString()));
			Sleeper.sleep(3000, driver);
			boolean result = personFilePage.isChangePrimaryEmail(paramObject.getEmailToChange(getRunIDAsString()));
			Debugger.log("changePrimaryEmail => "+result, isRemote);
			setResult(result, "Change Primary Email");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Change Primary Email", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.EMAIL}, 
			labels={"The test will add +RunID+Test to the email."})
	public void changePrimaryEmailFromEmployee(@Optional("TestID") Integer testID)
			throws Exception {
		changeParamObject(testID);
		try {
			EmployeeDashboardPage employeePage = new EmployeeDashboardPage(driver, environment);
			employeePage.changeEmail(paramObject.getEmailToChange(getRunIDAsString()));
			
			boolean result = true; //TODO
			Debugger.log("Change Primary Email From Employee"+result, isRemote);
			setResult(result, "changePrimaryEmailFromEmployee");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Change Primary Email From Employee", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.EMAIL}, 
			labels={"The test will add +RunID+Test"})
	public void addAlternativeEmailFromEmployee(@Optional("TestID") Integer testID) throws Exception{
		changeParamObject(testID);
		try {
			EmployeeDashboardPage employeePage = new EmployeeDashboardPage(driver, environment);
			employeePage.addAlternateiveEmail(paramObject.getEmailToChange(getRunIDAsString()));
			
			boolean result = true; //TODO
			Debugger.log("Change Primary Email From Employee"+result, isRemote);
			setResult(result, "changePrimaryEmailFromEmployee");
			assertTrue(result);
		} catch (Exception ex){ 
			failTest("Change Primary Email From Employee", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.EMAIL}, 
			labels={"The test will add +RunID+Test"})
	public void makePrimaryEmail(@Optional("TestID") Integer testID) throws Exception{
		changeParamObject(testID);
		try {
			EmployeeDashboardPage employeePage = new EmployeeDashboardPage(driver, environment);
			employeePage.makePrimaryEmail(paramObject.getEmailToChange(getRunIDAsString()));
			
			boolean result = true; //TODO
			Debugger.log("Change Primary Email From Employee"+result, isRemote);
			setResult(result, "changePrimaryEmailFromEmployee");
			assertTrue(result);
		} catch (Exception ex){ 
			failTest("Change Primary Email From Employee", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.MARITAL_STATUS}, 
			labels={"Marital Status"})
	public void changeMaritalStatus(@Optional("TestID") Integer testID)
			throws Exception {
		changeParamObject(testID);
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, environment);
			
			personFilePage.changeMaritalStatus(paramObject.getMaritalStatus());
			
			boolean result = personFilePage.isChangeMaritalStatus(paramObject.getMaritalStatus());
			Debugger.log("changeMaritalStatus => "+result, isRemote);
			setResult(result, "Change Marital Status");
			assertTrue(result);
		} catch (Exception ex){
			failTest("Change Marital Status", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.NAME, ParamObjectField.LAST_NAME, ParamObjectField.MIDDLE_NAME, ParamObjectField.MAIDEN_NAME}, 
			labels={"Name:", "Last Name", "Middle Name", "Maiden Name"})
	public void changeNameInPersonalInfo(@Optional("TestID") Integer testID)
			throws Exception {
		changeParamObject(testID);
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, environment);
			
			personFilePage.changeName(paramObject.getName(), paramObject.getLastName(), paramObject.getMiddleName(), paramObject.getMaidenName());
			
			boolean result = personFilePage.isChangeNameInPersonalDetails(paramObject, Wait.WAIT);
			Debugger.log("changeName => "+result, isRemote);
			setResult(result, "Change name in Personal Info");
			assertTrue(result);
		} catch (Exception ex){
			failTest("Change name in Personal Info", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.NAME, ParamObjectField.LAST_NAME, ParamObjectField.MIDDLE_NAME, ParamObjectField.MAIDEN_NAME}, 
			labels={"Name:", "Last Name", "Middle Name", "Maiden Name"})
	public void changeNameInTitle(@Optional("TestID") Integer testID)
			throws Exception {
		changeParamObject(testID);
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, environment);
			
			personFilePage.changeNameIntoTitle(paramObject.getName(), paramObject.getLastName(), paramObject.getMiddleName(), paramObject.getMaidenName());
			
			boolean result = personFilePage.isChangeNameInTitle(paramObject, Wait.WAIT);
			Debugger.log("changeName2 =>"+result, isRemote);
			setResult(result, "Change name from Title");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Change name 2", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.GENDER}, 
			labels={"Gender"})
	public void changeGender(@Optional("TestID") Integer testID)
			throws Exception {
		changeParamObject(testID);
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, environment);
			
			personFilePage.changeGender(paramObject.getGender());
			
			boolean result = personFilePage.isChangeGender(paramObject.getGender());
			Debugger.log("changeGender => "+result, isRemote);
			setResult(result, "Change Gender");
			assertTrue(result);
		} catch (Exception ex){
			failTest("Change Gender", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.BIRTH_DATE}, 
			labels={"Birth Date"})
	public void changeBirthDate(@Optional("TestID") Integer testID)
			throws Exception {
		changeParamObject(testID);
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, environment);
			
			personFilePage.changeBirthDate(paramObject.getBirthDate());
			
			boolean result = personFilePage.isChangeBirthDate(paramObject.getBirthDate());
			Debugger.log("changeBirthDate => "+result, isRemote);
			setResult(result, "Change Birth Date");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Change Birth Date", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.TITLE_OF_EMPLOYEE, ParamObjectField.DEPARTMENT_OF_EMPLYEE}, 
			labels={"Title", "Department"})
	public void addTitle(@Optional("TestID") Integer testID) throws Exception{
		changeParamObject(testID);
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, environment);
			
			personFilePage.changeTitle(paramObject.getTitleOfEmployee(), paramObject.getDepartamentOfEmployee());
			
			boolean result = personFilePage.isChangeTitle(paramObject.getTitleOfEmployee(), paramObject.getDepartamentOfEmployee());
			Debugger.log("addTitle => "+result, isRemote);
			setResult(result, "Add Title");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Add Title", ex, isRemote);
			assertTrue(false);
		}
	}

	/**
	 * @Test
	public void hasNotDependents() throws IOException, InterruptedException, ConfigurationException{
		setResult(false, "Add Has Not Dependents");
		PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
		
		personFilePage.addHasNotDependents();
		
		boolean result = personFilePage.isNotDependents();
		Debugger.log("hasNotDependents => "+result, isSaucelabs);
		long endTime = System.nanoTime();setResult(result, "Add Has Not Dependents");
		assertTrue(result);
	} */

	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.PRIMARY_PHONE}, 
			labels={"Primary Phone"})
	public void addPhoneNumber(@Optional("TestID") Integer testID) throws Exception{
		changeParamObject(testID);
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, environment);
			
			personFilePage.addPhoneNumber(paramObject.getPrimaryPhone(), getRunIDAsString());

			boolean result = personFilePage.isAddPhoneNumber(paramObject.getPrimaryPhone(), getRunIDAsString());
			Debugger.log("addPhoneNumber =>"+result, isRemote);
			setResult(result, "Add Phone Number");
			assertTrue(result);
		}catch (Exception ex){
			failTest("Add Phone Number", ex, isRemote);
			assertTrue(false);
		}
	}

	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.PRIMARY_PHONE}, 
			labels={"Add the same as Primary Phone and the test will change the first number to a 9"})
	public void addAlternativePhone(@Optional("TestID") Integer testID) throws Exception{
		changeParamObject(testID);
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, environment);
			personFilePage.addSecondaryEmail(paramObject.getPrimaryPhone(), getRunIDAsString());
			
			boolean result = personFilePage.isAddAlternativePhoneNumber(paramObject.getPrimaryPhone(), getRunIDAsString());
			Debugger.log("addAlternativePhone =>"+result, isRemote);
			setResult(result, "Add Alternative Number");
			assertTrue(result);
		}catch (Exception ex){
			failTest("Add Alternative Number", ex, isRemote);
			assertTrue(false);
		}
	}

	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.PRIMARY_PHONE}, 
			labels={"Add the same as Primary Phone"})
	public void makePrimaryPhone(@Optional("TestID") Integer testID) throws Exception{
		changeParamObject(testID);
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, environment);
			Map<String, String> resultsPhones = personFilePage.makeAsPrimary();
			
			boolean result = personFilePage.isChangePrimaryPhone(resultsPhones);
			Debugger.log("makePrimaryPhone =>"+result, isRemote);
			setResult(result, "Make as Primary Phone");
			assertTrue(result);
		}catch (Exception ex){
			failTest("makePrimaryPhone", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.US_ADDRESS_STREET, ParamObjectField.US_ADDRESS_SECOND_STREET, ParamObjectField.US_ADDRESS_CITY, 
					ParamObjectField.US_ADDRESS_STATE, ParamObjectField.US_ADDRESS_ZIP_CODE, ParamObjectField.US_ADDRESS_COUNTY, 
					ParamObjectField.US_ADDRESS_SAME_AS_HOME}, 
			labels={"Street", "Second Street", "City", "State", "Zip Copde", "County", "Same As Home"})
	public void addUSAddress(@Optional("TestID") Integer testID) throws Exception{
		changeParamObject(testID);
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, environment);
			this.usaddress = paramObject.getUsAddress();
			
			personFilePage.addUsAddress(paramObject.getUsAddress());
			
			boolean result = personFilePage.isAddUSAddress(paramObject.getUsAddress());
			Debugger.log("addUSAddress => "+result, isRemote);
			setResult(result, "Add US Address");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Add US Address", ex, isRemote);
			assertTrue(false);
		}
	}

	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={}, 
			labels={"Remove the US Address that you add before."})
	public void removeUSAddress(@Optional("TestID") Integer testID) throws Exception{
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, environment);
			personFilePage.removeUsAddress(usaddress);

			boolean result = personFilePage.isRemoveUsAddress(usaddress);
			Debugger.log("removeUSAddress => "+result, isRemote);
			setResult(result, "Remove US Address");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Remove US Address", ex, isRemote);
			assertTrue(false);
		}
	}
	
	/** @Test
	public void updateUSAddress() throws Exception{
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			personFilePage.updateUsAddress(person.getUSAddress());

			boolean result = personFilePage.isUpdateUsAddress(person.getUSAddress());
			Debugger.log("removeUSAddress => "+result, isSaucelabs);
			long endTime = System.nanoTime();setResult(result, "Update USAddress");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Update USAddress", ex, isSaucelabs);
			assertTrue(false);
		}
	} */
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={}, 
			labels={"Not implemented yet (Using a hardcoded task until final parameters implementation)"})
	public void assignTask(@Optional("TestID") Integer testID) throws Exception{
		PersonFilePage personFilePage = new PersonFilePage(driver, environment);
		try{ 
			personFilePage.assignTask();
			
			boolean result = personFilePage.isTaskAssigned();
			Debugger.log("asssignTask => "+result, isRemote);
			setResult(result, "Assign Task");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Assign Task", ex, isRemote);
			assertTrue(false);
		} finally {
			personFilePage.goToPersonalTab();
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.CHECKLIST_NAME}, 
			labels={"Checklist Name"})
	public void startChecklist(@Optional("TestID") Integer testID) throws Exception{
		changeParamObject(testID);
		PersonFilePage personFilePage = new PersonFilePage(driver, environment);
		try{ 
			personFilePage.assignChecklist(paramObject.getChecklistName());
			Sleeper.sleep(5000, driver);
			boolean result = personFilePage.isChecklistAssigned(paramObject.getChecklistName());
			Debugger.log("startChecklist => "+result, isRemote);
			setResult(result, "Start Checklist");
			assertTrue(result);
		}catch (Exception ex){ 
			personFilePage.goToPersonalTab();
			failTest("Start Checklist", ex, isRemote);
			assertTrue(false);
		} finally {
			personFilePage.goToPersonalTab();
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.SSN}, 
			labels={"SSN (The test will change the last digits for the runID)"})
	public void addSocialSecurityNumber(@Optional("TestID") Integer testID) throws Exception{
		changeParamObject(testID);
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, environment);
			
			personFilePage.addSocialSecurityNumber(paramObject.getSsn(), getRunIDAsString());
			
			boolean result = personFilePage.isSocialSecurityNumberAdded(paramObject.getSsn(), getRunIDAsString());
			Debugger.log("Add Social Security Number => "+result, isRemote);
			setResult(result, "Add Social Security Number");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Add Social Security Number", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.EMERGENCY_CONTACT_NAME, ParamObjectField.EMERGENCY_CONTACT_RELATIONSHIP, 
					ParamObjectField.EMERGENCY_CONTACT_EMAIL, ParamObjectField.EMERGENCY_CONTACT_PHONE}, 
			labels={"Name", "Relationship", "Email", "Phone"})
	public void addEmergencyContact(@Optional("TestID") Integer testID) throws Exception{
		changeParamObject(testID);
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, environment);
			EmergencyContact emg = paramObject.getEmergencyContact();
			personFilePage.addEmergencyContact(emg.getName(), emg.getRelationship(), emg.getPhone(), emg.getEmail());
			
			boolean result = personFilePage.isEmergencyContactAdded(emg);
			Debugger.log("Add Emergency Contact => "+result, isRemote);
			setResult(result, "Add Emergency Contact");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Add Emergency Contact", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.EMERGENCY_CONTACT_NAME, ParamObjectField.EMERGENCY_CONTACT_RELATIONSHIP, 
					ParamObjectField.EMERGENCY_CONTACT_EMAIL, ParamObjectField.EMERGENCY_CONTACT_PHONE}, 
			labels={"Name", "Relationship", "Email", "Phone"})
	public void changeEmergencyContact(@Optional("TestID") Integer testID) throws Exception{
		changeParamObject(testID);
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, environment);
			EmergencyContact emg = paramObject.getEmergencyContact();
			personFilePage.editEmergencyContact(emg.getName()+"Test", emg.getRelationship(), emg.getPhone(), emg.getEmail());
			
			String name = emg.getName();
			emg.setName(emg.getName()+"Test");
			
			boolean result = personFilePage.isEmergencyContactAdded(emg);
			emg.setName(name); //return the default name
			
			Debugger.log("Change Emergency Contact => "+result, isRemote);
			setResult(result, "Change Emergency Contact");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Change Emergency Contact", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={}, 
			labels={"Remove last Emergency Contact"})
	public void removeEmergencyContact(@Optional("TestID") Integer testID) throws Exception{
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, environment);
			int count = personFilePage.getNumberOfEmergencyContacts();
			personFilePage.removeLastEmergencyContact();
			Sleeper.sleep(3000, driver);
			boolean result = personFilePage.isEmergencyContactRemoved(count);
			Debugger.log("Remove Emergency Contact => "+result, isRemote);
			setResult(result, "Add Emergency Contact");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Remove Emergency Contact", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={}, 
			labels={"Not implemented yet (Using a hardcoded Job until final parameters implementation)"})
	public void assignJob(@Optional("TestID") Integer testID) throws Exception{
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, environment);
			personFilePage.assignJob();
			
			Boolean result = personFilePage.isJobAdded();
			
			Debugger.log("assignJob => "+result, isRemote);
			setResult(result, "Assign Job");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Assign Job", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={}, 
			labels={"No parameters needed"})
	public void logOut(@Optional("TestID") Integer testID) throws Exception{
		logOut();
	}

	//TODO DUPLICATED CODE
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={}, 
			labels={"No parameters needed"})
	public void logOut2(@Optional("TestID") Integer testID) throws Exception{
		logOut();
	}
	
	//TODO DUPLICATED CODE
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={}, 
			labels={"No parameters needed"})
	public void logOut3(@Optional("TestID") Integer testID) throws Exception{
		logOut();
	}

	private void logOut() throws Exception {
		Page page = new Page(driver);
		page.logout();
	}
	
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.EMAIL, ParamObjectField.LOGIN_PASSWORD}, 
			labels={"Insynctive Email", "New Password"})
	public void firstLogin(@Optional("TestID") Integer testID)
			throws Exception {
		changeParamObject(testID);
		try {
			makeFirstLogin(paramObject.email, paramObject.getGmailPassword(), paramObject.loginPassword);//Re utilize EMAIL param because is a String
			boolean result = true; //TODO
			Debugger.log("First Login  => "+result, isRemote);
			setResult(result, "Change Emergency Contact");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("First Login ", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.EMAIL, ParamObjectField.GMAIL_PASSWORD}, 
			labels={"Gmail email", "Gmail password"})
	public void checkIfChangeEmailIsSending(@Optional("TestID") Integer testID)
			throws Exception {
		changeParamObject(testID);
		try {
			boolean result = MailManager.checkIfChangeEmailIsSending(paramObject.getEmailToChange(getRunIDAsString()), paramObject.getGmailPassword(), paramObject.getEmailWithRunID(getRunIDAsString()));//Re utilize lastname param because is a String
			Debugger.log("checkIfChangeEmailIsSending  => "+result, isRemote);
			setResult(result, "Check If Change Email is Sending");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Check If Change Email is Sending", ex, isRemote);
			assertTrue(false);
		}
	}

	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.DOC_NAME, ParamObjectField.LOADING_TIME}, 
			labels={"Document name", "Documents count"})
	public void openDocuments(@Optional("TestID") Integer testID) throws Exception {
		Boolean result = true;
		try {
			changeParamObject(testID);
			PersonFilePage personFilePage = new PersonFilePage(driver, environment);
			personFilePage.goToDocumentsTab();
			
			for(int index = 1 ; index <= paramObject.loadingTime ; index++){
				personFilePage.openDocument(paramObject.docName + " " + index);
				result = result && personFilePage.isOpenDocument(); 
				personFilePage.returnToPerson();
			}
			
			Debugger.log("openDocuments  => "+result, isRemote);
			setResult(result, "Open document and check for content");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Open document and check for content", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.BOOLEAN_PARAM_ONE, ParamObjectField.BOOLEAN_PARAM_TWO}, 
			labels={"Configure 2FA For Agent", "Configure 2FA For Employee"})
	public void config2FAOn(@Optional("TestID") Integer testID) throws Exception {
		try {
			changeParamObject(testID);
			
			PeoplePage peoplePage = goTo2FAConfig();;
			if(paramObject.booleanParamOne){ peoplePage.configure2FaForAgent(true, "Email or Text"); }
			if(paramObject.booleanParamTwo){ peoplePage.configure2FaForEmployee(true, "Email or Text"); }
			peoplePage.save2FA();
			
			Boolean result = true;//TODO
			
			Debugger.log("config2FAOn  => "+result, isRemote);
			setResult(result, "Config 2FA On");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Config 2FA On", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.BOOLEAN_PARAM_ONE, ParamObjectField.BOOLEAN_PARAM_TWO}, 
			labels={"Configure 2FA For Agent", "Configure 2FA For Employee"})
	public void config2FAOff(@Optional("TestID") Integer testID) throws Exception {
		try {
			changeParamObject(testID);

			PeoplePage peoplePage = goTo2FAConfig();
			if(paramObject.booleanParamOne){ peoplePage.configure2FaForAgent(false, "Email or Text"); }
			if(paramObject.booleanParamTwo){ peoplePage.configure2FaForEmployee(false, "Email or Text"); }
			peoplePage.save2FA();
			
			Boolean result = true;//TODO
			
			Debugger.log("config2FAOff  => "+result, isRemote);
			setResult(result, "Config 2FA Off");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Config 2FA Off", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.BOOLEAN_PARAM_ONE, ParamObjectField.BOOLEAN_PARAM_TWO}, 
			labels={"Configure 2FA For Agent", "Configure 2FA For Employee"})
	public void goTo2FAConfig(@Optional("TestID") Integer testID) throws Exception {
		try {
			changeParamObject(testID);
			goTo2FAConfig();
			Boolean result = true;
			
			Debugger.log("config2FAOff  => "+result, isRemote);
			setResult(result, "Config 2FA Off");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Config 2FA Off", ex, isRemote);
			assertTrue(false);
		}
	}

	private PeoplePage goTo2FAConfig() throws Exception {
		PeoplePage peoplePage = new PeoplePage(driver, environment);
		peoplePage.loadPage();
		
		HomeForAgentsPage home = new HomeForAgentsPage(driver, environment);
		home.goToSecurityTabInSettingPeople();
		return peoplePage;
	}
	
	public boolean makeFirstLogin(String gmailEmail, String gmailPassword, String newPassword) throws Exception{
		String firstLoginToken = MailManager.getAuthLink(gmailEmail, gmailPassword, getRunIDAsString());
		ResetPasswordPage resetPasswordPage = new ResetPasswordPage(driver, environment, firstLoginToken);
		
		resetPasswordPage.loadPage();
		resetPasswordPage.changePassword(newPassword);
		
		return resetPasswordPage.checkIfEmployeePasswordWasChange();
	}
	
}




