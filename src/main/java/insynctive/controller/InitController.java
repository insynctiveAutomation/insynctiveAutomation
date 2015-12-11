package insynctive.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import insynctive.dao.AccountDao;
import insynctive.dao.CreatePersonFormDao;
import insynctive.dao.CrossBrowserAccountDao;
import insynctive.dao.InsynctivePropertyDao;
import insynctive.dao.test.TestDao;
import insynctive.dao.test.TestPlanDao;
import insynctive.dao.test.TestRunDao;
import insynctive.dao.test.TestSuiteDao;
import insynctive.dao.test.TestSuiteRunDao;
import insynctive.exception.ConfigurationException;
import insynctive.model.ParamObject;
import insynctive.model.test.Test;
import insynctive.model.test.TestPlan;
import insynctive.model.test.TestSuite;
import insynctive.model.test.TestSuiteRunner;
import insynctive.model.test.run.TestPlanRun;
import insynctive.utils.HibernateUtil;
import insynctive.utils.TestWebRunner;
import insynctive.utils.data.TestEnvironment;

@Controller
@Transactional
public class InitController {

	TestPlanDao testPlanDao;
	TestDao testDao;
	TestSuiteDao testSuiteDao;
	private AccountDao accDao;
	private TestSuiteRunDao testSuiteRunDao;
	private ServletContext servletContext;
	
	@Inject
	public InitController(InsynctivePropertyDao propertyDao, AccountDao accDao,
			CreatePersonFormDao createPersonFormDao, CrossBrowserAccountDao crossDao, TestPlanDao testPlanDao,
			TestSuiteDao testSuiteDao, TestSuiteRunDao testSuiteRunDao, TestDao testDao, TestRunDao testRunDao,
			ServletContext servletContext) throws Exception {
		
		HibernateUtil.init(propertyDao, accDao, createPersonFormDao, crossDao, testPlanDao, testSuiteDao, testSuiteRunDao, testDao, testRunDao);
		
		this.testPlanDao = testPlanDao;
		this.testDao = testDao;
		this.testSuiteDao = testSuiteDao;
		this.accDao = accDao;
		this.testSuiteRunDao = testSuiteRunDao;
		this.servletContext = servletContext;
	}
	
	@RequestMapping(value = "/init/nightly", method = RequestMethod.GET)
	@ResponseBody
	public String initNightly() throws Exception{
		
		ParamObject paramObject = new ParamObject();
		paramObject.setLoginUsername("evaleiras@insynctive.com");
		paramObject.setLoginPassword("password");
		
		Test newTest = new Test();
		newTest.setClassName("insynctive.tests.PersonFileTest");
		newTest.setTestName("loginTest");
		newTest.setParamObject(paramObject);
		
		TestSuite newTestSuite = new TestSuite();
		newTestSuite.setTestSuiteName("Login Test Suite");
		newTestSuite.addTest(newTest);
		
		TestPlan newTestPlan = new TestPlan();
		newTestPlan.setName("Nightly");
		newTestPlan.addTestSuiteRunner(new TestSuiteRunner(newTestSuite, "automationqa", TestEnvironment.CHROME));
		
		Integer id = testPlanDao.save(newTestPlan);
		
		TestPlan testByID = testPlanDao.getTestPlanByID(id);
		
		return "{\"status\" : 200, \"Test Plan ID\" : "+id+"}";
	}
	
	@RequestMapping(value = "/init/run/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String runTP(@PathVariable("id") Integer tpID) throws Exception {
		
		TestPlan testPlan = testPlanDao.getTestPlanByID(tpID);
		TestPlanRun tpRun = testPlan.run();
		testPlanDao.save(tpRun);
		
		return "{\"status\" : 200}";
	}
	
	@RequestMapping(value = "/init/xml", method = RequestMethod.GET)
	@ResponseBody
	public String initXMLS() throws Exception {
		TestWebRunner runner = new TestWebRunner(servletContext, testSuiteRunDao, accDao, testDao);
		List<String> testSuitesForRunUI = runner.getTestSuitesForRunUI();
		
		for(String xml : testSuitesForRunUI){
			String nameOfTS = xml.split("\\.")[0];
			List<XmlSuite> xmlTestSuiteForUI = runner.getXmlTestSuiteForUI(nameOfTS);
			for(XmlSuite suite : xmlTestSuiteForUI){
				for(XmlTest xmlTest : suite.getTests()){
					for(XmlClass clazz : xmlTest.getClasses()){
						TestSuite newTS = new TestSuite(clazz, suite.getName()); 
						testSuiteDao.save(newTS);
					}
				}
			}
		}
		
		return "{\"status\" : 200}";
	}
	
	
	/** Move to Tests Controller*/
	/** GET AND SAVE TEST **/
	@RequestMapping(value = "/test" ,method = RequestMethod.POST)
	@ResponseBody
	public String savetet(@RequestBody Test test) throws Exception{
		testDao.saveOrUpdate(test);
		return "{\"statut\" : 200}";
	}
	
	@RequestMapping(value = "/test/{testID}" ,method = RequestMethod.GET)
	@ResponseBody
	public Test getTest(@PathVariable("testID") Integer testID) throws Exception{
		Test test = testDao.getTestByID(testID);
		return test;
	}
	
	/** GET AND SAVE TEST SUITE **/
	@RequestMapping(value = "/testSuite" ,method = RequestMethod.POST)
	@ResponseBody
	public String getTestSuite(@RequestBody TestSuite testSuite) throws Exception{
		testSuiteDao.saveOrUpdate(testSuite);
		return "{\"statut\" : 200}";
	}
	
	@RequestMapping(value = "/testSuite/{testSuiteID}" ,method = RequestMethod.GET)
	@ResponseBody
	public TestSuite getTestSuite(@PathVariable Integer testSuiteID) throws ConfigurationException {
		TestSuite testByID = testSuiteDao.getTestByID(testSuiteID);
		return testByID;
	}

	/** GET AND SAVE TEST PLAN **/
	@RequestMapping(value = "/testPlan" ,method = RequestMethod.POST)
	@ResponseBody
	public String getTestPlan(@RequestBody TestPlan testPlan) throws Exception{
		testPlanDao.saveOrUpdate(testPlan);
		return "{\"statut\" : 200}";
	}
	
	@RequestMapping(value = "/testPlan/{testPlanID}" ,method = RequestMethod.GET)
	@ResponseBody
	public TestPlan getTestPlan(@PathVariable Integer testPlanID) throws ConfigurationException {
		TestPlan testPlanByID = testPlanDao.getTestPlanByID(testPlanID);
		return testPlanByID;
	}
	
	
}
