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
import insynctive.dao.CreatePersonFormDao;
import insynctive.dao.InsynctivePropertyDao;
import insynctive.dao.test.TestDao;
import insynctive.dao.test.TestPlanDao;
import insynctive.dao.test.TestPlanRunDao;
import insynctive.dao.test.TestRunDao;
import insynctive.dao.test.TestSuiteDao;
import insynctive.dao.test.TestSuiteRunDao;
import insynctive.model.Account;
import insynctive.model.test.TestPlan;
import insynctive.model.test.TestSuite;
import insynctive.model.test.run.TestPlanRun;
import insynctive.model.test.run.TestSuiteRun;
import insynctive.utils.TestWebRunner;

@Controller
@Transactional
public class NightlyController {
	
	private final int NIGHTLY_ACCOUNT_ID = 6;
	final String NIGHTLY_DEFAULT_ENVIRONMENT = "AutomationQA";
	
	//DB Connections.
	private final AccountDao accDao;
	private final TestSuiteRunDao testSuiteRunDao;
	private final TestSuiteDao testSuiteDao;
	private final TestPlanDao testPlanDao;
	
	//Test Runner
	private final TestWebRunner testRunner;

	@Inject
	public NightlyController(TestDao testDao, TestSuiteDao testSuiteDao, TestPlanDao testPlanDao, 
			TestRunDao testRunDao, TestSuiteRunDao testSuiteRunDao, TestPlanRunDao testPlanRunDao, 
			 ServletContext servletContext, AccountDao accDao, CreatePersonFormDao createPersonFormDao) {
		this.accDao = accDao;
		this.testSuiteRunDao = testSuiteRunDao;
		this.testSuiteDao = testSuiteDao;
		this.testPlanDao = testPlanDao;
		this.testRunner = new TestWebRunner();
	}
	
	@RequestMapping(value = "/nightly", method = RequestMethod.GET)
	@ResponseBody
	public String runNightly() throws Exception {
		Account nightlyAcc = accDao.getAccountByID(NIGHTLY_ACCOUNT_ID);
		TestPlan testPlan = testPlanDao.getTestPlanByName("Nightly");
		testRunner.runTest(testPlan, true);
		
		return "{\"status\" : 200, \"user\" : \""+nightlyAcc.getUsername()+"}";
	}
	
	@RequestMapping(value = "/nightly-microsoft", method = RequestMethod.POST)
	@ResponseBody
	public String runNightlyMicrosoft() throws Exception {
		Account nightlyAcc = accDao.getAccountByID(NIGHTLY_ACCOUNT_ID);
		return "{\"status\" : 200, \"user\" : \""+nightlyAcc.getUsername()+"}";
	}
}
