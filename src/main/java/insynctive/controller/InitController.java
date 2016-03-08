package insynctive.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import insynctive.dao.AccountDao;
import insynctive.dao.CreatePersonFormDao;
import insynctive.dao.CrossBrowserAccountDao;
import insynctive.dao.InsynctivePropertyDao;
import insynctive.dao.test.TestDao;
import insynctive.dao.test.TestPlanDao;
import insynctive.dao.test.TestPlanRunDao;
import insynctive.dao.test.TestRunDao;
import insynctive.dao.test.TestSuiteDao;
import insynctive.dao.test.TestSuiteRunDao;
import insynctive.utils.HibernateUtil;

@Controller
@Transactional
public class InitController {

	private final TestDao testDao;
	private final TestSuiteDao testSuiteDao;
	private final TestPlanDao testPlanDao;
	
	private final TestRunDao testRunDao;
	private final TestSuiteRunDao testSuiteRunDao;
	private final TestPlanRunDao testPlanRunDao;
	
	private final AccountDao accDao;
	private final InsynctivePropertyDao propertyDao;
	private final CreatePersonFormDao createPersonFormDao;
	private final CrossBrowserAccountDao crossDao;
	
	@Inject
	public InitController(TestDao testDao, TestSuiteDao testSuiteDao, TestPlanDao testPlanDao,
			TestRunDao testRunDao, TestSuiteRunDao testSuiteRunDao, TestPlanRunDao testPlanRunDao,
			InsynctivePropertyDao propertyDao, AccountDao accDao, CreatePersonFormDao createPersonFormDao, CrossBrowserAccountDao crossDao) throws Exception {
		
		this.testDao = testDao;
		this.testSuiteDao = testSuiteDao;
		this.testPlanDao = testPlanDao;
		
		this.testRunDao = testRunDao;
		this.testSuiteRunDao = testSuiteRunDao;
		this.testPlanRunDao = testPlanRunDao;
		
		this.accDao = accDao;
		this.propertyDao = propertyDao;
		this.createPersonFormDao = createPersonFormDao;
		this.crossDao = crossDao;
		
		HibernateUtil.init(testDao, testSuiteDao, testPlanDao, testRunDao, testSuiteRunDao, testPlanRunDao, propertyDao, accDao, createPersonFormDao, crossDao);
		
	}
	
	@RequestMapping(value = "/init", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String init() throws Exception{
		new InitController(testDao, testSuiteDao, testPlanDao, testRunDao, testSuiteRunDao, testPlanRunDao, propertyDao, accDao, createPersonFormDao, crossDao);
		return "{\"status\" : 200}";
	}
	
}
