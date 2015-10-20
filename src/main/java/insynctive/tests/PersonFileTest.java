package insynctive.tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import org.json.JSONException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import insynctive.exception.ConfigurationException;
import insynctive.model.EmergencyContact;
import insynctive.pages.insynctive.LoginPage;
import insynctive.pages.insynctive.PersonFilePage;
import insynctive.pages.insynctive.agent.hr.HomeForAgentsPage;
import insynctive.utils.CheckInApp;
import insynctive.utils.Debugger;
import insynctive.utils.Sleeper;
import insynctive.utils.Wait;
import insynctive.utils.data.TestEnvironment;
 
public class PersonFileTest extends TestMachine {

	@BeforeClass
	@Parameters({"accountID", "bowser"})
	public void tearUp(String accountID, String bowser) throws Exception {
		super.tearUp(Integer.valueOf(accountID));
		testEnvironment = TestEnvironment.valueOf(bowser);
		this.sessionName = "Person File Test ("+ person.getEmail()+")";
	}
	
	@Override
	@AfterClass(alwaysRun = true)
	public void teardown() throws ConfigurationException, MalformedURLException, IOException, JSONException {
		super.teardown();
	}
	
	@Test()
	public void loginTest()
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
	
////	//OPEN PERSON FILE NOT CREATE
//	@Test(dependsOnMethods="loginTest")
//	public void createPersonTest() throws Exception {
//		long startTime = System.nanoTime();
//		try{ 
//			HomeForAgentsPage homePage = new HomeForAgentsPage(driver, properties.getEnvironment());
//			homePage.openPersonFile(person.getSearchEmail()+"+71");
//
//			boolean result = homePage.isPersonFileOpened();
//			Sleeper.sleep(5000driver);
//			long endTime = System.nanoTime();
//			setResult(result, "Open Person File", endTime - startTime);
//			Debugger.log("createPersonTest => "+result, isSaucelabs);
//			assertTrue(result);
//		} catch(Exception ex){
//			long endTime = System.nanoTime();
//			failTest("Open Person File", ex, isSaucelabs, endTime - startTime);
//			assertTrue(false);
//		}
//	}
	
	@Test(dependsOnMethods="loginTest")
	public void createPersonTest() throws Throwable {
		long startTime = System.nanoTime();
		try{ 
			HomeForAgentsPage homePage = new HomeForAgentsPage(driver, properties.getEnvironment());
			
			person.setName(person.getName() + " " + account.getRunIDString());
			person.setEmail(person.getEmailWithRunID(account));
			homePage.createPersonCheckingInviteSS(person, CheckInApp.NO);
			homePage.sendInviteEmail(person, CheckInApp.NO);
			
			boolean result = homePage.checkIfPersonIsCreated(person);
			
			long endTime = System.nanoTime();
			setResult(result, "Create Person", endTime - startTime);
			Debugger.log("createPerson => "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			long endTime = System.nanoTime();
			failTest("Create Person", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}

	@Test(dependsOnMethods="createPersonTest")
	public void changePrimaryEmail() throws Exception {
		long startTime = System.nanoTime();  
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			personFilePage.changePrimaryEmail(person);
			
			boolean result = personFilePage.isChangePrimaryEmail(person.getEmailToChange());
			Debugger.log("changePrimaryEmail => "+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Change Primary Email", endTime - startTime);
			assertTrue(result);
		} catch(Exception ex){
			long endTime = System.nanoTime();
			failTest("Change Primary Email", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="createPersonTest")
	public void changeMaritalStatus()
			throws Exception {
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			
			personFilePage.changeMaritalStatus(person.getMaritalStatus());
			
			boolean result = personFilePage.isChangeMaritalStatus(person.getMaritalStatus());
			Debugger.log("changeMaritalStatus => "+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Change Marital Status", endTime - startTime);
			assertTrue(result);
		} catch (Exception ex){
			long endTime = System.nanoTime();
			failTest("Change Marital Status", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="createPersonTest")
	public void changeName()
			throws Exception {
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			
			personFilePage.changeName(person.getName(), person.getLastName(), person.getMiddleName(), person.getMaidenName());
			
			boolean result = personFilePage.isChangeName(person, Wait.WAIT);
			Debugger.log("changeName => "+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Change name 1", endTime - startTime);
			assertTrue(result);
		} catch (Exception ex){
			long endTime = System.nanoTime();
			failTest("Change name 1", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="createPersonTest")
	public void changeName2()
			throws Exception {
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			
			personFilePage.changeNameIntoTitle(person.getName(), person.getLastName(), person.getMiddleName(), person.getMaidenName());
			
			boolean result = personFilePage.isChangeName(person, Wait.WAIT);
			Debugger.log("changeName2 =>"+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Change name from Title", endTime - startTime);
			assertTrue(result);
		} catch(Exception ex){
			long endTime = System.nanoTime();
			failTest("Change name 2", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="createPersonTest")
	public void changeGender()
			throws Exception {
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			
			personFilePage.changeGender(person.getGender());
			
			boolean result = personFilePage.isChangeGender(person.getGender());
			Debugger.log("changeGender => "+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Change Gender", endTime - startTime);
			assertTrue(result);
		} catch (Exception ex){
			long endTime = System.nanoTime();
			failTest("Change Gender", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="createPersonTest")
	public void changeBirthDate()
			throws Exception {
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			
			personFilePage.changeBirthDate(person.getBirthDate());
			
			boolean result = personFilePage.isChangeBirthDate(person.getBirthDate());
			Debugger.log("changeBirthDate => "+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Change Birth Date", endTime - startTime);
			assertTrue(result);
		} catch(Exception ex){
			long endTime = System.nanoTime();
			failTest("Change Birth Date", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="createPersonTest")
	public void addTitle() throws Exception{
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			
			personFilePage.changeTitle(person.getTitleOfEmployee(), person.getDepartamentOfEmployee());
			
			boolean result = personFilePage.isChangeTitle(person.getTitleOfEmployee(), person.getDepartamentOfEmployee());
			Debugger.log("addTitle => "+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Add Title", endTime - startTime);
			assertTrue(result);
		}catch (Exception ex){ 
			long endTime = System.nanoTime();
			failTest("Add Title", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}

	/**
	 * @Test(dependsOnMethods="createPersonTest")
	public void hasNotDependents() throws IOException, InterruptedException, ConfigurationException{
		setResult(false, "Add Has Not Dependents");
		PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
		
		personFilePage.addHasNotDependents();
		
		boolean result = personFilePage.isNotDependents();
		Debugger.log("hasNotDependents => "+result, isSaucelabs);
		long endTime = System.nanoTime();setResult(result, "Add Has Not Dependents");
		assertTrue(result);
	} */

	@Test(dependsOnMethods="createPersonTest")
	public void addPhoneNumber() throws Exception{
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			
			personFilePage.addPhoneNumber(person.getPrimaryPhone(), account.getRunIDString());

			boolean result = personFilePage.isAddPhoneNumber(person.getPrimaryPhone(), account.getRunIDString());
			Debugger.log("addPhoneNumber =>"+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Add Phone Number", endTime - startTime);
			assertTrue(result);
		}catch (Exception ex){
			long endTime = System.nanoTime();
			failTest("Add Phone Number", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}

	@Test(dependsOnMethods="addPhoneNumber")
	public void addAlternativePhone() throws Exception{
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			personFilePage.addSecondaryEmail(person.getPrimaryPhone(), account.getRunIDString());
			
			boolean result = personFilePage.isAddAlternativePhoneNumber(person.getPrimaryPhone(), account.getRunIDString());
			Debugger.log("addAlternativePhone =>"+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Add Alternative Number", endTime - startTime);
			assertTrue(result);
		}catch (Exception ex){
			long endTime = System.nanoTime();
			failTest("Add Alternative Number", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}

	@Test(dependsOnMethods="addAlternativePhone")
	public void makePrimaryPhone() throws Exception{
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			Map<String, String> resultsPhones = personFilePage.makeAsPrimary();
			
			boolean result = personFilePage.isChangePrimaryPhone(resultsPhones);
			Debugger.log("makePrimaryPhone =>"+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Make as Primary Phone", endTime - startTime);
			assertTrue(result);
		}catch (Exception ex){
			long endTime = System.nanoTime();
			failTest("makePrimaryPhone", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="createPersonTest")
	public void addUSAddress() throws Exception{
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			
			personFilePage.addUsAddress(person.getUSAddress());
			
			boolean result = personFilePage.isAddUSAddress(person.getUSAddress());
			Debugger.log("addUSAddress => "+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Add US Address", endTime - startTime);
			assertTrue(result);
		}catch (Exception ex){ 
			long endTime = System.nanoTime();
			failTest("Add US Address", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="addUSAddress")
	public void removeUSAddress() throws Exception{
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			personFilePage.removeUsAddress(person.getUSAddress());

			boolean result = personFilePage.isRemoveUsAddress(person.getUSAddress());
			Debugger.log("removeUSAddress => "+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Remove US Address", endTime - startTime);
			assertTrue(result);
		}catch (Exception ex){ 
			long endTime = System.nanoTime();
			failTest("Remove US Address", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	/** @Test(dependsOnMethods="createPersonTest")
	public void updateUSAddress() throws Exception{
		try{ long startTime = System.nanoTime();
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			personFilePage.updateUsAddress(person.getUSAddress());

			boolean result = personFilePage.isUpdateUsAddress(person.getUSAddress());
			Debugger.log("removeUSAddress => "+result, isSaucelabs);
			long endTime = System.nanoTime();setResult(result, "Update USAddress");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Update USAddress", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	} */
	
	@Test(dependsOnMethods="createPersonTest")
	public void assignTask() throws Exception{
		long startTime = System.nanoTime();
		PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
		try{ 
			personFilePage.assignTask();
			
			boolean result = personFilePage.isTaskAssigned();
			Debugger.log("asssignTask => "+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Assign Task", endTime - startTime);
			assertTrue(result);
		}catch (Exception ex){ 
			long endTime = System.nanoTime();
			failTest("Assign Task", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="createPersonTest")
	public void startChecklist() throws Exception{
		long startTime = System.nanoTime();
		PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
		try{ 
			personFilePage.assignChecklist();
			Sleeper.sleep(5000, driver);
			boolean result = personFilePage.isChecklistAssigned();
			Debugger.log("startChecklist => "+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Start Checklist", endTime - startTime);
			assertTrue(result);
		}catch (Exception ex){ 
			personFilePage.goToPersonalTab();
			long endTime = System.nanoTime();
			failTest("Start Checklist", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="createPersonTest")
	public void addSocialSecurityNumber() throws Exception{
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			
			personFilePage.addSocialSecurityNumber(person.getSsn(), account.getRunIDString());
			
			boolean result = personFilePage.isSocialSecurityNumberAdded(person.getSsn(), account.getRunIDString());
			Debugger.log("Add Social Security Number => "+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Add Social Security Number", endTime - startTime);
			assertTrue(result);
		}catch (Exception ex){ 
			long endTime = System.nanoTime();
			failTest("Add Social Security Number", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="createPersonTest")
	public void addEmergencyContact() throws Exception{
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			EmergencyContact emg = person.getEmergencyContact();
			personFilePage.addEmergencyContact(emg.getName(), emg.getRelationship(), emg.getPhone(), emg.getEmail());
			
			boolean result = personFilePage.isEmergencyContactAdded(emg);
			Debugger.log("Add Emergency Contact => "+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Add Emergency Contact", endTime - startTime);
			assertTrue(result);
		}catch (Exception ex){ 
			long endTime = System.nanoTime();
			failTest("Add Emergency Contact", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="addEmergencyContact")
	public void changeEmergencyContact() throws Exception{
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			EmergencyContact emg = person.getEmergencyContact();
			personFilePage.editEmergencyContact(emg.getName()+"Test", emg.getRelationship(), emg.getPhone(), emg.getEmail());
			
			String name = emg.getName();
			emg.setName(emg.getName()+"Test");
			
			boolean result = personFilePage.isEmergencyContactAdded(emg);
			emg.setName(name); //return the default name
			
			Debugger.log("Change Emergency Contact => "+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Change Emergency Contact", endTime - startTime);
			assertTrue(result);
		}catch (Exception ex){ 
			long endTime = System.nanoTime();
			failTest("Change Emergency Contact", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="changeEmergencyContact")
	public void removeEmergencyContact() throws Exception{
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			int count = personFilePage.getNumberOfEmergencyContacts();
			personFilePage.removeLastEmergencyContact();
		
			boolean result = personFilePage.isEmergencyContactRemoved(count);
			Debugger.log("Remove Emergency Contact => "+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Add Emergency Contact", endTime - startTime);
			assertTrue(result);
		}catch (Exception ex){ 
			long endTime = System.nanoTime();
			failTest("Remove Emergency Contact", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="createPersonTest")
	public void assignJob() throws Exception{
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());
			personFilePage.assignJob();
			
			Boolean result = personFilePage.isJobAdded();
			
			Debugger.log("assignJob => "+result, isSaucelabs);
			setResult(result, "Assign Job");
			assertTrue(result);
		}catch (Exception ex){ 
			long endTime = System.nanoTime();
			failTest("Assign Job", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
}




