package insynctive.controller;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import insynctive.dao.AccountDao;
import insynctive.dao.InsynctivePropertyDao;
import insynctive.dao.TestDao;
import insynctive.dao.TestSuiteDao;
import insynctive.model.Account;
import insynctive.model.ParamObject;
import insynctive.model.TestSuite;
import insynctive.utils.TestResults;
import insynctive.utils.TestWebRunner;

@Controller
@RequestMapping(value = "/jenkins")
public class JenkinsController {

	//NIGHTLY SETTINGS
	private final int NIGHTLY_ACCOUNT_ID = 6;
	final String NIGHTLY_DEFAULT_ENVIRONMENT = "AutomationQA";
	
	//DB Connections.
	private final InsynctivePropertyDao propertyDao;
	private final AccountDao accDao;
	private final TestSuiteDao testSuiteDao;
	
	//Servlet Context Helper
	private final ServletContext servletContext;

	//Test Runner
	private final TestWebRunner testRunner;

	@Inject
	public JenkinsController(TestDao testDao, InsynctivePropertyDao propertyDao, ServletContext servletContext, AccountDao accDao, TestSuiteDao testSuiteDao) {
		this.servletContext = servletContext;
		this.propertyDao = propertyDao;
		this.accDao = accDao;
		this.testSuiteDao = testSuiteDao;
		this.testRunner = new TestWebRunner(servletContext, testSuiteDao, accDao, testDao);
	}
	
	@RequestMapping(value = "/build", method = RequestMethod.POST)
	@ResponseBody
	public String newBuild(@RequestBody JenkinsForm jenkinsForm) throws Exception {
		boolean isMaster = jenkinsForm.account.equals("master");
		boolean isIntegration = jenkinsForm.branch.toLowerCase().contains("integration"); 
		
		if(isMaster){ } else if(isIntegration){ }
		
		Account nightlyAcc = accDao.getAccountByID(NIGHTLY_ACCOUNT_ID);
		ParamObject defaultParamObject = nightlyAcc.getParamObject();
		
		//FIRST LOGIN
		TestSuite firstLoginform = testRunner.createTestSuite(defaultParamObject,"First Login", NIGHTLY_DEFAULT_ENVIRONMENT, "FIREFOX");
		Integer firstLoginRun = testRunner.runTest(firstLoginform, nightlyAcc);
		
		//Person File - FIREFOX
		TestSuite form = testRunner.createTestSuite(defaultParamObject,"Person File", NIGHTLY_DEFAULT_ENVIRONMENT, "FIREFOX");
			form.getTestByName("createPersonTest").getParamObject().setBooleanParamOne(false);
		Integer PersonFileFirefox = testRunner.runTest(form, nightlyAcc, TestResults.workers.get(firstLoginRun));
		
		return "{\"branch\" : \""+jenkinsForm.branch+"\", \"account\" : \""+jenkinsForm.account+"\", \"isMaster\" : \""+isMaster+"\", \"isIntegration\" : \""+isIntegration+"\"}";
	}
	public class JenkinsForm {
		public String account;
		public String branch;
	}
}
