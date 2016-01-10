package insynctive.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;

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
import insynctive.dao.test.TestPlanDao;
import insynctive.dao.test.TestPlanRunDao;
import insynctive.dao.test.TestRunDao;
import insynctive.dao.test.TestSuiteDao;
import insynctive.dao.test.TestSuiteRunDao;
import insynctive.model.Account;
import insynctive.model.ParamObject;
import insynctive.model.test.Test;
import insynctive.model.test.TestPlan;
import insynctive.model.test.TestSuite;
import insynctive.model.test.run.TestPlanRun;
import insynctive.model.test.run.TestRun;
import insynctive.model.test.run.TestSuiteRun;
import insynctive.runnable.RunnableTest;

@Transactional
public class TestWebRunner {

	private final AccountDao accDao;

	private final TestDao testDao;
	private final TestSuiteDao testSuiteDao;
	private final TestPlanDao testPlanDao;
	
	private final TestRunDao testRunDao;
	private final TestSuiteRunDao testSuiteRunDao;
	private final TestPlanRunDao testPlanRunDao;

	public TestWebRunner() {
		this.accDao = HibernateUtil.accDao;
		
		this.testDao = HibernateUtil.testDao;
		this.testSuiteDao = HibernateUtil.testSuiteDao;
		this.testPlanDao = HibernateUtil.testPlanDao;
		
		this.testRunDao = HibernateUtil.testRunDao;
		this.testSuiteRunDao = HibernateUtil.testSuiteRunDao;
		this.testPlanRunDao = HibernateUtil.testPlanRunDao;
	}
	
	public void runTest(TestPlan tp, Boolean isNotification) throws IllegalArgumentException, IllegalAccessException, Exception {
		TestPlanRun tpRun = tp.run();
		testPlanRunDao.save(tpRun);
		runTest(tpRun, isNotification);
	}
	
	public void runTest(TestPlan tp, Boolean isNotification, Boolean isRemote) throws IllegalArgumentException, IllegalAccessException, Exception {
		TestPlanRun tpRun = tp.run();
		testPlanRunDao.save(tpRun);
		runTest(tpRun, isNotification, isRemote);
	}
	
	public Integer runTest(TestSuite ts, Boolean isNotification) throws IllegalArgumentException, IllegalAccessException, Exception {
		TestSuiteRun tsRun = ts.run();
		testSuiteRunDao.save(tsRun);
		return runTest(tsRun, isNotification, tsRun.isRemote());
	}
	
	public Integer runTest(TestSuite ts, Boolean isNotification, Boolean isRemote) throws IllegalArgumentException, IllegalAccessException, Exception {
		TestSuiteRun tsRun = ts.run();
		testSuiteRunDao.save(tsRun);
		return runTest(tsRun, isNotification, isRemote);
	}
	
	public void runTest(TestPlanRun tpRun, Boolean isNotification) {
		List<TestSuiteRun> tsNoDepends = tpRun.getTestSuiteRuns().stream().filter(tsrun -> !tsrun.isDependingOnAnotherTS()).collect(Collectors.toList());
		List<TestSuiteRun> tsDepends = tpRun.getTestSuiteRuns().stream().filter(tsrun -> tsrun.isDependingOnAnotherTS()).collect(Collectors.toList());
			
		for(TestSuiteRun tsRun : tsNoDepends){
			runTest(tsRun, isNotification, tsRun.isRemote());
		}
		
		for(TestSuiteRun tsRun : tsDepends){
			runTest(tsRun, isNotification, tsRun.isRemote());
		}
	}
	
	public void runTest(TestPlanRun tpRun, Boolean isNotification, Boolean isRemote) {
		List<TestSuiteRun> tsNoDepends = tpRun.getTestSuiteRuns().stream().filter(tsrun -> !tsrun.isDependingOnAnotherTS()).collect(Collectors.toList());
		List<TestSuiteRun> tsDepends = tpRun.getTestSuiteRuns().stream().filter(tsrun -> tsrun.isDependingOnAnotherTS()).collect(Collectors.toList());
		
		for(TestSuiteRun tsRun : tsNoDepends){
			runTest(tsRun, isNotification, isRemote);
		}
		
		for(TestSuiteRun tsRun : tsDepends){
			runTest(tsRun, isNotification, isRemote);
		}
	}
	
	public Integer runTest(TestSuiteRun tsRun, Boolean isNotification) {
		return runTest(tsRun, isNotification, tsRun.isRemote());
	}
	
	public Integer runTest(TestSuiteRun tsRun, Boolean isNotification, Boolean isRemote) {
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
//		"environment", "browser", "isRemote", "isNotification", "testSuiteID", "testName"
		Map<String, String> parameters = new HashMap<>();
		parameters.put("environment", tsRun.getEnvironment());
		parameters.put("browser", tsRun.getBrowser());
		parameters.put("isRemote", String.valueOf(isRemote));
		parameters.put("isNotification", String.valueOf(isNotification));
		parameters.put("testSuiteID", tsRun.getTestSuiteRunID().toString());
		parameters.put("testName", tsRun.getName());

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
		Thread threadToJoin = TestResults.workers.get(tsRun.getDependsRunID());
		Thread thread = new Thread(new RunnableTest(testNG, tsRun, testListenerAdapter, testSuiteRunDao, testDao, (threadToJoin != null ? new Thread[]{threadToJoin} : new Thread[]{})));
		TestResults.addWorker(tsRun.getTestSuiteRunID(), thread);
		thread.start();
		
		return tsRun.getTestSuiteRunID();
	}

	public TestSuiteRun getTestSuiteRun(TestSuite testSuite, String environment, String browser) throws Exception {
		TestSuiteRun testSuiteRun = testSuite.toTestSuiteRun();
		testSuiteRun.setEnvironment(environment);
		testSuiteRun.setBrowser(browser);
		
		return testSuiteRun;
	}
	
	public TestSuiteRun getTestSuiteRun(TestSuite testSuite, String environment, String browser, Account account) throws Exception {
		TestSuiteRun testSuiteRun = getTestSuiteRun(testSuite, environment, browser);
		testSuiteRun.setRemote(account.getAccountProperty().isRemote());
		testSuiteRun.setTester(account.getUsername());
		
		return testSuiteRun;
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

	private XmlSuite createXmlTest(TestSuiteRun tsRun) {
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		List<XmlClass> classes = new ArrayList<XmlClass>();
		Integer index = 1;
		
		XmlSuite suite = new XmlSuite();
		suite.setName(tsRun.getName());
		
		XmlTest test = new XmlTest(suite);
		test.setName("Test Name");
		//Test class to be included for test execution
		XmlClass clz = new XmlClass(new ArrayList<>(tsRun.getTestsRuns()).get(0).getClassName());
		
		//Test methods to be included
		List<XmlInclude> includes = new ArrayList<XmlInclude>();
		for(TestRun testRun : tsRun.getTestsRuns()){
			XmlInclude method = new XmlInclude(testRun.getTestName(), index++);
			includes.add(method);
		}
		
		//Setting the included methods for the class
		clz.setIncludedMethods(includes);
		
		classes.add(clz);
		test.setXmlClasses(classes);

		return suite;
	}
	
	public String runExternalCreatePerson(TestSuiteRun tsRun, Integer newPersonID){
		XmlSuite suite = createXmlTest(tsRun);
		List<XmlSuite> suites = new ArrayList<>();
		suites.add(suite);
		
		//Add Parameters to Test
		for(TestRun testRun : tsRun.getTestsRuns()){
			for(XmlTest xmlTest : suite.getTests()){
				for(XmlClass classes : xmlTest.getClasses()){
					for(XmlInclude methodsInXML: classes.getIncludedMethods()){
						if(methodsInXML.getName().equals(testRun.getTestName())){
							methodsInXML.addParameter("personID", String.valueOf(newPersonID));
						}
					}
				}
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
}
