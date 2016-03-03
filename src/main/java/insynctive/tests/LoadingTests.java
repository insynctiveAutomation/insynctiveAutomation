package insynctive.tests;

import static org.junit.Assert.assertTrue;

import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import insynctive.annotation.ParametersFront;
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
import insynctive.utils.ParamObjectField;
import insynctive.utils.data.TestEnvironment;

@TransactionConfiguration
@Transactional
public class LoadingTests extends TestMachine {

	int TIME_EXPECTED = 10000;
	
	@BeforeClass
	@Parameters({"environment", "browser", "isRemote", "isNotification", "testSuiteID", "testName"})
	public void tearUp(String environment, String browser, String isRemote, String isNotification, String testSuiteID, String testName) throws Exception {
		tearUp(browser, environment, isRemote, isNotification, testSuiteID);
		this.sessionName = testName;
	}
	
	@Test()
	@Parameters({"TestID"})
	@ParametersFront(attrs={ParamObjectField.LOGIN_USERNAME, ParamObjectField.LOGIN_PASSWORD}, labels={"Login Username", "Login Password"})
	public void loginTest(@Optional("TestID") Integer testID)
			throws Exception {
		changeParamObject(testID);
		startTest();

		try{ 
			LoginPage loginPage = login();
			boolean result = loginPage.isLoggedIn();
			setResult(result, "Login Test");
			Debugger.log("loginTest => "+result, "Loading-Time");
			assertTrue(result);
		} catch(Exception ex){
			failTest("Login",  ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(attrs={ParamObjectField.LOADING_TIME}, labels={"Loading time (segs)"})
	public void hrPeopleLoadingTest(@Optional("TestID") Integer testID) throws Throwable {
		changeParamObject(testID);
		TIME_EXPECTED = paramObject.getLoadingTime()*1000;
		try{ 
			HomeForAgentsPage homePage = new HomeForAgentsPage(driver, environment);
			long timeToLoad = homePage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "HR > People > Loading Test (Duration: "+timeToLoad+" ms)");
			Debugger.log("hrPeopleLoadingTest "+timeToLoad+"=> "+result, "Loading-Time");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("hrPeopleLoadingTest", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(attrs={ParamObjectField.LOADING_TIME}, labels={"Loading time (segs)"})
	public void hrTasksLoadingTest(@Optional("TestID") Integer testID) throws Throwable {
		changeParamObject(testID);
		TIME_EXPECTED = paramObject.getLoadingTime()*1000;
		try{ 
			TaskPage taskPage = new TaskPage(driver, environment);
			long timeToLoad = taskPage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "HR > Tasks > Loading Test (Duration: "+timeToLoad+" ms)");
			Debugger.log("hrTasksLoadingTest "+timeToLoad+"=> "+result, "Loading-Time");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("hrTasksLoadingTest", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.LOADING_TIME}, 
			labels={"Loading time (segs)"})
	public void hrChecklistLoadingTest(@Optional("TestID") Integer testID) throws Throwable {
		changeParamObject(testID);
		TIME_EXPECTED = paramObject.getLoadingTime()*1000;
		try{ 
			CheckListsPage checkListPage = new CheckListsPage(driver, environment);
			long timeToLoad = checkListPage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "HR > Checklist > Loading Test (Duration: "+timeToLoad+" ms)");
			Debugger.log("hrChecklistLoadingTest "+timeToLoad+"=> "+result, "Loading-Time");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("hrChecklistLoadingTest", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.LOADING_TIME}, 
			labels={"Loading time (segs)"})
	public void hrHelpDeskLoadingTest(@Optional("TestID") Integer testID) throws Throwable {
		changeParamObject(testID);
		TIME_EXPECTED = paramObject.getLoadingTime()*1000;
		try{ 
			HelpDeskPage helpDeskPage = new HelpDeskPage(driver, environment);
			long timeToLoad = helpDeskPage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "HR > HelpDesk > Loading Test (Duration: "+timeToLoad+" ms)");
			Debugger.log("hrHelpDeskLoadingTest "+timeToLoad+"=> "+result, "Loading-Time");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("hrHelpDeskLoadingTest", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.LOADING_TIME}, 
			labels={"Loading time (segs)"})
	public void apptLoadingTest(@Optional("TestID") Integer testID) throws Throwable {
		changeParamObject(testID);
		TIME_EXPECTED = paramObject.getLoadingTime()*1000;
		try{ 
			AppsPage appPage = new AppsPage(driver, environment);
			long timeToLoad = appPage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "APP > Loading Test (Duration: "+timeToLoad+" ms)");
			Debugger.log("apptLoadingTest "+timeToLoad+"=> "+result, "Loading-Time");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("apptLoadingTest", ex, isRemote);
			assertTrue(false);
		}
	}

	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.LOADING_TIME}, 
			labels={"Loading time (segs)"})
	public void settingAccountLoadingTest(@Optional("TestID") Integer testID) throws Throwable {
		changeParamObject(testID);
		TIME_EXPECTED = paramObject.getLoadingTime()*1000;
		try{ 
			AccountPage accPage = new AccountPage(driver, environment);
			long timeToLoad = accPage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "Setting > Account > Loading Test (Duration: "+timeToLoad+" ms)");
			Debugger.log("settingAccountLoadingTest "+timeToLoad+"=> "+result, "Loading-Time");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("settingAccountLoadingTest", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.LOADING_TIME}, 
			labels={"Loading time (segs)"})
	public void settingPeopleLoadingTest(@Optional("TestID") Integer testID) throws Throwable {
		changeParamObject(testID);
		TIME_EXPECTED = paramObject.getLoadingTime()*1000;
		try{ 
			PeoplePage peoplePage = new PeoplePage(driver, environment);
			long timeToLoad = peoplePage.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "Setting > People > Loading Test (Duration: "+timeToLoad+" ms)");
			Debugger.log("settingPeopleLoadingTest "+timeToLoad+"=> "+result, "Loading-Time");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("settingPeopleLoadingTest", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.LOADING_TIME}, 
			labels={"Loading time (segs)"})
	public void settingOtherSettingsLoadingTest(@Optional("TestID") Integer testID) throws Throwable {
		changeParamObject(testID);
		TIME_EXPECTED = paramObject.getLoadingTime()*1000;
		try{ 
			OtherSettingPage otherSetting = new OtherSettingPage(driver, environment);
			long timeToLoad = otherSetting.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "Setting > Other Settings > Loading Test (Duration: "+timeToLoad+" ms)");
			Debugger.log("settingOtherSettingsLoadingTest "+timeToLoad+"=> "+result, "Loading-Time");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("settingOtherSettingsLoadingTest", ex, isRemote);
			assertTrue(false);
		}
	}
	
	@Test
	@Parameters({"TestID"})
	@ParametersFront(
			attrs={ParamObjectField.LOADING_TIME}, 
			labels={"Loading time (segs)"})
	public void settingV3SettingsLoadingTest(@Optional("TestID") Integer testID) throws Throwable {
		changeParamObject(testID);
		TIME_EXPECTED = paramObject.getLoadingTime()*1000;
		try{ 
			V3SettingsPage v3Setting = new V3SettingsPage(driver, environment);
			long timeToLoad = v3Setting.getTimeToLoad();
			
			boolean result = timeToLoad < TIME_EXPECTED;
			
			setResult(result, "Setting > V3 Settings > Loading Test (Duration: "+timeToLoad+" ms)"); 
			Debugger.log("settingV3SettingsLoadingTest "+timeToLoad+"=> "+result, "Loading-Time");
			assertTrue(result);
		}catch (Exception ex){ 
			failTest("settingV3SettingsLoadingTest", ex, isRemote);
			assertTrue(false);
		}
	}
}
