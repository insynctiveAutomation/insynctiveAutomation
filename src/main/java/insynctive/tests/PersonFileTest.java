package insynctive.tests;

import static org.junit.Assert.assertTrue;
import insynctive.pages.insynctive.HomeForAgentsPage;
import insynctive.pages.insynctive.LoginPage;
import insynctive.pages.insynctive.PersonFilePage;
import insynctive.utils.Debugger;
import insynctive.utils.EmergencyContact;
import insynctive.utils.PersonData;
import insynctive.utils.Wait;
import insynctive.utils.data.TestEnvironment;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
 

public class PersonFileTest extends TestMachine {

	PersonData person;
	
	@Override
	@BeforeClass
	public void tearUp() throws Exception {
		super.tearUp();
		properties.getRunIDAndAutoIncrement();
		person = new PersonData(properties.getRunID());
		this.sessionName = "Person File Test ("+ person.getEmail()+")";
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

		try{
			LoginPage loginPage = login();
			boolean result = loginPage.isLoggedIn();
			setResult(result, "Login Test");
			Debugger.log("loginTest => "+result, isSaucelabs);
			assertTrue(result);
		} catch(Exception ex){
			failTest("Login",  ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
//	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="loginTest")
//	public void openPersonFile(TestEnvironment testEnvironment) throws Exception {
//		try{
//			HomeForAgentsPage homePage = new HomeForAgentsPage(driver, properties.getEnviroment());
//			homePage.createPersonTest(person.getSearchEmail());
//			
//			boolean result = homePage.isPersonFileOpened();
//			Debugger.log("createPersonTest => "+result, isSaucelabs);
//			setResult(result, "Open Person File");
//			assertTrue(result);
//		} catch(Exception ex){
//			failTest("Open Person File", ex, isSaucelabs);
//			assertTrue(false);
//		}
//	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="loginTest")
	public void createPersonTest(TestEnvironment testEnvironment) throws Throwable {
		try{
			HomeForAgentsPage homePage = new HomeForAgentsPage(driver, properties.getEnviroment());
			
			person.setName(person.getName() + " " + properties.getRunID());
			homePage.createPersonCheckingInviteSS(person);
			homePage.sendInviteEmail(person);
			
			boolean result = homePage.checkIfPersonIsCreated(person);
			
			setResult(result, "Create Person");
			Debugger.log("createPerson => "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Create Person", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	

	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void changePrimaryEmail(TestEnvironment testEnvironment) throws Exception {
		try{
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			personFilePage.changePrimaryEmail(person);
			
			boolean result = personFilePage.isChangePrimaryEmail(person.getEmail());
			Debugger.log("changePrimaryEmail => "+result, isSaucelabs);
			setResult(result, "Change Primary Email");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Change Primary Email", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void changeMaritalStatus(TestEnvironment testEnvironment)
			throws Exception {
		try{
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			
			personFilePage.changeMaritalStatus(person.getMaritalStatus());
			
			boolean result = personFilePage.isChangeMaritalStatus(person.getMaritalStatus());
			Debugger.log("changeMaritalStatus => "+result, isSaucelabs);
			setResult(result, "Change Marital Status");
			assertTrue(result);
		} catch (Exception ex){
			failTest("Change Marital Status", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void changeName(TestEnvironment testEnvironment)
			throws Exception {
		try{
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			
			personFilePage.changeName(person.getName(), person.getLastName(), person.getMiddleName(), person.getMaidenName());
			
			boolean result = personFilePage.isChangeName(person, Wait.WAIT);
			Debugger.log("changeName => "+result, isSaucelabs);
			setResult(result, "Change name 1");
			assertTrue(result);
		} catch (Exception ex){
			failTest("C hange name 1", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void aaaachangeName2(TestEnvironment testEnvironment)
			throws Exception {
		try{
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			
			personFilePage.changeNameIntoTitle(person.getName(), person.getLastName(), person.getMiddleName(), person.getMaidenName());
			
			boolean result = personFilePage.isChangeName(person, Wait.WAIT);
			Debugger.log("changeName2 =>"+result, isSaucelabs);
			setResult(result, "Change name 2");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Change name 2", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void changeGender(TestEnvironment testEnvironment)
			throws Exception {
		try{
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			
			personFilePage.changeGender(person.getGender());
			
			boolean result = personFilePage.isChangeGender(person.getGender());
			Debugger.log("changeGender => "+result, isSaucelabs);
			setResult(result, "Change Gender");
			assertTrue(result);
		} catch (Exception ex){
			failTest("Change Gender", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void changeBirthDate(TestEnvironment testEnvironment)
			throws Exception {
		try{
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			
			personFilePage.changeBirthDate(person.getBirthDate());
			
			boolean result = personFilePage.isChangeBirthDate(person.getBirthDate());
			Debugger.log("changeBirthDate => "+result, isSaucelabs);
			setResult(result, "Change Birth Date");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Change Birth Date", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void addTitle(TestEnvironment testEnvironment) throws Exception{
		try{
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			
			personFilePage.changeTitle(person.getTitleOfEmployee(), person.getDepartamentOfEmployee());
			
			boolean result = personFilePage.isChangeTitle(person.getTitleOfEmployee(), person.getDepartamentOfEmployee());
			Debugger.log("addTitle => "+result, isSaucelabs);
			setResult(result, "Add Title");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Add Title", ex, isSaucelabs);
			assertTrue(false);
		}
	}

	/**
	 * @Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void hasNotDependents(TestEnvironment testEnvironment) throws IOException, InterruptedException, ConfigurationException{
		setResult(false, "Add Has Not Dependents");
		PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
		
		personFilePage.addHasNotDependents();
		
		boolean result = personFilePage.isNotDependents();
		Debugger.log("hasNotDependents => "+result, isSaucelabs);
		setResult(result, "Add Has Not Dependents");
		assertTrue(result);
	} */

	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void addPhoneNumber(TestEnvironment testEnvironment) throws Exception{
		try{
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			
			personFilePage.addPhoneNumber(person.getPrimaryPhone(), properties.getRunID());

			boolean result = personFilePage.isAddPhoneNumber(person.getPrimaryPhone(), properties.getRunID());
			Debugger.log("addPhoneNumber =>"+result, isSaucelabs);
			setResult(result, "Add Phone Number");
			assertTrue(result);
		}catch (Exception ex){
			failTest("Add Phone Number", ex, isSaucelabs);assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void addUSAddress(TestEnvironment testEnvironment) throws Exception{
		try{
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			
			personFilePage.addUsAddress(person.getUSAddress());
			
			boolean result = personFilePage.isAddUSAddress(person.getUSAddress());
			Debugger.log("addUSAddress => "+result, isSaucelabs);
			setResult(result, "Add US Address");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Add US Address", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="addUSAddress")
	public void removeUSAddress(TestEnvironment testEnvironment) throws Exception{
		try{
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			personFilePage.removeUsAddress(person.getUSAddress());

			boolean result = personFilePage.isRemoveUsAddress(person.getUSAddress());
			Debugger.log("removeUSAddress => "+result, isSaucelabs);
			setResult(result, "Remove US Address");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Remove US Address", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	/** @Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void updateUSAddress(TestEnvironment testEnvironment) throws Exception{
		try{
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			personFilePage.updateUsAddress(person.getUSAddress());

			boolean result = personFilePage.isUpdateUsAddress(person.getUSAddress());
			Debugger.log("removeUSAddress => "+result, isSaucelabs);
			setResult(result, "Update USAddress");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Update USAddress", ex, isSaucelabs);
			assertTrue(false);
		}
	} */
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void assignTask(TestEnvironment testEnvironment) throws Exception{
		try{
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			
			personFilePage.assignTask();
			
			boolean result = personFilePage.isTaskAssigned();
			Debugger.log("asssignTask => "+result, isSaucelabs);
			setResult(result, "Assign Task");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Assign Task", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void startChecklist(TestEnvironment testEnvironment) throws Exception{
		try{
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			
			personFilePage.assignChecklist();
			
			boolean result = personFilePage.isChecklistAssigned();
			Debugger.log("startChecklist => "+result, isSaucelabs);
			setResult(result, "Start Checklist");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Start Checklist", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void addSocialSecurityNumber(TestEnvironment testEnvironment) throws Exception{
		try{
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			
			personFilePage.addSocialSecurityNumber(person.getSsn(), properties.getRunID());
			
			boolean result = personFilePage.isSocialSecurityNumberAdded(person.getSsn(), properties.getRunID());
			Debugger.log("Add Social Security Number => "+result, isSaucelabs);
			setResult(result, "Add Social Security Number");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("Add Social Security Number", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void addEmergencyContact(TestEnvironment testEnvironment) throws Exception{
		try{
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			EmergencyContact emg = person.getEmergencyContact();
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
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="addEmergencyContact")
	public void changeEmergencyContact(TestEnvironment testEnvironment) throws Exception{
		try{
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			EmergencyContact emg = person.getEmergencyContact();
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
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="changeEmergencyContact")
	public void removeEmergencyContact(TestEnvironment testEnvironment) throws Exception{
		try{
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
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
	
	

}




