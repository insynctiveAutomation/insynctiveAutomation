package insynctive.controller;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
import insynctive.exception.ConfigurationException;
import insynctive.model.Account;
import insynctive.model.test.Test;
import insynctive.model.test.TestPlan;
import insynctive.model.test.TestSuite;
import insynctive.utils.HibernateUtil;

@Controller
@Transactional
public class InitController {

	TestPlanDao testPlanDao;
	TestDao testDao;
	TestSuiteDao testSuiteDao;
	
	@Inject
	public InitController(TestDao testDao, TestSuiteDao testSuiteDao, TestPlanDao testPlanDao,
			TestRunDao testRunDao, TestSuiteRunDao testSuiteRunDao, TestPlanRunDao testPlanRunDao,
			InsynctivePropertyDao propertyDao, AccountDao accDao, CreatePersonFormDao createPersonFormDao, CrossBrowserAccountDao crossDao) throws Exception {
		
		HibernateUtil.init(testDao, testSuiteDao, testPlanDao, testRunDao, testSuiteRunDao, testPlanRunDao, propertyDao, accDao, createPersonFormDao, crossDao);
		
		this.testPlanDao = testPlanDao;
		this.testDao = testDao;
		this.testSuiteDao = testSuiteDao;
	}
	
	@RequestMapping(value = "/init", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String init() throws Exception{
		return "{\"status\" : 200}";
	}
	
}
