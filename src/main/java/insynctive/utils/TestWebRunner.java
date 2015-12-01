package insynctive.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.xml.sax.SAXException;

import com.beust.jcommander.internal.Nullable;

import insynctive.dao.AccountDao;
import insynctive.dao.TestDao;
import insynctive.dao.TestSuiteDao;
import insynctive.model.Account;
import insynctive.model.InsynctiveProperty;
import insynctive.model.ParamObject;
import insynctive.model.Test;
import insynctive.model.TestSuite;
import insynctive.runnable.RunnableTest;

public class TestWebRunner {

	private final ServletContext servletContext;
	private final TestSuiteDao testSuiteDao;
	private final AccountDao accDao;
	private final TestDao testDao;

	public TestWebRunner(ServletContext servletContext, TestSuiteDao testSuiteDao, AccountDao accDao, TestDao testDao) {
		this.servletContext = servletContext;
		this.testSuiteDao = testSuiteDao;
		this.accDao = accDao;
		this.testDao = testDao;
	}
	
	public Integer runTest(TestSuite form, Account acc) {
		return runTest(form, acc, new Thread[]{});
	}

	public Integer runTest(TestSuite form, Account acc, Thread threadToJoin) {
		return runTest(form, acc, new Thread[]{threadToJoin});
	}
	
	public Integer runTest(TestSuite form, Account acc, Thread[] threadToJoin) {
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
		parameters.put("environment", form.getEnvironment());
		
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
		Thread thread = new Thread(new RunnableTest(testNG, testSuite, testListenerAdapter, testSuiteDao, testDao, threadToJoin));
		TestResults.addWorker(testSuite.getTestSuiteID(), thread);
		thread.start();
		
		return testSuite.getTestSuiteID();
	}
	


	public TestSuite createTestSuite(ParamObject paramObject, String testSuiteName, String environment, String browser) throws Exception {
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
							newTest.setParamObject(ParamObject.getNewWithOutIDs(paramObject));
							listIncMethod.add(newTest);
					}
				}
			}
		}
		testSuite.setTests(listIncMethod);
		
		return testSuite;
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
	
}
