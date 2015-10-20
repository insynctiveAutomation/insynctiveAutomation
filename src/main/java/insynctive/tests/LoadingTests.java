package insynctive.tests;

import static org.junit.Assert.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import insynctive.pages.insynctive.LoginPage;
import insynctive.pages.insynctive.agent.apps.AppsPage;
import insynctive.pages.insynctive.agent.hr.CheckListsPage;
import insynctive.pages.insynctive.agent.hr.HelpDeskPage;
import insynctive.pages.insynctive.agent.hr.HomeForAgentsPage;
import insynctive.pages.insynctive.agent.hr.TaskPage;
import insynctive.pages.insynctive.agent.settings.AccountPage;
import insynctive.pages.insynctive.agent.settings.OtherSettingPage;
import insynctive.pages.insynctive.agent.settings.PeoplePage;
import insynctive.pages.insynctive.agent.settings.V3SettingsPage;
import insynctive.utils.Debugger;
import insynctive.utils.data.TestEnvironment;

public class LoadingTests extends TestMachine {

	int TIME_EXPECTED = 5000;
	
	@BeforeClass
	@Parameters({"accountID", "bowser"})
	public void tearUp(String accountID, String bowser) throws Exception {
		super.tearUp(Integer.valueOf(accountID));
		testEnvironment = TestEnvironment.valueOf(bowser);
		this.sessionName = "Loading Tests";
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
	
	@Test(dependsOnMethods="loginTest")
	public void hrPeopleLoadingTest() throws Throwable {
		try{ 
			HomeForAgentsPage homePage = new HomeForAgentsPage(driver, properties.getEnvironment());
			long timeToLoad = homePage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "HR > People > Loading Test (Duration: "+timeToLoad+" ms)");
			Debugger.log("hrPeopleLoadingTest "+timeToLoad+"=> "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("hrPeopleLoadingTest", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="loginTest")
	public void hrTasksLoadingTest() throws Throwable {
		try{ 
			TaskPage taskPage = new TaskPage(driver, properties.getEnvironment());
			long timeToLoad = taskPage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "HR > Tasks > Loading Test (Duration: "+timeToLoad+" ms)");
			Debugger.log("hrTasksLoadingTest "+timeToLoad+"=> "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("hrTasksLoadingTest", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="loginTest")
	public void hrChecklistLoadingTest() throws Throwable {
		try{ 
			CheckListsPage checkListPage = new CheckListsPage(driver, properties.getEnvironment());
			long timeToLoad = checkListPage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "HR > Checklist > Loading Test (Duration: "+timeToLoad+" ms)");
			Debugger.log("hrChecklistLoadingTest "+timeToLoad+"=> "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("hrChecklistLoadingTest", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="loginTest")
	public void hrHelpDeskLoadingTest() throws Throwable {
		try{ 
			HelpDeskPage helpDeskPage = new HelpDeskPage(driver, properties.getEnvironment());
			long timeToLoad = helpDeskPage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "HR > HelpDesk > Loading Test (Duration: "+timeToLoad+" ms)");
			Debugger.log("hrHelpDeskLoadingTest "+timeToLoad+"=> "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("hrHelpDeskLoadingTest", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="loginTest")
	public void apptLoadingTest() throws Throwable {
		try{ 
			AppsPage appPage = new AppsPage(driver, properties.getEnvironment());
			long timeToLoad = appPage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "APP > Loading Test (Duration: "+timeToLoad+" ms)");
			Debugger.log("apptLoadingTest "+timeToLoad+"=> "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("apptLoadingTest", ex, isSaucelabs);
			assertTrue(false);
		}
	}

	@Test(dependsOnMethods="loginTest")
	public void settingAccountLoadingTest() throws Throwable {
		try{ 
			AccountPage accPage = new AccountPage(driver, properties.getEnvironment());
			long timeToLoad = accPage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "Setting > Account > Loading Test (Duration: "+timeToLoad+" ms)");
			Debugger.log("settingAccountLoadingTest "+timeToLoad+"=> "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("settingAccountLoadingTest", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="loginTest")
	public void settingPeopleLoadingTest() throws Throwable {
		try{ 
			PeoplePage peoplePage = new PeoplePage(driver, properties.getEnvironment());
			long timeToLoad = peoplePage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "Setting > People > Loading Test (Duration: "+timeToLoad+" ms)");
			Debugger.log("settingPeopleLoadingTest "+timeToLoad+"=> "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("settingPeopleLoadingTest", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="loginTest")
	public void settingOtherSettingsLoadingTest() throws Throwable {
		try{ 
			OtherSettingPage otherSetting = new OtherSettingPage(driver, properties.getEnvironment());
			long timeToLoad = otherSetting.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "Setting > Other Settings > Loading Test (Duration: "+timeToLoad+" ms)");
			Debugger.log("settingOtherSettingsLoadingTest "+timeToLoad+"=> "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("settingOtherSettingsLoadingTest", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dependsOnMethods="loginTest")
	public void settingV3SettingsLoadingTest() throws Throwable {
		try{ 
			V3SettingsPage v3Setting = new V3SettingsPage(driver, properties.getEnvironment());
			long timeToLoad = v3Setting.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "Setting > V3 Settings > Loading Test (Duration: "+timeToLoad+" ms)"); 
			Debugger.log("settingV3SettingsLoadingTest "+timeToLoad+"=> "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("settingV3SettingsLoadingTest", ex, isSaucelabs);
			assertTrue(false);
		}
	}
}
