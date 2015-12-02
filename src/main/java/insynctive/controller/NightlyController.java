package insynctive.controller;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import insynctive.dao.AccountDao;
import insynctive.dao.InsynctivePropertyDao;
import insynctive.dao.TestDao;
import insynctive.dao.TestSuiteDao;
import insynctive.model.Account;
import insynctive.model.ParamObject;
import insynctive.model.TestSuite;
import insynctive.utils.TestResults;
import insynctive.utils.TestWebRunner;

@Controller
@RequestMapping
public class NightlyController {
	
	private final int NIGHTLY_ACCOUNT_ID = 6;
	final String NIGHTLY_DEFAULT_ENVIRONMENT = "AutomationQA";
	
	//DB Connections.
	private final InsynctivePropertyDao propertyDao;
	private final AccountDao accDao;
	private final TestSuiteDao testSuiteDao;
	
	//Servlet Context Helper
	private final ServletContext servletContext;

	//Test Runner
	private final TestWebRunner testRunner;

	@Inject
	public NightlyController(TestDao testDao, InsynctivePropertyDao propertyDao, ServletContext servletContext, AccountDao accDao, TestSuiteDao testSuiteDao) {
		this.servletContext = servletContext;
		this.propertyDao = propertyDao;
		this.accDao = accDao;
		this.testSuiteDao = testSuiteDao;
		this.testRunner = new TestWebRunner(servletContext, testSuiteDao, accDao, testDao);
	}
	
	@RequestMapping(value = "/nightly/{environment}/{xmlName}/{browser}", method = RequestMethod.POST)
	@ResponseBody
	public String runNightlyTest(@PathVariable("xmlName") String xmlName, @PathVariable("environment") String environment, @PathVariable("browser") String browser) throws Exception{
		
		Account nightlyAcc = accDao.getAccountByID(NIGHTLY_ACCOUNT_ID);
		ParamObject paramObject = nightlyAcc.getParamObject();
		TestSuite form = testRunner.createTestSuite(paramObject, xmlName, environment, browser);
		
		return "{\"index\" : \""+(testRunner.runTest(form, nightlyAcc))+"\"}";
	}
	
	@RequestMapping(value = "/nightly/{environment}/{xmlName}", method = RequestMethod.POST)
	@ResponseBody
	public String runNightlybyTestSuiteName(@PathVariable("xmlName") String xmlName, @PathVariable("environment") String environment) throws Exception{
		Account nightlyAcc = accDao.getAccountByID(NIGHTLY_ACCOUNT_ID);
		ParamObject paramObject = nightlyAcc.getParamObject();
		
		//Person File RUN
		TestSuite form = testRunner.createTestSuite(paramObject, xmlName, environment, "FIREFOX");
		testRunner.runTest(form, nightlyAcc);
		form = testRunner.createTestSuite(paramObject, xmlName, environment, "CHROME");
		testRunner.runTest(form, nightlyAcc);
		form = testRunner.createTestSuite(paramObject, xmlName, environment, "IPAD");
		testRunner.runTest(form, nightlyAcc);
		
		return "{\"status\" : 200, \"user\" : \""+nightlyAcc.getUsername()+"}";
	}

	@RequestMapping(value = "/nightly/{environment}", method = RequestMethod.POST)
	@ResponseBody
	public String runNightly(@PathVariable("environment") String environment) throws Exception{
		Account nightlyAcc = accDao.getAccountByID(NIGHTLY_ACCOUNT_ID);
		ParamObject paramObject = nightlyAcc.getParamObject();
		
		//Person File RUN
		TestSuite form = testRunner.createTestSuite(paramObject,"Person File", environment, "FIREFOX");
		testRunner.runTest(form, nightlyAcc);
		form = testRunner.createTestSuite(paramObject,"Person File", environment, "CHROME");
		testRunner.runTest(form, nightlyAcc);
		form = testRunner.createTestSuite(paramObject,"Person File", environment, "IPAD");
		testRunner.runTest(form, nightlyAcc);
		
		//Loading Page RUN
		TestSuite loadingForm = testRunner.createTestSuite(paramObject,"Loading Page", environment, "FIREFOX");
		testRunner.runTest(loadingForm, nightlyAcc);
		loadingForm = testRunner.createTestSuite(paramObject, "Loading Page", environment, "CHROME");
		testRunner.runTest(loadingForm, nightlyAcc);
		loadingForm = testRunner.createTestSuite(paramObject, "Loading Page", environment, "IPAD");
		testRunner.runTest(loadingForm, nightlyAcc);
		
		return "{\"status\" : 200, \"user\" : \""+nightlyAcc.getUsername()+"}";
	}

	@RequestMapping(value = "/nightly", method = RequestMethod.POST)
	@ResponseBody
	public String runNightly() throws Exception {
		Account nightlyAcc = accDao.getAccountByID(NIGHTLY_ACCOUNT_ID);
		ParamObject defaultParamObject = nightlyAcc.getParamObject();
		
		//Person File - FIREFOX
		TestSuite form = testRunner.createTestSuite(defaultParamObject,"Person File", NIGHTLY_DEFAULT_ENVIRONMENT, "FIREFOX");
			form.getTestByName("createPersonTest").getParamObject().setBooleanParamOne(false);
		Integer PersonFileFirefox = testRunner.runTest(form, nightlyAcc);
		
		//Person File - CHROME
		form = testRunner.createTestSuite(defaultParamObject,"Person File", NIGHTLY_DEFAULT_ENVIRONMENT, "CHROME");
			form.getTestByName("createPersonTest").getParamObject().setBooleanParamOne(false);
		Integer PersonFileChrome = testRunner.runTest(form, nightlyAcc);
		
		//Person File - IPAD
		form = testRunner.createTestSuite(defaultParamObject,"Person File", NIGHTLY_DEFAULT_ENVIRONMENT, "IPAD");
			form.getTestByName("createPersonTest").getParamObject().setBooleanParamOne(false);
		Integer PersonFileIPad = testRunner.runTest(form, nightlyAcc);
		
		//Open Documents - Person File - FIREFOX 
		TestSuite odpform = testRunner.createTestSuite(defaultParamObject,"Open Documents - Person File", NIGHTLY_DEFAULT_ENVIRONMENT, "FIREFOX");
			odpform.getTestByName("openDocuments").getParamObject().setLoadingTime(5);
		Integer openDocumentsPersonFileFirefox = testRunner.runTest(odpform, nightlyAcc);
		
		//Open Documents - Person File - CHROME
		odpform = testRunner.createTestSuite(defaultParamObject,"Open Documents - Person File", NIGHTLY_DEFAULT_ENVIRONMENT, "CHROME");
			odpform.getTestByName("openDocuments").getParamObject().setLoadingTime(5);
		Integer openDocumentsPersonFileChrome = testRunner.runTest(odpform, nightlyAcc);
		
		//TODO //Open Documents - Person File - IPAD
		
		//Open Documents - Employee Interface - FIRRFOX
		TestSuite odeiform = testRunner.createTestSuite(defaultParamObject,"Open Documents - Employee Interface", NIGHTLY_DEFAULT_ENVIRONMENT, "FIREFOX");
			odeiform.getTestByName("getDocuments").getParamObject().setLoadingTime(5);
		Integer openDocumentsEmployeeInterfaceFirefox = testRunner.runTest(odeiform, nightlyAcc);
		
		//Open Documents - Employee Interface - CHROME
		odeiform = testRunner.createTestSuite(defaultParamObject,"Open Documents - Employee Interface", NIGHTLY_DEFAULT_ENVIRONMENT, "CHROME");
			odeiform.getTestByName("getDocuments").getParamObject().setLoadingTime(5);
		Integer openDocumentsEmployeeInterfaceChrome = testRunner.runTest(odeiform, nightlyAcc);
		
		//TODO //Open Documents - Employee Interface - IPAD
		
		//Loading Page - FIREFOX
		TestSuite loadingForm = testRunner.createTestSuite(defaultParamObject,"Loading Page", NIGHTLY_DEFAULT_ENVIRONMENT, "FIREFOX");
		Integer loadingPageFirefox = testRunner.runTest(loadingForm, nightlyAcc);

		//Loading Page - CHROME
		loadingForm = testRunner.createTestSuite(defaultParamObject, "Loading Page", NIGHTLY_DEFAULT_ENVIRONMENT, "CHROME");
		Integer loadingPageChrome = testRunner.runTest(loadingForm, nightlyAcc);
		
		//Loading Page - IPAD
		loadingForm = testRunner.createTestSuite(defaultParamObject, "Loading Page", NIGHTLY_DEFAULT_ENVIRONMENT, "IPAD");
		Integer loadingPageIPad = testRunner.runTest(loadingForm, nightlyAcc);
		
		//2FA - Email - Agent
		TestSuite twoFAEmailAgentForm = testRunner.createTestSuite(defaultParamObject, "2FA - Email - Agent", "2FA", "CHROME");
			twoFAEmailAgentForm.getTestByName("loginWith2FAEmail").getParamObject().setLoginUsername("insynctiveCBT+agent@gmail.com");
			twoFAEmailAgentForm.getTestByName("loginWith2FAEmail").getParamObject().setLoginPassword("password");
			twoFAEmailAgentForm.getTestByName("config2FAOn").getParamObject().setBooleanParamOne(true);
			twoFAEmailAgentForm.getTestByName("config2FAOn").getParamObject().setBooleanParamTwo(false);
			twoFAEmailAgentForm.getTestByName("config2FAOff").getParamObject().setBooleanParamOne(true);
			twoFAEmailAgentForm.getTestByName("config2FAOff").getParamObject().setBooleanParamTwo(false);
		Integer twoFAEmailAgentID = testRunner.runTest(twoFAEmailAgentForm, nightlyAcc);
		
		//2FA - Email - Employee
		TestSuite twoFAEmailEmployeeForm = testRunner.createTestSuite(defaultParamObject, "2FA - Email - Employee", "2FA", "FIREFOX");
			twoFAEmailEmployeeForm.getTestByName("loginWith2FAEmail").getParamObject().setLoginUsername("insynctiveCBT+employee@gmail.com");
			twoFAEmailEmployeeForm.getTestByName("loginWith2FAEmail").getParamObject().setLoginPassword("password");
			twoFAEmailEmployeeForm.getTestByName("config2FAOn").getParamObject().setBooleanParamOne(false);
			twoFAEmailEmployeeForm.getTestByName("config2FAOn").getParamObject().setBooleanParamTwo(true);
			twoFAEmailEmployeeForm.getTestByName("config2FAOff").getParamObject().setBooleanParamOne(false);
			twoFAEmailEmployeeForm.getTestByName("config2FAOff").getParamObject().setBooleanParamTwo(true);
		Integer twoFAEmailEmployeeID = testRunner.runTest(twoFAEmailEmployeeForm, nightlyAcc, TestResults.workers.get(twoFAEmailAgentID));
		
		//2FA - Phone - Agent
		TestSuite twoFAPhoneAgentForm = testRunner.createTestSuite(defaultParamObject, "2FA - Phone - Agent", "2FA", "IPAD");
			twoFAPhoneAgentForm.getTestByName("loginWith2FAPhone").getParamObject().setLoginUsername("insynctivetestng@gmail.com");
			twoFAPhoneAgentForm.getTestByName("loginWith2FAPhone").getParamObject().setLoginPassword("password");
			twoFAPhoneAgentForm.getTestByName("config2FAOn").getParamObject().setBooleanParamOne(true);
			twoFAPhoneAgentForm.getTestByName("config2FAOn").getParamObject().setBooleanParamTwo(false);
			twoFAPhoneAgentForm.getTestByName("config2FAOff").getParamObject().setBooleanParamOne(true);
			twoFAPhoneAgentForm.getTestByName("config2FAOff").getParamObject().setBooleanParamTwo(false);
		Integer twoFAPhoneAgentID = testRunner.runTest(twoFAPhoneAgentForm, nightlyAcc, TestResults.workers.get(twoFAEmailEmployeeID));
		
		//2FA - Phone - Employee
		TestSuite twoFAPhoneEmployeeForm = testRunner.createTestSuite(defaultParamObject, "2FA - Phone - Employee", "2FA", "CHROME");
			twoFAPhoneEmployeeForm.getTestByName("loginWith2FAPhone").getParamObject().setLoginUsername("insynctivecbt@gmail.com");
			twoFAPhoneEmployeeForm.getTestByName("loginWith2FAPhone").getParamObject().setLoginPassword("password");
			twoFAPhoneEmployeeForm.getTestByName("config2FAOn").getParamObject().setBooleanParamOne(false);
			twoFAPhoneEmployeeForm.getTestByName("config2FAOn").getParamObject().setBooleanParamTwo(true);
			twoFAPhoneEmployeeForm.getTestByName("config2FAOff").getParamObject().setBooleanParamOne(false);
			twoFAPhoneEmployeeForm.getTestByName("config2FAOff").getParamObject().setBooleanParamTwo(true);
		Integer twoFAPhoneEmployeeID = testRunner.runTest(twoFAPhoneEmployeeForm, nightlyAcc, TestResults.workers.get(twoFAPhoneAgentID));
		
		//Make Primary Email and Login
		TestSuite makePrimeryEmailAndLoginForm = testRunner.createTestSuite(defaultParamObject, "Make Primary Email and Login", NIGHTLY_DEFAULT_ENVIRONMENT, "CHROME");
		testRunner.runTest(makePrimeryEmailAndLoginForm, nightlyAcc, TestResults.workers.get(loadingPageIPad));
		
		//Change Email And Login
		TestSuite changeEmailAndLoginForm = testRunner.createTestSuite(defaultParamObject, "Change Email And Login", NIGHTLY_DEFAULT_ENVIRONMENT, "CHROME");
		testRunner.runTest(changeEmailAndLoginForm, nightlyAcc, new Thread[]{
				TestResults.workers.get(PersonFileFirefox),TestResults.workers.get(PersonFileChrome)
		});
		
		return "{\"status\" : 200, \"user\" : \""+nightlyAcc.getUsername()+"}";
	}
	
	@RequestMapping(value = "/nightly-microsoft", method = RequestMethod.POST)
	@ResponseBody
	public String runNightlyMicrosoft() throws Exception {
		Account nightlyAcc = accDao.getAccountByID(NIGHTLY_ACCOUNT_ID);
		ParamObject defaultParamObject = nightlyAcc.getParamObject();
		
		//Person File - IE 10
		TestSuite form10 = testRunner.createTestSuite(defaultParamObject,"Person File", NIGHTLY_DEFAULT_ENVIRONMENT, "IE_10");
			form10.getTestByName("createPersonTest").getParamObject().setBooleanParamOne(false);
		Integer PersonFileFirefox10 = testRunner.runTest(form10, nightlyAcc);
		
		//Person File - IE 11
		TestSuite form11 = testRunner.createTestSuite(defaultParamObject,"Person File", NIGHTLY_DEFAULT_ENVIRONMENT, "IE_11");
		form11.getTestByName("createPersonTest").getParamObject().setBooleanParamOne(false);
		Integer PersonFileFirefox11 = testRunner.runTest(form11, nightlyAcc);
		
		//Open Documents - Person File - IE 10
		TestSuite odpform10 = testRunner.createTestSuite(defaultParamObject,"Open Documents - Person File", NIGHTLY_DEFAULT_ENVIRONMENT, "IE_10");
			odpform10.getTestByName("openDocuments").getParamObject().setLoadingTime(5);
		Integer openDocumentsPersonFileFirefox10 = testRunner.runTest(odpform10, nightlyAcc);
		
		//Open Documents - Person File - IE 11
		TestSuite odpform11 = testRunner.createTestSuite(defaultParamObject,"Open Documents - Person File", NIGHTLY_DEFAULT_ENVIRONMENT, "IE_11");
		odpform11.getTestByName("openDocuments").getParamObject().setLoadingTime(5);
		Integer openDocumentsPersonFileFirefox11 = testRunner.runTest(odpform11, nightlyAcc);
		
		//Open Documents - Employee Interface - IE 10
		TestSuite odeiform10 = testRunner.createTestSuite(defaultParamObject,"Open Documents - Employee Interface", NIGHTLY_DEFAULT_ENVIRONMENT, "IE_10");
			odeiform10.getTestByName("getDocuments").getParamObject().setLoadingTime(5);
		Integer openDocumentsEmployeeInterfaceFirefox10 = testRunner.runTest(odeiform10, nightlyAcc);
		
		//Open Documents - Employee Interface - IE 11
		TestSuite odeiform11 = testRunner.createTestSuite(defaultParamObject,"Open Documents - Employee Interface", NIGHTLY_DEFAULT_ENVIRONMENT, "IE_11");
		odeiform11.getTestByName("getDocuments").getParamObject().setLoadingTime(5);
		Integer openDocumentsEmployeeInterfaceFirefox11 = testRunner.runTest(odeiform11, nightlyAcc);

		//Loading Page - IE 10
		TestSuite loadingForm10 = testRunner.createTestSuite(defaultParamObject,"Loading Page", NIGHTLY_DEFAULT_ENVIRONMENT, "IE_10");
		Integer loadingPageFirefox10 = testRunner.runTest(loadingForm10, nightlyAcc);
		
		//Loading Page - IE 11
		TestSuite loadingForm11 = testRunner.createTestSuite(defaultParamObject,"Loading Page", NIGHTLY_DEFAULT_ENVIRONMENT, "IE_11");
		Integer loadingPageFirefox11 = testRunner.runTest(loadingForm11, nightlyAcc);
		
		return "{\"status\" : 200, \"user\" : \""+nightlyAcc.getUsername()+"}";
	}

}
