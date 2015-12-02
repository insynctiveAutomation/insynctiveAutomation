package insynctive.controller;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import insynctive.dao.AccountDao;
import insynctive.dao.InsynctivePropertyDao;
import insynctive.dao.test.TestDao;
import insynctive.dao.test.TestPlanDao;
import insynctive.dao.test.TestSuiteDao;
import insynctive.dao.test.TestSuiteRunDao;
import insynctive.model.Account;
import insynctive.model.ParamObject;
import insynctive.model.test.TestPlan;
import insynctive.model.test.TestSuite;
import insynctive.model.test.run.TestPlanRun;
import insynctive.model.test.run.TestSuiteRun;
import insynctive.utils.TestResults;
import insynctive.utils.TestWebRunner;

@Controller
@Transactional
public class NightlyController {
	
	private final int NIGHTLY_ACCOUNT_ID = 6;
	final String NIGHTLY_DEFAULT_ENVIRONMENT = "AutomationQA";
	
	//DB Connections.
	private final InsynctivePropertyDao propertyDao;
	private final AccountDao accDao;
	private final TestSuiteRunDao testSuiteRunDao;
	private final TestSuiteDao testSuiteDao;
	private final TestPlanDao testPlanDao;
	
	//Servlet Context Helper
	private final ServletContext servletContext;

	//Test Runner
	private final TestWebRunner testRunner;

	@Inject
	public NightlyController(TestDao testDao, InsynctivePropertyDao propertyDao, ServletContext servletContext, AccountDao accDao, TestSuiteRunDao testSuiteRunDao, TestSuiteDao testSuiteDao, TestPlanDao tpDao) {
		this.servletContext = servletContext;
		this.propertyDao = propertyDao;
		this.accDao = accDao;
		this.testSuiteRunDao = testSuiteRunDao;
		this.testSuiteDao = testSuiteDao;
		this.testPlanDao = tpDao;
		this.testRunner = new TestWebRunner(servletContext, testSuiteRunDao, accDao, testDao);
	}
	
	@RequestMapping(value = "/run/tp/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String runTestPlanByID(@PathVariable("id") Integer tpID) throws Exception{
		Account nightlyAcc = accDao.getAccountByID(NIGHTLY_ACCOUNT_ID);
		
		TestPlan testPlan = testPlanDao.getTestPlanByID(tpID);
		TestPlanRun tpRun = testPlan.run();
		testPlanDao.save(tpRun);
		
		testRunner.runTest(tpRun, nightlyAcc);
		
		return "{\"status\" : 200, \"user\" : \""+nightlyAcc.getUsername()+"}";
	}
	
//	@RequestMapping(value = "/run/ts/{id}", method = RequestMethod.GET)
//	@ResponseBody
//	public String runTestSuite(@PathVariable("id") Integer tpID) throws Exception{
//		Account nightlyAcc = accDao.getAccountByID(NIGHTLY_ACCOUNT_ID);
//		
//		TestSuite testPlan = testSuiteDao.getTestByID(tpID);
//		TestPlanRun tpRun = testPlan.run(); TODO
//		testPlanDao.save(tpRun);
//		
//		return "{\"index\" : \""+(testRunner.runTest(tpRun, nightlyAcc))+"\"}";
//	}

	@RequestMapping(value = "/nightly", method = RequestMethod.GET)
	@ResponseBody
	public String runNightly() throws Exception {
		Account nightlyAcc = accDao.getAccountByID(NIGHTLY_ACCOUNT_ID);
		TestPlan testPlan = testPlanDao.getTestPlanByName("Nightly");
		TestPlanRun tpRun = testPlan.run();
		testPlanDao.save(tpRun);
		
		testRunner.runTest(tpRun, nightlyAcc);
		return "{\"status\" : 200, \"user\" : \""+nightlyAcc.getUsername()+"}";
	}
	
	@RequestMapping(value = "/nightly-microsoft", method = RequestMethod.POST)
	@ResponseBody
	public String runNightlyMicrosoft() throws Exception {
		Account nightlyAcc = accDao.getAccountByID(NIGHTLY_ACCOUNT_ID);
		return "{\"status\" : 200, \"user\" : \""+nightlyAcc.getUsername()+"}";
	}
	
	@RequestMapping(value = "/run_ts/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String runID(@PathVariable("id") Integer id) throws Exception{
		Account nightlyAcc = accDao.getAccountByID(NIGHTLY_ACCOUNT_ID);
		TestSuite ts = testSuiteDao.getTestByID(id);
		TestSuiteRun tsRun = testRunner.getTestSuiteRun(ts, "automationQA", "FIREFOX");

		testRunner.runTest(tsRun, nightlyAcc);
		
		return "{\"status\" : 200}";
	}

}
