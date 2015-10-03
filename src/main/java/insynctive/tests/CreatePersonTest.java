package insynctive.tests;

import static org.junit.Assert.assertTrue;

import org.hibernate.Transaction;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import insynctive.model.CreatePersonForm;
import insynctive.pages.insynctive.LoginPage;
import insynctive.pages.insynctive.hr.HomeForAgentsPage;
import insynctive.utils.Debugger;
import insynctive.utils.data.TestEnvironment;

public class CreatePersonTest extends TestMachine {

	@Override
	@BeforeClass
	public void tearUp() throws Exception {
		super.tearUp();
		this.sessionName = "Create Person Test";
	}
	
	@Parameters({"personID"})	
	@Test
	public void loginTest(@Optional("personID") Integer personID) throws Exception {
		testEnvironment = TestEnvironment.FIREFOX;
		account.getAccountProperty().setRemote(true);
		if(personID.equals("personID")){
			throw new Exception("No email added");
		}
		
		//Search for The Person Data
		Transaction transaction = openSession().beginTransaction();
		CreatePersonForm createPersonForm = (CreatePersonForm) openSession().get(CreatePersonForm.class, personID);
		transaction.commit();
		
		account.getAccountProperty().setEnvironment(createPersonForm.getEnvironment());
		person.setEmail(createPersonForm.getEmail());
		person.setName(createPersonForm.getName());
		person.setLastName(createPersonForm.getLastName());
		
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

	@Parameters({"email"})
	@Test
	public void createPersonTest(@Optional("person_id") String personID) throws Throwable {
		long startTime = System.nanoTime();
		try{ 
			HomeForAgentsPage homePage = new HomeForAgentsPage(driver, properties.getEnvironment());
			
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
		
//	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="createPersonTest")
//	public void firstLogin(TestEnvironment testEnvironment)
//			throws Exception {
//		
//		String firstLoginToken = MailManager.getAuthLink(properties.getNewEmployeeEmail(), properties.getGmailPassword(), "Invitation to signup to Alpha 6 HR Portal");
//		ResetPasswordPage resetPasswordPage = new ResetPasswordPage(driver, properties.getEnviroment(), firstLoginToken);
//		
//		resetPasswordPage.loadPage();
//		resetPasswordPage.changePassword(properties.getNewEmployeePassword());
//		
//		assertTrue(resetPasswordPage.checkIfEmployeePasswordWasChange());
//	}
}
