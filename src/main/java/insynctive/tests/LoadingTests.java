package insynctive.tests;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import insynctive.pages.insynctive.LoginPage;
import insynctive.pages.insynctive.apps.AppsPage;
import insynctive.pages.insynctive.hr.CheckListsPage;
import insynctive.pages.insynctive.hr.HelpDeskPage;
import insynctive.pages.insynctive.hr.HomeForAgentsPage;
import insynctive.pages.insynctive.hr.TaskPage;
import insynctive.pages.insynctive.settings.AccountPage;
import insynctive.pages.insynctive.settings.OtherSettingPage;
import insynctive.pages.insynctive.settings.PeoplePage;
import insynctive.pages.insynctive.settings.V3SettingsPage;
import insynctive.utils.Debugger;
import insynctive.utils.data.TestEnvironment;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoadingTests extends TestMachine {

	int TIME_EXPECTED = 5000;
	
	@Override
	@BeforeClass
	public void tearUp() throws Exception {
		super.tearUp();
		properties.IncrementRunID();
		this.sessionName = "Loading Tests";
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
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="loginTest")
	public void hrPeopleLoadingTest(TestEnvironment testEnvironment) throws Throwable {
		try{ 
			HomeForAgentsPage homePage = new HomeForAgentsPage(driver, properties.getEnviroment());
			long timeToLoad = homePage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "HR > People > Loading Test");
			Debugger.log("hrPeopleLoadingTest "+timeToLoad+"=> "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("hrPeopleLoadingTest", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="loginTest")
	public void hrTasksLoadingTest(TestEnvironment testEnvironment) throws Throwable {
		try{ 
			TaskPage taskPage = new TaskPage(driver, properties.getEnviroment());
			long timeToLoad = taskPage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "HR > Tasks > Loading Test");
			Debugger.log("hrTasksLoadingTest "+timeToLoad+"=> "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("hrTasksLoadingTest", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="loginTest")
	public void hrChecklistLoadingTest(TestEnvironment testEnvironment) throws Throwable {
		try{ 
			CheckListsPage checkListPage = new CheckListsPage(driver, properties.getEnviroment());
			long timeToLoad = checkListPage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "HR > Checklist > Loading Test");
			Debugger.log("hrChecklistLoadingTest "+timeToLoad+"=> "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("hrChecklistLoadingTest", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="loginTest")
	public void hrHelpDeskLoadingTest(TestEnvironment testEnvironment) throws Throwable {
		try{ 
			HelpDeskPage helpDeskPage = new HelpDeskPage(driver, properties.getEnviroment());
			long timeToLoad = helpDeskPage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "HR > HelpDesk > Loading Test");
			Debugger.log("hrHelpDeskLoadingTest "+timeToLoad+"=> "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("hrHelpDeskLoadingTest", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	@Test(dataProvider = "hardCodedBrowsers", dependsOnMethods="loginTest")
	public void apptLoadingTest(TestEnvironment testEnvironment) throws Throwable {
		try{ 
			AppsPage appPage = new AppsPage(driver, properties.getEnviroment());
			long timeToLoad = appPage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "APP > Loading Test");
			Debugger.log("apptLoadingTest "+timeToLoad+"=> "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("apptLoadingTest", ex, isSaucelabs);
			assertTrue(false);
		}
	}

	public void settingAccountLoadingTest(TestEnvironment testEnvironment) throws Throwable {
		try{ 
			AccountPage accPage = new AccountPage(driver, properties.getEnviroment());
			long timeToLoad = accPage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "Setting > Account > Loading Test");
			Debugger.log("settingAccountLoadingTest "+timeToLoad+"=> "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("settingAccountLoadingTest", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	public void settingPeopleLoadingTest(TestEnvironment testEnvironment) throws Throwable {
		try{ 
			PeoplePage peoplePage = new PeoplePage(driver, properties.getEnviroment());
			long timeToLoad = peoplePage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "Setting > People > Loading Test");
			Debugger.log("settingPeopleLoadingTest "+timeToLoad+"=> "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("settingPeopleLoadingTest", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	public void settingOtherSettingsLoadingTest(TestEnvironment testEnvironment) throws Throwable {
		try{ 
			OtherSettingPage otherSetting = new OtherSettingPage(driver, properties.getEnviroment());
			long timeToLoad = otherSetting.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "Setting > Other Settings > Loading Test");
			Debugger.log("settingOtherSettingsLoadingTest "+timeToLoad+"=> "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("settingOtherSettingsLoadingTest", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	public void settingV3SettingsLoadingTest(TestEnvironment testEnvironment) throws Throwable {
		try{ 
			V3SettingsPage v3Setting = new V3SettingsPage(driver, properties.getEnviroment());
			long timeToLoad = v3Setting.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "Setting > V3 Settings > Loading Test");
			Debugger.log("settingV3SettingsLoadingTest "+timeToLoad+"=> "+result, isSaucelabs);
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("settingV3SettingsLoadingTest", ex, isSaucelabs);
			assertTrue(false);
		}
	}
	
	

}
