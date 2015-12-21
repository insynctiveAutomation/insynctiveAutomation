package insynctive.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.xml.sax.SAXException;

import insynctive.dao.AccountDao;
import insynctive.dao.test.TestDao;
import insynctive.dao.test.TestSuiteRunDao;
import insynctive.model.Account;
import insynctive.model.ParamObject;
import insynctive.model.test.Test;
import insynctive.model.test.TestSuite;
import insynctive.model.test.run.TestPlanRun;
import insynctive.model.test.run.TestRun;
import insynctive.model.test.run.TestSuiteRun;
import insynctive.runnable.RunnableTest;

@Transactional
public class TestWebRunner {

	private final ServletContext servletContext;
	private final TestSuiteRunDao testSuiteRunDao;
	private final AccountDao accDao;
	private final TestDao testDao;

	public TestWebRunner(ServletContext servletContext, TestSuiteRunDao testSuiteRunDao, AccountDao accDao, TestDao testDao) {
		this.servletContext = servletContext;
		this.testSuiteRunDao = testSuiteRunDao;
		this.accDao = accDao;
		this.testDao = testDao;
	}
	
	public void runTest(TestPlanRun tpRun, Account acc) {

		for(TestSuiteRun tsRun : tpRun.testSuiteRuns){
			runTest(tsRun, acc);
		}
	}
	
	public Integer runTest(TestSuiteRun form, Account acc) {
		return runTest(form, acc, new Thread[]{});
	}

	public Integer runTest(TestSuiteRun form, Account acc, Thread threadToJoin) {
		return runTest(form, acc, new Thread[]{threadToJoin});
	}
	
	public Integer runTest(TestSuiteRun tsRun, Account acc, Thread[] threadToJoin) {
		XmlSuite suite = createXmlTest(tsRun);
		List<XmlSuite> suites = new ArrayList<>();
		suites.add(suite);
		
		//Add Parameters to Test
		for(TestRun testRun : tsRun.getTestsRuns()){
			for(XmlTest xmlTest : suite.getTests()){
				for(XmlClass classes : xmlTest.getClasses()){
					for(XmlInclude methodsInXML: classes.getIncludedMethods()){
						if(methodsInXML.getName().equals(testRun.getTestName())){
							methodsInXML.addParameter("TestID", testRun.getTestRunID().toString());
						}
					}
				}
			}
		}
		
		//Initialize tests.
		Map<String, String> parameters = new HashMap<>();
		parameters.put("accountID", String.valueOf(acc.getAccountID()));
		parameters.put("runID", acc.getRunIDString());
		parameters.put("bowser", tsRun.getBrowser());
		parameters.put("testID", tsRun.getTestSuiteRunID().toString());
		parameters.put("testName", tsRun.getName());
		parameters.put("environment", tsRun.getEnvironment());
		
		
		//Add to Test Suite
		suite.setParameters(parameters);
		
		//Create Test listener and add it.
		TestListenerAdapter testListenerAdapter = new TestListenerAdapter();
		TestResults.addListener(tsRun.getTestSuiteRunID(), testListenerAdapter);
		
		//Make test and run the thread.
		TestNG testNG = new TestNG();
		testNG.setXmlSuites(suites);
		testNG.setPreserveOrder(true);
		testNG.addListener(testListenerAdapter);
		
		//START TEST IN OTHER THREAD
		Thread thread = new Thread(new RunnableTest(testNG, tsRun, testListenerAdapter, testSuiteRunDao, testDao, threadToJoin));
		TestResults.addWorker(tsRun.getTestSuiteRunID(), thread);
		thread.start();
		
		return tsRun.getTestSuiteRunID();
	}
	
	public TestSuite getTestSuiteByXML(ParamObject paramObject, String testSuiteName) throws Exception {
		TestSuite testSuite = new TestSuite();
		testSuite.setTestSuiteName(testSuiteName);
		List<XmlSuite> suites = getXmlTestSuiteForUI(testSuiteName);
		List<Test> listIncMethod = new ArrayList<Test>();
		for (XmlSuite suite : suites) {
			for(XmlTest test : suite.getTests()){
				for(XmlClass clazz : test.getClasses()){
					for(XmlInclude incMethod : clazz.getIncludedMethods()){
							Test newTest= new Test(incMethod.getName());
							newTest.setClassName(clazz.getName());
							newTest.setParamObject(ParamObject.getNewWithOutIDs(paramObject));
							listIncMethod.add(newTest);
					}
				}
			}
		}
		testSuite.setTests(new HashSet<Test>(listIncMethod));
		
		return testSuite;
	}

	public TestSuiteRun getTestSuiteRun(TestSuite testSuite, String environment, String browser) throws Exception {
		TestSuiteRun testSuiteRun = testSuite.toTestSuiteRun();
		testSuiteRun.setEnvironment(environment);
		testSuiteRun.setBrowser(browser);
		
		return testSuiteRun;
	}
	
	public TestSuiteRun getTestSuiteRunByXML(ParamObject paramObject, String testSuiteName, String environment, String browser) throws Exception {
		TestSuite testSuite = getTestSuiteByXML(paramObject, testSuiteName);
		TestSuiteRun testSuiteRun = getTestSuiteRun(testSuite, environment, browser);
		
		return testSuiteRun;
	}
	
	public String getClassnameFromXMLTestSuite(String xmlName) {
		List<XmlSuite> suites = getXmlTestSuiteForUI(xmlName);
		for(XmlTest xmlTest : suites.get(0).getTests()){
			for(XmlClass classes : xmlTest.getClasses()){
				return classes.getName();
			}
		}
		return null;
	}
	
	/*private Methods*/
	public List<XmlSuite> getXmlTestSuiteForUI(String xmlName){
		return getXmlTestSuite(xmlName, "/WEB-INF/testsSuits/");
	}
	
	public List<XmlSuite> getXmlTestSuiteForExternalUser(String xmlName){
		return getXmlTestSuite(xmlName, "/WEB-INF/externalTest/");
	}
	
	public List<XmlSuite> getXmlTestSuite(String xmlName, String path) {
		String xmlFileName = new File( servletContext.getRealPath(path+xmlName+".xml")).getPath();
		
		List<XmlSuite> suite = getSuite(xmlFileName);
		
		return suite;
	}

	public List<XmlSuite> getSuite(String xmlFileName) {
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
	
	public List<String> getTestSuitesForRunUI() throws MalformedURLException, URISyntaxException{
		return getTestSuites("/WEB-INF/testsSuits/");
	}
	
	public List<String> getTestSuitesForExternalClient() throws MalformedURLException, URISyntaxException{
		return getTestSuites("/WEB-INF/externalTest/");
	}
	
	public List<String> getTestSuites(String path) throws MalformedURLException, URISyntaxException{
		List<String> results = new ArrayList<String>();

		File[] files = new File( servletContext.getRealPath(path)).listFiles();
		
		for (File file : files) {
		    if (file.isFile()) {
		        results.add(file.getName());
		    }
		}
		return results;
	}

	private XmlSuite createXmlTest(TestSuiteRun tsRun) {
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		List<XmlClass> classes = new ArrayList<XmlClass>();
		
		XmlSuite suite = new XmlSuite();
		suite.setName(tsRun.getName());
		
		XmlTest test = new XmlTest(suite);
		test.setName("Test Name");
		//Test class to be included for test execution
		XmlClass clz = new XmlClass(new ArrayList<>(tsRun.getTestsRuns()).get(0).getClassName());
		
		//Test methods to be included
		List<XmlInclude> includes = new ArrayList<XmlInclude>();
		for(TestRun testRun : tsRun.getTestsRuns()){
			XmlInclude method = new XmlInclude(testRun.getTestName());
			includes.add(method);
		}
		
		//Setting the included methods for the class
		clz.setIncludedMethods(includes);
		
		classes.add(clz);
		test.setXmlClasses(classes);

		return suite;
	}
}
