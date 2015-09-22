package insynctive.tests;

import static org.junit.Assert.assertTrue;
import insynctive.pages.insynctive.LoginPage;
import insynctive.pages.insynctive.PersonFilePage;
import insynctive.pages.insynctive.hr.HomeForAgentsPage;
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
		properties.IncrementRunID();
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
	
//	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="loginTest")
//	public void openPersonFile(TestEnvironment testEnvironment) throws Exception {
//		try{ long startTime = System.nanoTime();
//			HomeForAgentsPage homePage = new HomeForAgentsPage(driver, properties.getEnviroment());
//			homePage.createPersonTest(person.getSearchEmail());
//			
//			boolean result = homePage.isPersonFileOpened();
//			Debugger.log("createPersonTest => "+result, isSaucelabs);
//			long endTime = System.nanoTime();setResult(result, "Open Person File");
//			assertTrue(result);
//		} catch(Exception ex){
//			failTest("Open Person File", ex, isSaucelabs, endTime - startTime);
//			assertTrue(false);
//		}
//	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="loginTest")
	public void createPersonTest(TestEnvironment testEnvironment) throws Throwable {
		long startTime = System.nanoTime();
		try{ 
			HomeForAgentsPage homePage = new HomeForAgentsPage(driver, properties.getEnviroment());
			
			person.setName(person.getName() + " " + properties.getRunID());
			homePage.createPersonCheckingInviteSS(person);
			homePage.sendInviteEmail(person);
			
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
	

	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void changePrimaryEmail(TestEnvironment testEnvironment) throws Exception {
		long startTime = System.nanoTime();  
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
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
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void changeMaritalStatus(TestEnvironment testEnvironment)
			throws Exception {
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			
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
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void changeName(TestEnvironment testEnvironment)
			throws Exception {
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			
			personFilePage.changeName(person.getName(), person.getLastName(), person.getMiddleName(), person.getMaidenName());
			
			boolean result = personFilePage.isChangeName(person, Wait.WAIT);
			Debugger.log("changeName => "+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Change name 1", endTime - startTime);
			assertTrue(result);
		} catch (Exception ex){
			long endTime = System.nanoTime();
			failTest("C hange name 1", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void changeName2(TestEnvironment testEnvironment)
			throws Exception {
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			
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
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void changeGender(TestEnvironment testEnvironment)
			throws Exception {
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			
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
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void changeBirthDate(TestEnvironment testEnvironment)
			throws Exception {
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			
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
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void addTitle(TestEnvironment testEnvironment) throws Exception{
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			
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
	 * @Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void hasNotDependents(TestEnvironment testEnvironment) throws IOException, InterruptedException, ConfigurationException{
		setResult(false, "Add Has Not Dependents");
		PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
		
		personFilePage.addHasNotDependents();
		
		boolean result = personFilePage.isNotDependents();
		Debugger.log("hasNotDependents => "+result, isSaucelabs);
		long endTime = System.nanoTime();setResult(result, "Add Has Not Dependents");
		assertTrue(result);
	} */

	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void addPhoneNumber(TestEnvironment testEnvironment) throws Exception{
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			
			personFilePage.addPhoneNumber(person.getPrimaryPhone(), properties.getRunID());

			boolean result = personFilePage.isAddPhoneNumber(person.getPrimaryPhone(), properties.getRunID());
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
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void addUSAddress(TestEnvironment testEnvironment) throws Exception{
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			
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
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="addUSAddress")
	public void removeUSAddress(TestEnvironment testEnvironment) throws Exception{
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
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
	
	/** @Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void updateUSAddress(TestEnvironment testEnvironment) throws Exception{
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
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void assignTask(TestEnvironment testEnvironment) throws Exception{
		long startTime = System.nanoTime();
		PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
		try{ 
			personFilePage.assignTask();
			
			boolean result = personFilePage.isTaskAssigned();
			Debugger.log("asssignTask => "+result, isSaucelabs);
			long endTime = System.nanoTime();
			setResult(result, "Assign Task", endTime - startTime);
			assertTrue(result);
		}catch (Exception ex){ 
			personFilePage.goToPersonalTab();
			long endTime = System.nanoTime();
			failTest("Assign Task", ex, isSaucelabs, endTime - startTime);
			assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void startChecklist(TestEnvironment testEnvironment) throws Exception{
		long startTime = System.nanoTime();
		PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
		try{ 
			personFilePage.assignChecklist();
			
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
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void addSocialSecurityNumber(TestEnvironment testEnvironment) throws Exception{
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
			
			personFilePage.addSocialSecurityNumber(person.getSsn(), properties.getRunID());
			
			boolean result = personFilePage.isSocialSecurityNumberAdded(person.getSsn(), properties.getRunID());
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
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
	public void addEmergencyContact(TestEnvironment testEnvironment) throws Exception{
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
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
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="addEmergencyContact")
	public void changeEmergencyContact(TestEnvironment testEnvironment) throws Exception{
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
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
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="changeEmergencyContact")
	public void removeEmergencyContact(TestEnvironment testEnvironment) throws Exception{
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnviroment());
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
	
	

}




