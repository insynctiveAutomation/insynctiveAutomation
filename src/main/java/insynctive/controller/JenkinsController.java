package insynctive.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import insynctive.dao.AccountDao;
import insynctive.dao.InsynctivePropertyDao;
import insynctive.model.Account;
import insynctive.model.ParamObject;
import insynctive.utils.TestResults;
import insynctive.dao.test.TestDao;
import insynctive.dao.test.TestSuiteRunDao;
import insynctive.utils.TestWebRunner;
import insynctive.utils.jenkins.JenkinsForm;
import insynctive.utils.slack.SlackMessage;
import insynctive.utils.slack.SlackUtil;
import insynctive.utils.slack.builders.SlackMessageBuilder;

@Controller
@RequestMapping(value = "/jenkins")
public class JenkinsController {

	//NIGHTLY SETTINGS
	private final int NIGHTLY_ACCOUNT_ID = 6;
	final String NIGHTLY_DEFAULT_ENVIRONMENT = "AutomationQA";
	
	//DB Connections.
	private final InsynctivePropertyDao propertyDao;
	private final AccountDao accDao;
	private final TestSuiteRunDao testSuiteDao;
	
	//Servlet Context Helper
	private final ServletContext servletContext;

	//Test Runner
	private final TestWebRunner testRunner;

	@Inject
	public JenkinsController(TestDao testDao, InsynctivePropertyDao propertyDao, ServletContext servletContext, AccountDao accDao, TestSuiteRunDao testSuiteDao) {
		this.servletContext = servletContext;
		this.propertyDao = propertyDao;
		this.accDao = accDao;
		this.testSuiteDao = testSuiteDao;
		this.testRunner = new TestWebRunner(servletContext, testSuiteDao, accDao, testDao);
	}
	
	@RequestMapping(value = "/build", method = RequestMethod.POST)
	@ResponseBody
	public String newBuild(@RequestBody JenkinsForm jenkinsForm) throws Exception {

		//Comunicate The status of the build if need it.
		if(needComunication(jenkinsForm)){
			SlackMessage Slackmessage = new SlackMessageBuilder()
					.setUsername(jenkinsForm.getUsername())
					.setText(jenkinsForm.getMessage())
					.setIconEmoji(jenkinsForm.getEmoji())
					.setChannel(jenkinsForm.getChannel())
					.build();
			SlackUtil.sendMessage(Slackmessage);
		}

		//Start Tests if is install and finish success.
		if(jenkinsForm.isTypeInstall() && jenkinsForm.isStatusSuccess()){
			
			if(jenkinsForm.isMaster()){ 
				runMasterTests(jenkinsForm);
			} else if(jenkinsForm.isIntegration()){
				runIntegrationsTests(jenkinsForm);
			} else {
				runOtherBranchesTests(jenkinsForm);
			}
		
		}
		
		return "{\"status\" : 200}";
	}
	
	private void runOtherBranchesTests(JenkinsForm jenkinsForm) throws Exception {
		runMasterTests(jenkinsForm);
	}

	private void runIntegrationsTests(JenkinsForm jenkinsForm) throws Exception {
		runMasterTests(jenkinsForm);
	}

	private boolean needComunication(JenkinsForm jenkinsForm) {
		return jenkinsForm.isStatusFailure() || jenkinsForm.isPhaseStarted() || jenkinsForm.isStatusAborted() || jenkinsForm.isTypeInstall(); 
	}

	public void runMasterTests(JenkinsForm jenkinsForm) throws Exception{
		Account nightlyAcc = accDao.getAccountByID(NIGHTLY_ACCOUNT_ID);
		ParamObject defaultParamObject = nightlyAcc.getParamObject();
		String insynctiveAccount = jenkinsForm.getBuild().getParameters().getAccount();
		
		//FIRST LOGIN
//		TestSuite firstLoginform = testRunner.createTestSuite(defaultParamObject,"First Login", insynctiveAccount, "FIREFOX");
//		Integer firstLoginRun = testRunner.runTest(firstLoginform, nightlyAcc);
//		
//		//Person File - FIREFOX
//		TestSuite form = testRunner.createTestSuite(defaultParamObject,"Person File", insynctiveAccount, "FIREFOX");
//			form.getTestByName("createPersonTest").getParamObject().setBooleanParamOne(false);
//		Integer PersonFileFirefox = testRunner.runTest(form, nightlyAcc, TestResults.workers.get(firstLoginRun));
	}
}
