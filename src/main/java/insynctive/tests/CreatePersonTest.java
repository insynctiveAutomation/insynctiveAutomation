package insynctive.tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import insynctive.exception.ConfigurationException;
import insynctive.model.CreatePersonForm;
import insynctive.pages.insynctive.LoginPage;
import insynctive.pages.insynctive.PersonFilePage;
import insynctive.pages.insynctive.agent.hr.HomeForAgentsPage;
import insynctive.utils.CheckInApp;
import insynctive.utils.Debugger;
import insynctive.utils.data.TestEnvironment;

public class CreatePersonTest extends TestMachine {

	CreatePersonForm createPersonForm;
	
	@Override
	@BeforeClass
	public void tearUp() throws Exception {
		super.tearUp(2);//This is the ID of the Account who user the http://usertestingstepone.herokuapp.com/ Application 
		this.sessionName = "Create Person Test";
	}

	@Override
	@AfterClass
	public void teardown() throws ConfigurationException, MalformedURLException, IOException, JSONException {
		super.teardown();
	}
	
	@Parameters({"personID"})	
	@Test
	public void loginTest(@Optional("personID") Integer personID) throws Exception {
		if(personID.equals("personID")){
			throw new Exception("No email added");
		}
		
		//Search for The Person Data
		Transaction transaction = openSession().beginTransaction();
		Session session = openSession();
		createPersonForm = (CreatePersonForm) session.get(CreatePersonForm.class, personID);
		session.flush();
		transaction.commit();
		
		//Complete Data
		testEnvironment = TestEnvironment.FIREFOX;
		account.getAccountProperty().setRemote(true);
		person.setEmail(createPersonForm.getEmail());
		person.setName(createPersonForm.getName());
		person.setLastName(createPersonForm.getLastName());
		sessionName = "Crete person: "+createPersonForm.getEmail();
		
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

	@Parameters({"personID"})
	@Test(dependsOnMethods="loginTest")
	public void createPersonTest(@Optional("person_id") String personID) throws Throwable {
		long startTime = System.nanoTime();
		try{ 
			HomeForAgentsPage homePage = new HomeForAgentsPage(driver, properties.getEnvironment());
			
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
	
	@Parameters({"personID"})
	@Test(dependsOnMethods="createPersonTest")
	public void assignJob(@Optional("person_id") String personID) throws Exception{
		long startTime = System.nanoTime();
		try{ 
			PersonFilePage personFilePage = new PersonFilePage(driver, properties.getEnvironment());

			personFilePage.assignJob();
			long endTime = System.nanoTime();
			setResult(true, "AssignJob", endTime - startTime);
			Debugger.log("assignJob => "+true, isSaucelabs);
			assertTrue(true);
		}catch (Exception ex){ 
			long endTime = System.nanoTime();
			failTest("Assign Job", ex, isSaucelabs, endTime - startTime);
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
