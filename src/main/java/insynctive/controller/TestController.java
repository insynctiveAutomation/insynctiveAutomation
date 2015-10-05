package insynctive.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.context.annotation.Scope;
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

import insynctive.dao.AccountDao;
import insynctive.dao.CreatePersonFormDao;
import insynctive.dao.CrossBrowserAccountDao;
import insynctive.dao.InsynctivePropertyDao;
import insynctive.exception.ConfigurationException;
import insynctive.model.Account;
import insynctive.model.CreatePersonForm;
import insynctive.model.InsynctiveProperty;
import insynctive.results.Result;
import insynctive.results.TestResultsTestNG;
import insynctive.results.TestSuite;
import insynctive.runnable.RunnableTest;
import insynctive.utils.NightlyRegressions;

@Controller
@Scope("session")
public class TestController {
 
	private final ServletContext servletContext;
	private final InsynctivePropertyDao propertyDao;
	private final AccountDao accDao;
	private final CreatePersonFormDao createPersonFormDao;
	private final CrossBrowserAccountDao crossDao;
	private int accID = 1;//NOW IM USING THIS BECAUSE WE DONT HAVE LOGIN
	private Account account;
	private Thread[] workers;
	
	@Inject
	public TestController(InsynctivePropertyDao propertyDao, ServletContext servletContext, AccountDao accDao, CrossBrowserAccountDao crossDao, CreatePersonFormDao createPersonFormDao) {
		this.servletContext = servletContext;
		this.propertyDao = propertyDao;
		this.accDao = accDao;
		this.crossDao = crossDao;
		this.createPersonFormDao = createPersonFormDao;
	}
	
	TestListenerAdapter tla = new TestListenerAdapter();

	@RequestMapping(value = "/" ,method = RequestMethod.GET)
	public ModelAndView root(HttpSession session){
		ModelAndView model = new ModelAndView();
		model.setViewName("test");
		account = accDao.getAccountByID(accID);
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

	@RequestMapping(value = "/accountProperties" ,method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public InsynctiveProperty getAccountProperties() throws ConfigurationException {
		return propertyDao.getPropertybyID(accID);
	}

	@RequestMapping(value = "/clearTest" ,method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public void clearTestResult() throws ConfigurationException {
		tla = new TestListenerAdapter();
	}

	@RequestMapping(value = "/video" ,method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getVideo() throws InterruptedException, ConfigurationException{
		if (account.getAccountProperty().isRemote()) {
			int times = 1;
			int sleep = 2000;
			
			while (times <= 30) {
				String videoLink = insynctive.utils.TestResults.video;
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
		List<String> testsSuites = getTestSuites();
		return testsSuites;
	}
	
	@RequestMapping(value = "/environments" ,method = RequestMethod.GET)
	@ResponseBody
	public List<String> getEnvironments(){
		List<String> environments = new ArrayList<String>();
		
		return environments;
	}
	
	@RequestMapping(value = "/status" ,method = RequestMethod.GET)
	@ResponseBody
	public TestResultsTestNG getStatus(){
		List<Result> resultsAux = new ArrayList<Result>();
		TestResultsTestNG testResults = new TestResultsTestNG();
		
		for(ITestResult testResult : tla.getPassedTests()){
			resultsAux.add(new Result(testResult.getName(),"SUCCESS"));
		}
		testResults.setPassedTests(resultsAux);
		resultsAux = new ArrayList<Result>();
		
		for(ITestResult testResult : tla.getFailedTests()){
			resultsAux.add(new Result(testResult.getName(),"FAILED"));
		}
		testResults.setFailedTests(resultsAux);
		resultsAux = new ArrayList<Result>();
		
		for(ITestResult testResult : tla.getSkippedTests()){
			resultsAux.add(new Result(testResult.getName(),"SKIPPED"));
		}
		testResults.setSkipedTests(resultsAux);
		resultsAux = new ArrayList<Result>();
		
		return testResults;
	} 
	
	@RequestMapping(value = "/get/{xmlName}" ,method = RequestMethod.GET)
	@ResponseBody
	public TestSuite getTestsRuns(@PathVariable("xmlName") String xmlName) {
		TestSuite testSuite = null;
		try{
			List<XmlSuite> suite = getXmlTestSuite(xmlName);
			testSuite = new TestSuite();
			
			for(XmlTest test : suite.get(0).getTests()){
				testSuite.setTestName(test.getName());
				for(XmlClass classes : test.getClasses()){
					testSuite.setClassName(classes.getName());
					for(XmlInclude includeMethods: classes.getIncludedMethods()){
						testSuite.addMethod(new Result(includeMethods.getName(), "-"));
					}
				}
			}
		} catch(Exception ex) {
			
		}
		return testSuite;
	}
	
	@RequestMapping(value = "/test/{xmlName}/{environment}" ,method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String runTest(@PathVariable("xmlName") String xmlName, @PathVariable("environment") String environment) throws ConfigurationException{
		account = accDao.incrementRunIDAndGetAcc(accID);
		InsynctiveProperty properties = account.getAccountProperty();
		if(!properties.getEnvironment().equals(environment)){
			properties.setEnvironment(environment); 
			propertyDao.update(properties);
		}
		
		tla = new TestListenerAdapter();
		insynctive.utils.TestResults.resetResults();
		
		List<XmlSuite> suites = getXmlTestSuite(xmlName);

		TestNG testNG = new TestNG();
		
		testNG.setXmlSuites(suites);
		testNG.setPreserveOrder(true);
		testNG.addListener(tla);
		//START TEST IN OTHER THREAD
		workers = new Thread[1];
		workers[0] = new Thread(new RunnableTest(testNG));
		workers[0].start();
		
		return "The Test Starts!";
	}
	
	@RequestMapping(value = "/nt" ,method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String runTest(@RequestBody NightlyRegressions nightlyData) throws ConfigurationException{
		String data = nightlyData.getText().split("[")[1].split("]")[0];
		String environment = data.split(",")[0];
		String xmlName = data.split(",")[1];
		
		InsynctiveProperty properties = account.getAccountProperty();
		properties.setEnvironment(environment); 
		
		tla = new TestListenerAdapter();
		insynctive.utils.TestResults.resetResults();
		
		List<XmlSuite> suites = getXmlTestSuite(xmlName);

		TestNG testNG = new TestNG();
		
		testNG.setXmlSuites(suites);
		testNG.setPreserveOrder(true);
		testNG.addListener(tla);
		testNG.run();
		
		return "Finish!";
	}
	
	@RequestMapping(value = "/test/{testName}" ,method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String getTest(@PathVariable("testName") String testName) throws ConfigurationException{
		tla.getPassedTests().forEach(failTest -> System.out.println(failTest));
		tla.getSkippedTests().forEach(failTest -> System.out.println(failTest));
		tla.getFailedTests().forEach(failTest -> System.out.println(failTest));
		tla.getTestContexts().forEach(failTest -> System.out.println(failTest));
		return null;
	}
	
	@RequestMapping(value = "/start" ,method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String startCreatePerson(@RequestBody CreatePersonForm form) throws ConfigurationException{
		form.setEnvironment("Alpha2");
		Integer newPersonID = createPersonFormDao.saveCreatePersonForm(form);
		List<XmlSuite> suites = getXmlTestSuite("CreatePerson");

		HashMap<String,String> params = new HashMap<String, String>();
		params.put("personID", String.valueOf(newPersonID));
		
		for (XmlSuite suite : suites){
			for(XmlTest test : suite.getTests()){
				test.setParameters(params);
			}
		}
		
		insynctive.utils.TestResults.resetResults();
		tla = new TestListenerAdapter();
		TestNG testNG = new TestNG();
		testNG.setXmlSuites(suites);
		testNG.setPreserveOrder(true);
		testNG.addListener(tla);
		
		//START TEST IN OTHER THREAD
		workers = new Thread[1];
		workers[0] = new Thread(new RunnableTest(testNG));
		workers[0].start();
		
		return "Check your inbox "+form.getEmail()+" in minutes and start testing..";
	}
	
	@RequestMapping(value = "/saveAccountConfig" ,method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String saveAccountConfig(@RequestBody InsynctiveProperty properties) throws ConfigurationException{
		propertyDao.update(properties);
		return "Done!";
	}
	
	/*Private Methods*/
	private List<XmlSuite> getXmlTestSuite(String xmlName) {
		String xmlFileName = new File( servletContext.getRealPath("/WEB-INF/testsSuits/"+xmlName+".xml")).getPath();
		
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
	
	private List<String> getTestSuites() throws MalformedURLException, URISyntaxException{
		List<String> results = new ArrayList<String>();

		File[] files = new File( servletContext.getRealPath("/WEB-INF/testsSuits/")).listFiles();
		
		for (File file : files) {
		    if (file.isFile()) {
		        results.add(file.getName());
		    }
		}
		return results;
	}
}
