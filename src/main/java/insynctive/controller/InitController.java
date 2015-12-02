package insynctive.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
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
import insynctive.model.ParamObject;
import insynctive.model.test.Test;
import insynctive.model.test.TestPlan;
import insynctive.model.test.TestSuite;
import insynctive.model.test.TestSuiteRunner;
import insynctive.model.test.run.TestPlanRun;
import insynctive.model.test.run.TestSuiteRun;
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
	
	
}
