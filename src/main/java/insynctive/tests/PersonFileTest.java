package insynctive.tests;

import static org.junit.Assert.assertTrue;

import java.util.Map;
import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
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
import insynctive.pages.insynctive.agent.hr.HomeForAgentsPage;
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
	@Parameters({"accountID", "runID", "bowser", "testID", "testName"})
	public void tearUp(String accountID, String runID, String bowser, String testSuiteID, String testName) throws Exception {
		super.testSuiteID = Integer.parseInt(testSuiteID);
		super.tearUp(Integer.valueOf(accountID), Integer.valueOf(runID));
		testEnvironment = TestEnvironment.valueOf(bowser);
		this.sessionName = testName;
	}
	
	@BeforeTest
	@Parameters({"TestID"})
	public void beforeTest(@Optional("TestID") String testID){
		System.out.println("TESTID: "+testID);
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(attrs={ParamObjectField.LOGIN_USERNAME, ParamObjectField.LOGIN_PASSWORD}, 
	labels={"Login Username", "Login Password"})
	public void loginTest(@Optional("TestID") Integer testID)
			throws Exception {
		changeParamObject(testID);
		
		startTest(testEnvironment);
		try{ 
			LoginPage loginPage = login();
			Sleeper.sleep(1500, driver);
			boolean result = loginPage.isLoggedIn();
			Debugger.log("loginTest => "+result, isSaucelabs);
			setResult(result, "Login Test");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Login Test", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(attrs={ParamObjectField.LOGIN_USERNAME, ParamObjectField.LOGIN_PASSWORD}, 
	labels={"Login Username", "Login Password"})
	public void loginAsEmployeeTest(@Optional("TestID") Integer testID)
			throws Exception {
		changeParamObject(testID);
		
		startTest(testEnvironment);
		try{ 
			LoginPage loginPage = loginAsEmployee(paramObject.loginUsername, paramObject.loginPassword);
			boolean result = loginPage.isLoggedIn();
			Debugger.log("loginTest => "+result, isSaucelabs);
			setResult(result, "Login Test");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Login Test", ex, isSaucelabs);
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
			LoginPage loginPage = loginAsEmployee(paramObject.getEmailToChange(account.getRunIDString()), paramObject.loginPassword);
			boolean result = loginPage.isLoggedIn();
			Debugger.log("loginTest => "+result, isSaucelabs);
			setResult(result, "Login Test");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Login Test", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.BOOLEAN_PARAM ,ParamObjectField.EMAIL, ParamObjectField.NAME, 
					ParamObjectField.LAST_NAME, ParamObjectField.DEPARTMENT_OF_EMPLYEE, 
					ParamObjectField.TITLE_OF_EMPLOYEE}, 
			labels={"Create OR Open Person?", "Email (+RunID is automatically added)", "Name", "Last Name", "Department", "Title"})
	public void createPersonTest(@Optional("TestID") Integer testID) throws Throwable {
		changeParamObject(testID);
		try{ 
			HomeForAgentsPage homePage = new HomeForAgentsPage(driver, properties.getEnvironment());
			boolean result;
			if(paramObject.getBooleanParam()){//True = Open Person file > False = Create Person.
				homePage.openPersonFile(paramObject.getSearchEmail());
				result = homePage.isPersonFileOpened();
				Sleeper.sleep(5000, driver);
			} else {
				paramObject.setName(paramObject.getName() + " " + account.getRunIDString());
				paramObject.setEmail(paramObject.getEmailWithRunID(account.getRunIDString()));
				homePage.createPersonCheckingInviteSS(paramObject, CheckInApp.NO);
				homePage.sendInviteEmail(paramObject, CheckInApp.NO);
				result = homePage.checkIfPersonIsCreated(paramObject);
			}
			
			setResult(result, "Create Person");
			Debugger.log("createPerson => "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Create Person", ex, isSaucelabs);
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
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			personFilePage.changePrimaryEmail(paramObject.getEmailToChange(account.getRunIDString()));
			
			boolean result = personFilePage.isChangePrimaryEmail(paramObject.getEmailToChange(account.getRunIDString()));
			Debugger.log("changePrimaryEmail => "+result, isSaucelabs);
			setResult(result, "Change Primary Email");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Change Primary Email", ex, isSaucelabs);
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
			EmployeeDashboardPage employeePage = new EmployeeDashboardPage(driver, properties.getEnvironment());
			employeePage.changeEmail(paramObject.getEmailToChange(account.getRunIDString()));
			
			boolean result = true;
			Debugger.log("Change Primary Email From Employee"+result, isSaucelabs);
			setResult(result, "changePrimaryEmailFromEmployee");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Change Primary Email From Employee", ex, isSaucelabs);
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
			EmployeeDashboardPage employeePage = new EmployeeDashboardPage(driver, properties.getEnvironment());
			employeePage.addAlternateiveEmail(paramObject.getEmailToChange(account.getRunIDString()));
			
			boolean result = true;
			Debugger.log("Change Primary Email From Employee"+result, isSaucelabs);
			setResult(result, "changePrimaryEmailFromEmployee");
			assertTrue(result);
		} catch (Exception ex){ 
			failTest("Change Primary Email From Employee", ex, isSaucelabs);
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
			EmployeeDashboardPage employeePage = new EmployeeDashboardPage(driver, properties.getEnvironment());
			employeePage.makePrimaryEmail(paramObject.getEmailToChange(account.getRunIDString()));
			
			boolean result = true;
			Debugger.log("Change Primary Email From Employee"+result, isSaucelabs);
			setResult(result, "changePrimaryEmailFromEmployee");
			assertTrue(result);
		} catch (Exception ex){ 
			failTest("Change Primary Email From Employee", ex, isSaucelabs);
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
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			
			personFilePage.changeMaritalStatus(paramObject.getMaritalStatus());
			
			boolean result = personFilePage.isChangeMaritalStatus(paramObject.getMaritalStatus());
			Debugger.log("changeMaritalStatus => "+result, isSaucelabs);
			setResult(result, "Change Marital Status");
			assertTrue(result);
		} catch (Exception ex){
			failTest("Change Marital Status", ex, isSaucelabs);
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
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			
			personFilePage.changeName(paramObject.getName(), paramObject.getLastName(), paramObject.getMiddleName(), paramObject.getMaidenName());
			
			boolean result = personFilePage.isChangeNameInPersonalDetails(paramObject, Wait.WAIT);
			Debugger.log("changeName => "+result, isSaucelabs);
			setResult(result, "Change name in Personal Info");
			assertTrue(result);
		} catch (Exception ex){
			failTest("Change name in Personal Info", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.NAME, ParamObjectField.LAST_NAME, ParamObjectField.MIDDLE_NAME, ParamObjectField.MAIDEN_NAME}, 
			labels={"Name:", "Last Name", "Middle Name", "Maiden Name"})
	public void ChangeNameInTitle(@Optional("TestID") Integer testID)
			throws Exception {
		changeParamObject(testID);
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			
			personFilePage.changeNameIntoTitle(paramObject.getName(), paramObject.getLastName(), paramObject.getMiddleName(), paramObject.getMaidenName());
			
			boolean result = personFilePage.isChangeNameInTitle(paramObject, Wait.WAIT);
			Debugger.log("changeName2 =>"+result, isSaucelabs);
			setResult(result, "Change name from Title");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Change name 2", ex, isSaucelabs);
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
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			
			personFilePage.changeGender(paramObject.getGender());
			
			boolean result = personFilePage.isChangeGender(paramObject.getGender());
			Debugger.log("changeGender => "+result, isSaucelabs);
			setResult(result, "Change Gender");
			assertTrue(result);
		} catch (Exception ex){
			failTest("Change Gender", ex, isSaucelabs);
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
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			
			personFilePage.changeBirthDate(paramObject.getBirthDate());
			
			boolean result = personFilePage.isChangeBirthDate(paramObject.getBirthDate());
			Debugger.log("changeBirthDate => "+result, isSaucelabs);
			setResult(result, "Change Birth Date");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Change Birth Date", ex, isSaucelabs);
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
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			
			personFilePage.changeTitle(paramObject.getTitleOfEmployee(), paramObject.getDepartamentOfEmployee());
			
			boolean result = personFilePage.isChangeTitle(paramObject.getTitleOfEmployee(), paramObject.getDepartamentOfEmployee());
			Debugger.log("addTitle => "+result, isSaucelabs);
			setResult(result, "Add Title");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Add Title", ex, isSaucelabs);
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
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			
			personFilePage.addPhoneNumber(paramObject.getPrimaryPhone(), account.getRunIDString());

			boolean result = personFilePage.isAddPhoneNumber(paramObject.getPrimaryPhone(), account.getRunIDString());
			Debugger.log("addPhoneNumber =>"+result, isSaucelabs);
			setResult(result, "Add Phone Number");
			assertTrue(result);
		}catch (Exception ex){
			failTest("Add Phone Number", ex, isSaucelabs);
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
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			personFilePage.addSecondaryEmail(paramObject.getPrimaryPhone(), account.getRunIDString());
			
			boolean result = personFilePage.isAddAlternativePhoneNumber(paramObject.getPrimaryPhone(), account.getRunIDString());
			Debugger.log("addAlternativePhone =>"+result, isSaucelabs);
			setResult(result, "Add Alternative Number");
			assertTrue(result);
		}catch (Exception ex){
			failTest("Add Alternative Number", ex, isSaucelabs);
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
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			Map<String, String> resultsPhones = personFilePage.makeAsPrimary();
			
			boolean result = personFilePage.isChangePrimaryPhone(resultsPhones);
			Debugger.log("makePrimaryPhone =>"+result, isSaucelabs);
			setResult(result, "Make as Primary Phone");
			assertTrue(result);
		}catch (Exception ex){
			failTest("makePrimaryPhone", ex, isSaucelabs);
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
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			this.usaddress = paramObject.getUSAddress();
			personFilePage.addUsAddress(paramObject.getUSAddress());
			
			boolean result = personFilePage.isAddUSAddress(paramObject.getUSAddress());
			Debugger.log("addUSAddress => "+result, isSaucelabs);
			setResult(result, "Add US Address");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Add US Address", ex, isSaucelabs);
			assertTrue(false);
		}
	}

	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={}, 
			labels={"Remove the US Address that is in the person File."})
	public void removeUSAddress(@Optional("TestID") Integer testID) throws Exception{
		setparamObjectAsAccount(testID);
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			personFilePage.removeUsAddress(usaddress);

			boolean result = personFilePage.isRemoveUsAddress(paramObject.getUSAddress());
			Debugger.log("removeUSAddress => "+result, isSaucelabs);
			setResult(result, "Remove US Address");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Remove US Address", ex, isSaucelabs);
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
		PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
		try{ 
			personFilePage.assignTask();
			
			boolean result = personFilePage.isTaskAssigned();
			Debugger.log("asssignTask => "+result, isSaucelabs);
			setResult(result, "Assign Task");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Assign Task", ex, isSaucelabs);
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
		PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
		try{ 
			personFilePage.assignChecklist(paramObject.getChecklistName());
			Sleeper.sleep(5000, driver);
			boolean result = personFilePage.isChecklistAssigned(paramObject.getChecklistName());
			Debugger.log("startChecklist => "+result, isSaucelabs);
			setResult(result, "Start Checklist");
			assertTrue(result);
		}catch (Exception ex){ 
			personFilePage.goToPersonalTab();
			failTest("Start Checklist", ex, isSaucelabs);
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
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			
			personFilePage.addSocialSecurityNumber(paramObject.getSsn(), account.getRunIDString());
			
			boolean result = personFilePage.isSocialSecurityNumberAdded(paramObject.getSsn(), account.getRunIDString());
			Debugger.log("Add Social Security Number => "+result, isSaucelabs);
			setResult(result, "Add Social Security Number");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Add Social Security Number", ex, isSaucelabs);
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
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			EmergencyContact emg = paramObject.getEmergencyContact();
			personFilePage.addEmergencyContact(emg.getName(), emg.getRelationship(), emg.getPhone(), emg.getEmail());
			
			boolean result = personFilePage.isEmergencyContactAdded(emg);
			Debugger.log("Add Emergency Contact => "+result, isSaucelabs);
			setResult(result, "Add Emergency Contact");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Add Emergency Contact", ex, isSaucelabs);
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
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			EmergencyContact emg = paramObject.getEmergencyContact();
			personFilePage.editEmergencyContact(emg.getName()+"Test", emg.getRelationship(), emg.getPhone(), emg.getEmail());
			
			String name = emg.getName();
			emg.setName(emg.getName()+"Test");
			
			boolean result = personFilePage.isEmergencyContactAdded(emg);
			emg.setName(name); //return the default name
			
			Debugger.log("Change Emergency Contact => "+result, isSaucelabs);
			setResult(result, "Change Emergency Contact");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Change Emergency Contact", ex, isSaucelabs);
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
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			int count = personFilePage.getNumberOfEmergencyContacts();
			personFilePage.removeLastEmergencyContact();
		
			boolean result = personFilePage.isEmergencyContactRemoved(count);
			Debugger.log("Remove Emergency Contact => "+result, isSaucelabs);
			setResult(result, "Add Emergency Contact");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Remove Emergency Contact", ex, isSaucelabs);
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
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			personFilePage.assignJob();
			
			Boolean result = personFilePage.isJobAdded();
			
			Debugger.log("assignJob => "+result, isSaucelabs);
			setResult(result, "Assign Job");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Assign Job", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={}, 
			labels={"No parameters needed"})
	public void logOut(@Optional("TestID") Integer testID) throws Exception{
		try{ 
			Page page = new Page(driver);
			page.logout();
			Sleeper.sleep(2000, driver);
		}catch (Exception ex){ 
			failTest("log out fail", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.EMAIL, ParamObjectField.LOGIN_PASSWORD}, 
			labels={"Gmail Email", "New Password"})
	public void firstLogin(@Optional("TestID") Integer testID)
			throws Exception {
		changeParamObject(testID);
		try {
			makeFirstLogin(paramObject.email, properties.getGmailPassword(), paramObject.loginPassword);//Re utilize EMAIL param because is a String
			boolean result = true;
			Debugger.log("First Login  => "+result, isSaucelabs);
			setResult(result, "Change Emergency Contact");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("First Login ", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	public boolean makeFirstLogin(String gmailEmail, String gmailPassword, String newPassword) throws Exception{
		String firstLoginToken = MailManager.getAuthLink(gmailEmail, gmailPassword, account.getRunIDString());
		ResetPasswordPage resetPasswordPage = new ResetPasswordPage(driver, properties.getEnvironment(), firstLoginToken);
		
		resetPasswordPage.loadPage();
		resetPasswordPage.changePassword(newPassword);
		
		return resetPasswordPage.checkIfEmployeePasswordWasChange();
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.EMAIL}, 
			labels={"Base Email (After change email will add '+runID+test' and Before change email will add '+runID')"})
	public void checkIfChangeEmailIsSending(@Optional("TestID") Integer testID)
			throws Exception {
		changeParamObject(testID);
		try {
			boolean result = checkIfChangeEmailIsSending(paramObject.getEmailToChange(account.getRunIDString()), properties.getGmailPassword(), paramObject.getEmailWithRunID(account.getRunIDString()));//Re utilize lastname param because is a String
			Debugger.log("checkIfChangeEmailIsSending  => "+result, isSaucelabs);
			setResult(result, "Check If Change Email is Sending");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Check If Change Email is Sending", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	public boolean checkIfChangeEmailIsSending(String gmailEmail, String gmailPassword, String beforeEmail) throws Exception{
		try {
			return MailManager.checkIfChangeEmailIsSending(gmailEmail, gmailPassword, beforeEmail);
		} catch(Exception ex) {
			return false;
		}
	}
}




