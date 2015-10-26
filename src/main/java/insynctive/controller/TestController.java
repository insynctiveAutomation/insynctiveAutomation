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

import org.springframework.beans.factory.annotation.Value;
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
import insynctive.exception.ConfigurationException;
import insynctive.model.Account;
import insynctive.model.CreatePersonForm;
import insynctive.model.InsynctiveProperty;
import insynctive.model.ParamObject;
import insynctive.model.Test;
import insynctive.results.IncludeMethod;
import insynctive.results.TestResultsTestNG;
import insynctive.results.TestSuite;
import insynctive.runnable.RunnableTest;
import insynctive.utils.LoginForm;
import insynctive.utils.ParamObjectField;
import insynctive.utils.ParametersFrontObject;

@Controller
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS, value="session")
public class TestController {
 
	@Value("${local}")
	private Boolean local;
	
	private final InsynctivePropertyDao propertyDao;
	private final AccountDao accDao;
	private final CreatePersonFormDao createPersonFormDao;
	private final CrossBrowserAccountDao crossDao;
	private final TestDao testDao;
	
	private final ServletContext servletContext;

	private Integer accID;//NOW IM USING THIS BECAUSE WE DONT HAVE LOGIN
	private Account account;
	
	private Integer threadIndex = 0;
	private Map<Integer, Thread> workers = new HashMap<>();
	
	private Integer testListenenerIndex = 0;
	private Map<Integer, TestListenerAdapter> tla = new HashMap<>();
	
	@Inject
	public TestController(TestDao testDao, InsynctivePropertyDao propertyDao, ServletContext servletContext, AccountDao accDao, CrossBrowserAccountDao crossDao, CreatePersonFormDao createPersonFormDao) {
		this.servletContext = servletContext;
		this.propertyDao = propertyDao;
		this.accDao = accDao;
		this.crossDao = crossDao;
		this.createPersonFormDao = createPersonFormDao;
		this.testDao = testDao;
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
	
	@RequestMapping(value = "/login" ,method = RequestMethod.POST)
	@ResponseBody
	public String loginPost(@RequestBody LoginForm form) throws Exception{
		Account acc = accDao.getAccountLogin(form.getUsername(), form.getPassword());
		if(acc != null){
			accID = acc.getAccountID();
			return "{\"accID\" : \""+(acc.getAccountID())+"\"}";
		}
		throw new Exception("Account not found");
	}
	
	@RequestMapping(value = "/logout" ,method = RequestMethod.POST)
	public ModelAndView logout() throws Exception{
		accID = null;
		ModelAndView model = new ModelAndView();
		model.setViewName("login");
		return model;
	}
	
	@RequestMapping(value = "/" ,method = RequestMethod.GET)
	public ModelAndView root(HttpSession session){
		ModelAndView model = new ModelAndView();
		model.setViewName("test");
		account = accID != null ? accDao.getAccountByID(accID) : null;
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
	
	@RequestMapping(value = "/parameter/{className}/{testName}" ,method = RequestMethod.GET)
	@ResponseBody
	public ParametersFrontObject modelParameters(@PathVariable("className") String className, @PathVariable("testName") String testName) throws ConfigurationException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		ModelAndView model = new ModelAndView();
		model.setViewName("parametersModel");
		return getParams(className, testName);
	}

	@RequestMapping(value = "/accountProperties" ,method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public Account getAccountProperties() throws ConfigurationException {
		return accDao.getAccountByID(accID);
	}

	@RequestMapping(value = "/clearTest/{index}" ,method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public void clearTestResult(@PathVariable("index") Integer index) throws ConfigurationException {
		tla.remove(index);
	}

	@RequestMapping(value = "/video/{testListenenerIndex}" ,method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getVideo(@PathVariable("testListenenerIndex") Integer testListenenerIndex) throws InterruptedException, ConfigurationException{
		if (account.getAccountProperty().isRemote()) {
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
	public List<String> getTestsSuites() throws MalformedURLException, URISyntaxException{
		List<String> testsSuites = getTestSuitesForRunUI();
		return testsSuites;
	}
	
	@RequestMapping(value = "/environments" ,method = RequestMethod.GET)
	@ResponseBody
	public List<String> getEnvironments(){
		List<String> environments = new ArrayList<String>();
		
		return environments;
	}
	
	@RequestMapping(value = "/status/{index}" ,method = RequestMethod.GET)
	@ResponseBody
	public TestResultsTestNG getStatus(@PathVariable("index") Integer index){
		List<IncludeMethod> resultsAux = new ArrayList<IncludeMethod>();
		TestResultsTestNG testResults = new TestResultsTestNG();
		
		for(ITestResult testResult : tla.get(index).getPassedTests()){
			resultsAux.add(new IncludeMethod(testResult.getName(),"SUCCESS"));
		}
		testResults.setPassedTests(resultsAux);
		resultsAux = new ArrayList<IncludeMethod>();
		
		for(ITestResult testResult : tla.get(index).getFailedTests()){
			resultsAux.add(new IncludeMethod(testResult.getName(),"FAILED"));
		}
		testResults.setFailedTests(resultsAux);
		resultsAux = new ArrayList<IncludeMethod>();
		
		for(ITestResult testResult : tla.get(index).getSkippedTests()){
			resultsAux.add(new IncludeMethod(testResult.getName(),"SKIPPED"));
		}
		testResults.setSkipedTests(resultsAux);
		resultsAux = new ArrayList<IncludeMethod>();
		
		return testResults;
	} 
	
	@RequestMapping(value = "/get/{xmlName}" ,method = RequestMethod.GET)
	@ResponseBody
	public TestSuite getTestsRuns(@PathVariable("xmlName") String xmlName) {
		TestSuite testSuite = null;
		try{
			List<XmlSuite> suite = getXmlTestSuiteForUI(xmlName);
			testSuite = new TestSuite();
			
			for(XmlTest test : suite.get(0).getTests()){
				testSuite.setTestName(test.getName());
				for(XmlClass classes : test.getClasses()){
					testSuite.setClassName(classes.getName());
					for(XmlInclude includeMethod: classes.getIncludedMethods()){
						IncludeMethod newIncludeMethod = new IncludeMethod(includeMethod.getName(), "-");
						ParametersFrontObject params = getParams(classes.getName(), includeMethod.getName());
						ParamObject paramObject = new ParamObject();
						for(String param : params.getParams()){
							Field fieldByName = paramObject.getFieldByName(param);
							fieldByName.set(paramObject, account.getParamObject().getValueByName(param));
						}
						if(params.getParams().size() > 0){
							newIncludeMethod.setParamObject(paramObject);
						}
						
						testSuite.addMethod(newIncludeMethod);
					}
				}
			} 
		} catch(Exception ex) {
			System.out.println(ex);
		}
		return testSuite;
	}
	
	@RequestMapping(value = "/test/{xmlName}/{environment}/{browser}" ,method = RequestMethod.POST)
	@ResponseBody
	public String runTest(@RequestBody TestSuite form, @PathVariable("xmlName") String xmlName, @PathVariable("environment") String environment, @PathVariable("browser") String browser) throws ConfigurationException{
		
		Map<String, String> parameters = new HashMap<>();
		parameters.put("accountID", accID.toString());
		parameters.put("bowser", browser);
		parameters.put("testID", testListenenerIndex.toString());
		
		List<XmlSuite> suites = getXmlTestSuiteForUI(xmlName);
		for (XmlSuite suite : suites) {
			suite.setParameters(parameters);
		}
		
		
		List<IncludeMethod> includeMethods = form.getIncludeMethods();
		for(IncludeMethod includeMethod : includeMethods){
			Test newTest = new Test();
			newTest.setParamObject(includeMethod.getParamObject());
			newTest.setTestName(includeMethod.getName());
			
			for(XmlTest test : suites.get(0).getTests()){
				for(XmlClass classes : test.getClasses()){
					for(XmlInclude methodsInXML: classes.getIncludedMethods()){
						if(methodsInXML.getName().equals(includeMethod.getName())){
							Integer testID = testDao.save(newTest);
							methodsInXML.addParameter("TestID", testID.toString());
						}
					}
				}
			}
		}
		
		account = accDao.incrementRunIDAndGetAcc(accID);
		InsynctiveProperty properties = account.getAccountProperty();
		properties.setEnvironment(environment);
		propertyDao.update(properties);
		
		insynctive.utils.TestResults.resetResults();
		TestListenerAdapter testListenerAdapter = new TestListenerAdapter();
		tla.put(testListenenerIndex, testListenerAdapter);
		
		TestNG testNG = new TestNG();
		
		testNG.setXmlSuites(suites);
		testNG.setPreserveOrder(true);
		testNG.addListener(testListenerAdapter);
		
		//START TEST IN OTHER THREAD
		Thread thread = new Thread(new RunnableTest(testNG));
		workers.put(threadIndex, thread);
		thread.start();
		threadIndex++;
		
		
		return "{\"index\" : \""+(testListenenerIndex++)+"\"}";
	}

	@RequestMapping(value = "/test/{testName}/{index}" ,method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getTest(@PathVariable("testName") String testName, @PathVariable("index") Integer index) throws ConfigurationException{
		tla.get(index).getPassedTests().forEach(failTest -> System.out.println(failTest));
		tla.get(index).getSkippedTests().forEach(failTest -> System.out.println(failTest));
		tla.get(index).getFailedTests().forEach(failTest -> System.out.println(failTest));
		tla.get(index).getTestContexts().forEach(failTest -> System.out.println(failTest));
		return null;
	}
	
	@RequestMapping(value = "/start" ,method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String startCreatePerson(@RequestBody CreatePersonForm form) throws ConfigurationException{
		Integer newPersonID = createPersonFormDao.saveCreatePersonForm(form);
		
		List<XmlSuite> suites = getXmlTestSuiteForExternalUser("CreatePerson");
		HashMap<String,String> params = new HashMap<String, String>();
		params.put("personID", String.valueOf(newPersonID));
		
		for (XmlSuite suite : suites){
			for(XmlTest test : suite.getTests()){
				test.setParameters(params);
			}
		}
		
		insynctive.utils.TestResults.resetResults();
		TestListenerAdapter testListenerAdapter = new TestListenerAdapter();
		tla.put(testListenenerIndex, testListenerAdapter);
		
		TestNG testNG = new TestNG();
		testNG.setXmlSuites(suites);
		testNG.setPreserveOrder(true);
		testNG.addListener(testListenerAdapter);
		
		//START TEST IN OTHER THREAD
		Thread thread = new Thread(new RunnableTest(testNG));
		workers.put(threadIndex, thread);
		thread.start();
		threadIndex++;
		
		return "{\"index\" : \""+(testListenenerIndex++)+"\"}";
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
		List<ITestResult> passedTests = tla.get(tlaIndex).getPassedTests();
		for(ITestResult test : passedTests){
			if(test.getMethod().getMethodName().equals("createPersonTest")){
				return "{\"status\": true}";
			}
		}
		List<ITestResult> failedTests = tla.get(tlaIndex).getFailedTests();
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
}
