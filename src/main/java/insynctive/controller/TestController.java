package insynctive.controller;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.xml.sax.SAXException;

import insynctive.annotation.ParametersFront;
import insynctive.dao.AccountDao;
import insynctive.dao.CreatePersonFormDao;
import insynctive.dao.CrossBrowserAccountDao;
import insynctive.dao.InsynctivePropertyDao;
import insynctive.dao.TestDao;
import insynctive.dao.TestSuiteDao;
import insynctive.exception.ConfigurationException;
import insynctive.model.Account;
import insynctive.model.CreatePersonForm;
import insynctive.model.InsynctiveProperty;
import insynctive.model.ParamObject;
import insynctive.model.Test;
import insynctive.model.TestSuite;
import insynctive.results.TestResultsTestNG;
import insynctive.runnable.RunnableTest;
import insynctive.utils.LoginForm;
import insynctive.utils.ParamObjectField;
import insynctive.utils.ParametersFrontObject;
import insynctive.utils.TestResults;

@Controller
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS, value="session")
public class TestController {
 
//	How to return error codes
//	return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	
	private final int NIGHTLY_ACCOUNT_ID = 6;
	
	//DB Connections.
	private final InsynctivePropertyDao propertyDao;
	private final AccountDao accDao;
	private final CreatePersonFormDao createPersonFormDao;
	private final CrossBrowserAccountDao crossDao;
	private final TestDao testDao;
	private final TestSuiteDao testSuiteDao;
	
	//Servlet Context Helper
	private final ServletContext servletContext;

	//SESSION SCOPES
	private Account account;
	private Integer logedAccID;
	
	

	@Inject
	public TestController(TestDao testDao, InsynctivePropertyDao propertyDao, ServletContext servletContext, AccountDao accDao, CrossBrowserAccountDao crossDao, CreatePersonFormDao createPersonFormDao, TestSuiteDao testSuiteDao) {
		this.servletContext = servletContext;
		this.propertyDao = propertyDao;
		this.accDao = accDao;
		this.crossDao = crossDao;
		this.createPersonFormDao = createPersonFormDao;
		this.testDao = testDao;
		this.testSuiteDao = testSuiteDao;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginGet(HttpSession session){
		ModelAndView model = new ModelAndView();
		model.setViewName("login");
		return model;
	}
	
	@RequestMapping(value = "/configuration", method = RequestMethod.GET)
	public ModelAndView configuration(HttpSession session){
		ModelAndView model = new ModelAndView();
		model.setViewName("advancedConfiguration");
		return model;
	}

	@RequestMapping(value = "/gender_template", method = RequestMethod.GET)
	public ModelAndView getGender() {
		ModelAndView model = new ModelAndView();
		model.setViewName("template/gender");
		return model;
	}
	
	@RequestMapping(value = "/marital_status", method = RequestMethod.GET)
	public ModelAndView getMaritalStatus() {
		ModelAndView model = new ModelAndView();
		model.setViewName("template/maritalStatus");
		return model;
	}
	
	@RequestMapping(value = "/us_address", method = RequestMethod.GET)
	public ModelAndView getUsAddress() {
		ModelAndView model = new ModelAndView();
		model.setViewName("template/us_address");
		return model;
	}
	
	@RequestMapping(value = "/benefit_company" ,method = RequestMethod.GET)
	public ModelAndView modelBenefitCompany() throws ConfigurationException {
		ModelAndView model = new ModelAndView();
		model.setViewName("template/benefit_company");
		return model;
	}
	
	@RequestMapping(value = "/yes_no" ,method = RequestMethod.GET)
	public ModelAndView getYesNo() throws ConfigurationException {
		ModelAndView model = new ModelAndView();
		model.setViewName("template/yes_no");
		return model;
	}

	@RequestMapping(value = "/logout" ,method = RequestMethod.POST)
	public ModelAndView logout() throws Exception{
		logedAccID = null;
		ModelAndView model = new ModelAndView();
		model.setViewName("login");
		return model;
	}
	
	@RequestMapping(value = "/" ,method = RequestMethod.GET)
	public ModelAndView root(HttpSession session){
		ModelAndView model = new ModelAndView();
		if(logedAccID != null){
			model.setViewName("test");
			account = logedAccID != null ? accDao.getAccountByID(logedAccID) : null;
		} else {
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(value = "/main" ,method = RequestMethod.GET)
	public ModelAndView main(){
		ModelAndView model = new ModelAndView();
		model.setViewName("main");
		return model;
	}
	
	@RequestMapping(value = "/accountConfigContent" ,method = RequestMethod.GET)
	public ModelAndView goAccountConfigModel() throws ConfigurationException {
		ModelAndView model = new ModelAndView();
		model.setViewName("accountConfigModel");
		return model;
	}

	@RequestMapping(value = "/model/{model}" ,method = RequestMethod.GET)
	public ModelAndView goModel(@PathVariable("model") String modelName) throws ConfigurationException {
		ModelAndView model = new ModelAndView();
		model.setViewName(modelName);
		return model;
	}
	
	@RequestMapping(value = "/editParameters" ,method = RequestMethod.GET)
	public ModelAndView modelParameters() throws ConfigurationException {
		ModelAndView model = new ModelAndView();
		model.setViewName("parametersModel");
		return model;
	}
	
	@RequestMapping(value = "/dashboard" ,method = RequestMethod.GET)
	public ModelAndView dashboard() throws ConfigurationException {
		ModelAndView model = new ModelAndView();;
		if(logedAccID != null){
			model.setViewName("dashboard");
		} else {
			model.setViewName("login");
		}
		return model;
	}
	
	@RequestMapping(value = "/testSuite" ,method = RequestMethod.GET)
	public ModelAndView testSuite() throws ConfigurationException {
		ModelAndView model = new ModelAndView();
		model.setViewName("template/testSuite");
		return model;
	}
	
	@RequestMapping(value = "/configuration/{accID}", method = RequestMethod.GET)
	@ResponseBody
	public Account configuration(@PathVariable("accID") Integer accID){
		return accDao.getAccountByID(accID);
	}
	
	@ResponseBody
	@RequestMapping(value = "/configuration/save", method = RequestMethod.POST)
	public String save(@RequestBody Account formAcc){
		accDao.save(formAcc);
		return "{\"status\" : 200}";
	}
	
	@RequestMapping(value = "/login" ,method = RequestMethod.POST)
	@ResponseBody
	public String loginPost(@RequestBody LoginForm form) throws Exception{
		Account acc = accDao.getAccountLogin(form.getUsername(), form.getPassword());
		if(acc != null){
			logedAccID = acc.getAccountID();
			return "{\"accID\" : \""+(acc.getAccountID())+"\"}";
		}
		throw new Exception("Account not found");
	}
	
	@RequestMapping(value = "/parameter/{className}/{testName}" ,method = RequestMethod.GET)
	@ResponseBody
	public ParametersFrontObject modelParameters(@PathVariable("className") String className, @PathVariable("testName") String testName) throws ConfigurationException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		return getParams(className, testName);
	}

	@RequestMapping(value = "/accountProperties" ,method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Account getAccountProperties() throws ConfigurationException {
		return accDao.getAccountByID(logedAccID);
	}

	@RequestMapping(value = "/video/{testListenenerIndex}" ,method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getVideo(@PathVariable("testListenenerIndex") Integer testListenenerIndex) throws InterruptedException, ConfigurationException{
		if (account != null && account.getAccountProperty().isRemote()) {
			int times = 1;
			int sleep = 2000;
			
			while (times <= 30) {
				String videoLink = insynctive.utils.TestResults.getVideo(testListenenerIndex);
				if (videoLink != null) {
					return videoLink;
				}
				Thread.sleep(sleep);
				times++;
			}
			return "";//TimeOut
		} else {
			return "";//Local Test
		}
	}
	
	@RequestMapping(value = "/testsSuites" ,method = RequestMethod.GET)
	@ResponseBody
	public List<String> getTestsSuitesString() throws MalformedURLException, URISyntaxException{
		List<String> testsSuites = getTestSuitesForRunUI();
		return testsSuites;
	}
	
	
	@RequestMapping(value = "/status/{index}" ,method = RequestMethod.GET)
	@ResponseBody
	public TestResultsTestNG getStatus(@PathVariable("index") Integer index){
		Map<Integer, TestListenerAdapter> tla = TestResults.listeners;
		
		List<Test> resultsAux = new ArrayList<Test>();
		TestResultsTestNG testResults = new TestResultsTestNG();
		
		for(ITestResult testResult : tla.get(index).getPassedTests()){
			resultsAux.add(new Test(testResult.getName(),"SUCCESS"));
		}
		testResults.setPassedTests(resultsAux);
		resultsAux = new ArrayList<Test>();
		
		for(ITestResult testResult : tla.get(index).getFailedTests()){
			resultsAux.add(new Test(testResult.getName(),"FAILED"));
		}
		testResults.setFailedTests(resultsAux);
		resultsAux = new ArrayList<Test>();
		
		for(ITestResult testResult : tla.get(index).getSkippedTests()){
			resultsAux.add(new Test(testResult.getName(),"SKIPPED"));
		}
		testResults.setSkipedTests(resultsAux);
		resultsAux = new ArrayList<Test>();
		
		return testResults;
	} 
	
	@RequestMapping(value = "/getTestSuite/{ID}" ,method = RequestMethod.GET)
	@ResponseBody
	public TestSuite getTestsRuns(@PathVariable("ID") Integer id) {
		return testSuiteDao.getTestSuiteByID(id);
	}
	
	@RequestMapping(value = "/get/{xmlName}" ,method = RequestMethod.GET)
	@ResponseBody
	public TestSuite getTestsRuns(@PathVariable("xmlName") String xmlName) {
		TestSuite testSuite = null;
		try{
			List<XmlSuite> suite = getXmlTestSuiteForUI(xmlName);
			testSuite = new TestSuite();
			
			for(XmlTest test : suite.get(0).getTests()){
				testSuite.setTestSuiteName(test.getName());
				for(XmlClass classes : test.getClasses()){
					
					testSuite.setClassName(classes.getName());
					
					for(XmlInclude includeMethod: classes.getIncludedMethods()){
						Test newTest = new Test(includeMethod.getName());
						ParametersFrontObject params = getParams(classes.getName(), includeMethod.getName());
						ParamObject newParamObject = new ParamObject();
						for(String param : params.getParams()){
//							Field fieldByName = newParamObject.getFieldByName(param.split("\\.")[0]);
//							fieldByName.set(newParamObject, account.getParamObject().getValueByName(param.split("\\.")[0]));
							Field fieldByName = newParamObject.getFieldByName(param);
							fieldByName.set(newParamObject.getFieldToSet(param), account.getParamObject().getValueByName(param));
						}
						if(params.getParams().size() > 0){
							newTest.setParamObject(newParamObject);
						}
						testSuite.addMethod(newTest);
					}
				}
			} 
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return testSuite;
	}
	
	@RequestMapping(value = "/getAllTestsSuites" ,method = RequestMethod.GET)
	@ResponseBody
	public List<TestSuite> getTestsSuites() {
		return testSuiteDao.getAllTestSuite();
	}
	
	@RequestMapping(value = "/test/{xmlName}/{environment}/{browser}", method = RequestMethod.POST)
	@ResponseBody
	public String runTest(@RequestBody TestSuite form, @PathVariable("xmlName") String xmlName, @PathVariable("environment") String environment, @PathVariable("browser") String browser) throws ConfigurationException{
		form.setBrowser(browser);
		form.setEnvironment(environment);
		form.setTestSuiteName(xmlName);
		return "{\"index\" : \""+(runTest(form, accDao.getAccountByID(logedAccID)))+"\"}";
	}
	
	@RequestMapping(value = "/nightly/{environment}/{xmlName}/{browser}", method = RequestMethod.POST)
	@ResponseBody
	public String runNightlyTest(@PathVariable("xmlName") String xmlName, @PathVariable("environment") String environment, @PathVariable("browser") String browser) throws ConfigurationException{
		
		Account nightlyAcc = accDao.getAccountByID(NIGHTLY_ACCOUNT_ID);
		ParamObject paramObject = nightlyAcc.getParamObject();
		TestSuite form = createTestSuite(paramObject, xmlName, environment, browser);
		
		return "{\"index\" : \""+(runTest(form, nightlyAcc))+"\"}";
	}
	
	@RequestMapping(value = "/retry/{ID}", method = RequestMethod.GET)
	@ResponseBody
	public String retry(@PathVariable("ID") Integer testSuiteID) throws CloneNotSupportedException {
		
		TestSuite testSuiteToRetry = testSuiteDao.getTestSuiteByID(testSuiteID);
		TestSuite testSuite = testSuiteToRetry.getNewWithOutIDs();
		
		return "{\"index\" : \""+(runTest(testSuite, accDao.getAccountByID(logedAccID)))+"\"}";
	}
	
	@RequestMapping(value = "/nightly/{environment}/{xmlName}", method = RequestMethod.POST)
	@ResponseBody
	public String runNightlybyTestSuiteName(@PathVariable("xmlName") String xmlName, @PathVariable("environment") String environment) throws ConfigurationException{
		Account nightlyAcc = accDao.getAccountByID(NIGHTLY_ACCOUNT_ID);
		ParamObject paramObject = nightlyAcc.getParamObject();
		
		//Person File RUN
		TestSuite form = createTestSuite(paramObject, xmlName, environment, "FIREFOX");
		runTest(form, nightlyAcc);
		form = createTestSuite(paramObject, xmlName, environment, "CHROME");
		runTest(form, nightlyAcc);
		form = createTestSuite(paramObject, xmlName, environment, "IPAD");
		runTest(form, nightlyAcc);
		
		return "{\"status\" : 200, \"user\" : \""+nightlyAcc.getUsername()+"}";
	}

	@RequestMapping(value = "/nightly/{environment}", method = RequestMethod.POST)
	@ResponseBody
	public String runNightly(@PathVariable("environment") String environment) throws ConfigurationException{
		Account nightlyAcc = accDao.getAccountByID(NIGHTLY_ACCOUNT_ID);
		ParamObject paramObject = nightlyAcc.getParamObject();
		
		//Person File RUN
		TestSuite form = createTestSuite(paramObject,"Person File", environment, "FIREFOX");
		runTest(form, nightlyAcc);
		form = createTestSuite(paramObject,"Person File", environment, "CHROME");
		runTest(form, nightlyAcc);
		form = createTestSuite(paramObject,"Person File", environment, "IPAD");
		runTest(form, nightlyAcc);
		
		//Loading Page RUN
		TestSuite loadingForm = createTestSuite(paramObject,"Loading Page", environment, "FIREFOX");
		runTest(loadingForm, nightlyAcc);
		loadingForm = createTestSuite(paramObject, "Loading Page", environment, "CHROME");
		runTest(loadingForm, nightlyAcc);
		loadingForm = createTestSuite(paramObject, "Loading Page", environment, "IPAD");
		runTest(loadingForm, nightlyAcc);
		
		return "{\"status\" : 200, \"user\" : \""+nightlyAcc.getUsername()+"}";
	}
	
	@RequestMapping(value = "/test/{testName}/{index}" ,method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getTest(@PathVariable("testName") String testName, @PathVariable("index") Integer index) throws ConfigurationException{
		Map<Integer, TestListenerAdapter> tla = TestResults.listeners;
		
		tla.get(index).getPassedTests().forEach(failTest -> System.out.println(failTest));
		tla.get(index).getSkippedTests().forEach(failTest -> System.out.println(failTest));
		tla.get(index).getFailedTests().forEach(failTest -> System.out.println(failTest));
		tla.get(index).getTestContexts().forEach(failTest -> System.out.println(failTest));
		return null;
	}
	
	@RequestMapping(value = "/start/OE" ,method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String startCreatePersonOpenEnrollment(@RequestBody CreatePersonForm form) throws ConfigurationException{
		Integer newPersonID = createPersonFormDao.saveCreatePersonForm(form);
		
		List<XmlSuite> suites = getXmlTestSuiteForExternalUser("CreatePerson_OpenEnrollment");
		HashMap<String,String> params = new HashMap<String, String>();
		params.put("personID", String.valueOf(newPersonID));
		
		for (XmlSuite suite : suites){
			for(XmlTest test : suite.getTests()){
				test.setParameters(params);
			}
		}
		
		TestListenerAdapter testListenerAdapter = new TestListenerAdapter();
		
		TestNG testNG = new TestNG();
		testNG.setXmlSuites(suites);
		testNG.setPreserveOrder(true);
		testNG.addListener(testListenerAdapter);
		
		//START TEST IN OTHER THREAD
		Thread thread = new Thread(new RunnableTest(testNG));
		thread.start();
		
		return "{\"status\" : 200}";
	}
	
	@RequestMapping(value = "/start/OB" ,method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String startCreatePersonOnBoarding(@RequestBody CreatePersonForm form) throws ConfigurationException{
		Integer newPersonID = createPersonFormDao.saveCreatePersonForm(form);
		
		List<XmlSuite> suites = getXmlTestSuiteForExternalUser("CreatePerson_Onboarding");
		HashMap<String,String> params = new HashMap<String, String>();
		params.put("personID", String.valueOf(newPersonID));
		
		for (XmlSuite suite : suites){
			for(XmlTest test : suite.getTests()){
				test.setParameters(params);
			}
		}
		
		TestListenerAdapter testListenerAdapter = new TestListenerAdapter();
		
		TestNG testNG = new TestNG();
		testNG.setXmlSuites(suites);
		testNG.setPreserveOrder(true);
		testNG.addListener(testListenerAdapter);
		
		//START TEST IN OTHER THREAD
		Thread thread = new Thread(new RunnableTest(testNG));
		thread.start();
		
		return "{\"index\" : 200}";
	}
	
	@RequestMapping(value = "/isPersonCreated/{tlaIndex}" ,method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String isPersonCreated(@PathVariable("tlaIndex") Integer tlaIndex) throws Exception{
		return checkStatusOfMethodInTLA(tlaIndex, "createPersonTest");
	}
	
	@RequestMapping(value = "/checkIfIsJobAdded/{tlaIndex}" ,method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String checkIfIsJobAdded(@PathVariable("tlaIndex") Integer tlaIndex) throws Exception{
		return checkStatusOfMethodInTLA(tlaIndex, "assignJob");
	}
	
	@RequestMapping(value = "/saveAccountConfig" ,method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String saveAccountConfig(@RequestBody InsynctiveProperty properties) throws ConfigurationException{
		propertyDao.update(properties);
		return "Done!";
	}

	@RequestMapping(value = "/clearTest/{index}" ,method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public void clearTestResult(@PathVariable("index") Integer index) throws ConfigurationException {
		TestResults.removeListener(index);
	}

	/*Private Methods*/
	private List<XmlSuite> getXmlTestSuiteForUI(String xmlName){
		return getXmlTestSuite(xmlName, "/WEB-INF/testsSuits/");
	}
	
	private List<XmlSuite> getXmlTestSuiteForExternalUser(String xmlName){
		return getXmlTestSuite(xmlName, "/WEB-INF/externalTest/");
	}
	
	private List<XmlSuite> getXmlTestSuite(String xmlName, String path) {
		String xmlFileName = new File( servletContext.getRealPath(path+xmlName+".xml")).getPath();
		
		List<XmlSuite> suite = getSuite(xmlFileName);
		
		return suite;
	}

	private List<XmlSuite> getSuite(String xmlFileName) {
		List<XmlSuite> suite = null;
		try
		{
			suite = (List <XmlSuite>)(new Parser(xmlFileName).parse());
		}
		catch (ParserConfigurationException e)
		{
		    e.printStackTrace();
		}
		catch (SAXException e)
		{
		    e.printStackTrace();
		}
		catch (IOException e)
		{
		    e.printStackTrace();
		}
		return suite;
	}
	
	private List<String> getTestSuitesForRunUI() throws MalformedURLException, URISyntaxException{
		return getTestSuites("/WEB-INF/testsSuits/");
	}
	
	private List<String> getTestSuitesForExternalClient() throws MalformedURLException, URISyntaxException{
		return getTestSuites("/WEB-INF/externalTest/");
	}
	
	private List<String> getTestSuites(String path) throws MalformedURLException, URISyntaxException{
		List<String> results = new ArrayList<String>();

		File[] files = new File( servletContext.getRealPath(path)).listFiles();
		
		for (File file : files) {
		    if (file.isFile()) {
		        results.add(file.getName());
		    }
		}
		return results;
	}
	
	private String checkStatusOfMethodInTLA(Integer tlaIndex, String nameOfTest) throws Exception {
		List<ITestResult> passedTests = TestResults.listeners.get(tlaIndex).getPassedTests();
		for(ITestResult test : passedTests){
			if(test.getMethod().getMethodName().equals("createPersonTest")){
				return "{\"status\": true}";
			}
		}
		List<ITestResult> failedTests = TestResults.listeners.get(tlaIndex).getFailedTests();
		for(ITestResult test : failedTests){
			if(test.getMethod().getMethodName().equals("createPersonTest")){
				throw new Exception("The Methods Create Job Fails");
			}
		}
		return "{\"status\": false}";
	}
	
	private ParametersFrontObject getParams(String className, String testName) throws ClassNotFoundException, NoSuchMethodException, SecurityException{
		Class<?> aClass = Class.forName(className);
		Method testMethod = aClass.getMethod(testName, Integer.class);
		Annotation[] annotations = testMethod.getAnnotationsByType(ParametersFront.class);
		List<ParamObjectField> params = new ArrayList<>();
		List<String> labels = new ArrayList<>();;
		ParametersFrontObject parametersFrontObject = new ParametersFrontObject();
		
		for(Annotation annotation : annotations){
		    	ParametersFront parameters = (ParametersFront) annotation;
		    	
		    	Collections.addAll(params, parameters.attrs()); 
		    	Collections.addAll(labels, parameters.labels()); 
		    	
		    	parametersFrontObject.setParams(params.stream().map(param -> param.getValue()).collect(Collectors.toList()));
		    	parametersFrontObject.setLabels(labels);
		}
		return parametersFrontObject;
	}

	private TestSuite createTestSuite(ParamObject paramObject, String testSuiteName, String environment, String browser) {
		TestSuite testSuite = new TestSuite();
		testSuite.setEnvironment(environment);
		testSuite.setBrowser(browser);
		testSuite.setTestSuiteName(testSuiteName);
		List<XmlSuite> suites = getXmlTestSuiteForUI(testSuiteName);
		List<Test> listIncMethod = new ArrayList<Test>();
		for (XmlSuite suite : suites) {
			for(XmlTest test : suite.getTests()){
				for(XmlClass clazz : test.getClasses()){
					testSuite.setClassName(clazz.getName());
					for(XmlInclude incMethod : clazz.getIncludedMethods()){
							Test newTest= new Test(incMethod.getName());
							newTest.setParamObject(paramObject);
							listIncMethod.add(newTest);
					}
				}
			}
		}
		testSuite.setTests(listIncMethod);
		
		return testSuite;
	}

	private Integer runTest(TestSuite form, Account acc) {
		//Increment Run ID of account and update it.
		InsynctiveProperty properties = acc.getAccountProperty();
		properties.setEnvironment(form.getEnvironment());
		accDao.update(acc);
		List<XmlSuite> suites = getXmlTestSuiteForUI(form.getTestSuiteName());

		//Save Test Suite with all TESTS and Parameters
		TestSuite testSuite = new TestSuite();
		testSuite.setTestSuiteName(form.getTestSuiteName());
		testSuite.setTests(form.getTests());
		testSuite.setClassName(getClassnameFromXMLTestSuite(form.getTestSuiteName()));
		testSuite.setBrowser(form.getBrowser());
		testSuite.setEnvironment(form.getEnvironment());
		testSuite.setRemote(properties.isRemote());
		testSuite.setTester(acc.getUsername());
		testSuite.setStatus("RUNNING");
		Integer testSuiteID = testSuiteDao.save(testSuite);
		
		//Add TestID parameters in method (THE XML NEED TO HAVE ONLY ONE SUTIE)
		for(Test test : form.getTests()){
			test.setTestSuiteID(testSuiteID);
			//Add Parameters to Test
			for(XmlTest xmlTest : suites.get(0).getTests()){
				for(XmlClass classes : xmlTest.getClasses()){
					for(XmlInclude methodsInXML: classes.getIncludedMethods()){
						if(methodsInXML.getName().equals(test.getTestName())){
							methodsInXML.addParameter("TestID", test.getTestID().toString());
						}
					}
				}
			}
		}
		
		//Initialize tests.
		Map<String, String> parameters = new HashMap<>();
		parameters.put("accountID", String.valueOf(acc.getAccountID()));
		parameters.put("runID", acc.getRunIDString());
		parameters.put("bowser", form.getBrowser());
		parameters.put("testID", testSuite.getTestSuiteID().toString());
		parameters.put("testName", form.getTestSuiteName());
		
		//Add to Test Suite
		for (XmlSuite suite : suites) {
			suite.setParameters(parameters);
		}
		
		//Is not using now.
		
		//Create Test listener and add it.
		TestListenerAdapter testListenerAdapter = new TestListenerAdapter();
		TestResults.addListener(testSuite.getTestSuiteID(), testListenerAdapter);
		
		//Make test and run the thread.
		TestNG testNG = new TestNG();
		testNG.setXmlSuites(suites);
		testNG.setPreserveOrder(true);
		testNG.addListener(testListenerAdapter);
		
		//START TEST IN OTHER THREAD
		Thread thread = new Thread(new RunnableTest(testNG, testSuite, testListenerAdapter, testSuiteDao, testDao));
		TestResults.addWorker(testSuite.getTestSuiteID(), thread);
		thread.start();
		
		return testSuite.getTestSuiteID();
	}

	private String getClassnameFromXMLTestSuite(String xmlName) {
		List<XmlSuite> suites = getXmlTestSuiteForUI(xmlName);
		for(XmlTest xmlTest : suites.get(0).getTests()){
			for(XmlClass classes : xmlTest.getClasses()){
				return classes.getName();
			}
		}
		return null;
	}

}
