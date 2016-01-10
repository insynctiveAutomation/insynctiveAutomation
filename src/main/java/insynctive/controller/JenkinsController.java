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
import insynctive.dao.CreatePersonFormDao;
import insynctive.dao.InsynctivePropertyDao;
import insynctive.model.Account;
import insynctive.model.ParamObject;
import insynctive.model.test.TestPlan;
import insynctive.utils.TestResults;
import insynctive.dao.test.TestDao;
import insynctive.dao.test.TestPlanDao;
import insynctive.dao.test.TestPlanRunDao;
import insynctive.dao.test.TestRunDao;
import insynctive.dao.test.TestSuiteDao;
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
	final String NIGHTLY_DEFAULT_ENVIRONMENT = "AutomationQA";
	
	//DB Connections.
	private final TestPlanDao testPlanDao;
	
	//Test Runner
	private final TestWebRunner testRunner;
		
	@Inject
	public JenkinsController(TestPlanDao testPlanDao) {
		this.testPlanDao = testPlanDao;
		this.testRunner = new TestWebRunner();
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

	private boolean needComunication(JenkinsForm jenkinsForm) {
		return jenkinsForm.isStatusFailure() || jenkinsForm.isPhaseStarted() || jenkinsForm.isStatusAborted() || jenkinsForm.isTypeInstall(); 
	}
	
	public void runMasterTests(JenkinsForm jenkinsForm) throws Exception{
		TestPlan testPlan = testPlanDao.getTestPlanByName("Jenkins Build - Master");
		testPlan.setNewEnvironmentInTests(jenkinsForm.getBuild().getParameters().getAccount());
		testRunner.runTest(testPlan, true, true);
	}

	private void runIntegrationsTests(JenkinsForm jenkinsForm) throws Exception {
		TestPlan testPlan = testPlanDao.getTestPlanByName("Jenkins Build - Integration");
		testPlan.setNewEnvironmentInTests(jenkinsForm.getBuild().getParameters().getAccount());
		testRunner.runTest(testPlan, true, true);
	}
	
	public void runOtherBranchesTests(JenkinsForm jenkinsForm) throws Exception {
		TestPlan testPlan = testPlanDao.getTestPlanByName("Jenkins Build - Other");
		testPlan.setNewEnvironmentInTests(jenkinsForm.getBuild().getParameters().getAccount());
		testRunner.runTest(testPlan, true, true);
	}
}
