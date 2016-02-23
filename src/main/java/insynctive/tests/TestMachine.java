package insynctive.tests;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;

import com.fasterxml.jackson.annotation.JsonIgnore;

import insynctive.exception.ConfigurationException;
import insynctive.model.CrossBrowserAccount;
import insynctive.model.ParamObject;
import insynctive.model.test.run.TestRun;
import insynctive.pages.insynctive.LoginPage;
import insynctive.utils.Debugger;
import insynctive.utils.ExternalTestRunner;
import insynctive.utils.HibernateUtil;
import insynctive.utils.Sleeper;
import insynctive.utils.TestResults;
import insynctive.utils.crossbrowser.CrossBrowserUtil;
import insynctive.utils.data.TestEnvironment;
import insynctive.utils.saucelabs.SauceLabsUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public abstract class TestMachine {

	//Session Name with default values
	public String sessionName = "Insynctive Session";
	public String testName = "Test Name";
	public String suiteName = "Suite Name";
	
	//Parameters
	public Integer runID;
	public String environment;
	public TestEnvironment testEnvironment;
	public boolean isRemote;
	public boolean isNotification;
	public Integer testSuiteID; //TestSuiteID represent the test in the lists of 'video links' and 'result listeners'.
	
	//Parameter to chenge ParamObject
	public ParamObject paramObject;
	
	//WebDriver
//	public ExternalTestRunner externalTestRunner = new CrossBrowserUtil();
	public ExternalTestRunner externalTestRunner = new SauceLabsUtil();
	public WebDriver driver;
	public ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
	public ThreadLocal<String> sessionId = new ThreadLocal<String>();
	public String jobID;

	//Final result.
	boolean generalStatus = true;
	
	//SLACK link
	private String slackChannel = "https://hooks.slack.com/services/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";

	//TAGS => Add tests results (only the fail tests).
	List<String> tags = new ArrayList<String>();
	
	private String getJobURL() throws IOException, JSONException {
		return externalTestRunner.getPublicVideoLinkOfJob(jobID);
	}
	
	public WebDriver getWebDriver() {
		System.out.println("WebDriver" + webDriver.get());
		return webDriver.get();
	}

	public String getSessionId() {
		return sessionId.get();
	}
	
	public void tearUp(String browser, String environment, String isRemote, String isNotification, String testSuiteID) throws Exception {
		tearUp(TestEnvironment.valueOf(browser), environment, Boolean.valueOf(isRemote), Boolean.valueOf(isNotification), Integer.parseInt(testSuiteID));
	}

	//@BeforeClass()
	public void tearUp(TestEnvironment testEnvironment, String environment, Boolean isRemote, Boolean isNotification, Integer testSuiteID) throws Exception{
		try{

			this.runID = getRunID();
			this.isRemote = isRemote;
			this.testEnvironment = testEnvironment;
			this.environment = environment;
			this.isNotification = isNotification;
			this.testSuiteID = testSuiteID;
			
			Sleeper.setIsRemote(isRemote);
			
		} catch(Exception ex){
			ex.printStackTrace();
			throw new Exception("Fail on TearUp "+ex);
		}
	}
	
	@AfterClass(alwaysRun = true)
	public void teardown() throws ConfigurationException, MalformedURLException, IOException, JSONException {
		try{
			if(isRemote){this.driver.quit();}
		} catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Fail On TearDown");
		} finally {
			setFinalResult();
		}
	}
	
	public WebDriver createDriver() throws MalformedURLException {

		webDriver.set(externalTestRunner.getRemoteWebDriver(sessionName, testEnvironment));
	  
	    jobID = ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
		sessionId.set(jobID);
		if(testEnvironment.isDesktop) webDriver.get().manage().window().maximize();
		
	    return webDriver.get();
	}

	public void startTest() throws ConfigurationException, JSONException, IOException {
		if (isRemote) {
			driver = createDriver();
		} else {
			FirefoxProfile firefoxProfile = new FirefoxProfile();
			firefoxProfile.setAcceptUntrustedCertificates(true);
			firefoxProfile.setAssumeUntrustedCertificateIssuer(true);
			driver = new FirefoxDriver(firefoxProfile);
			driver.manage().window().maximize();
		}
		TestResults.addVideo(testSuiteID, getJobURL());
	}

	public void failTest(String testName,Exception ex, boolean isSaucelabs) throws Exception{
		failTest(testName, ex, isSaucelabs, null);
	}
	
	public void failTest(String testName,Exception ex, boolean isSaucelabs, Long duration) throws Exception {
		Reporter.log( testName, true );
		ex.printStackTrace();
		
		Throwable cause = ex.getCause();
		String exMessage = ex.getMessage();
		String nameAndCause = "";

		if(cause != null){
//			nameAndCause = testName+" Cause Message: => " + cause.getMessage();
			nameAndCause = testName;
		} else if(exMessage != null) {
//			nameAndCause = testName+" Exception Message =>  "+exMessage;
			nameAndCause = testName;
		} else {
//			nameAndCause = testName+" => "+ (ex != null ? ex : "EXCEPTION");
			nameAndCause = testName;
		}
		
		Debugger.log(ex, "Exception");
		
		setResult(false, nameAndCause, duration);
	}
	
	//Set Result WITHOUT duration
	public void setResult(boolean status, String nameOfTest) throws MalformedURLException, IOException {
		setResult(status, nameOfTest, null);
	}
	
	//Set Result WITH duration
	public void setResult(boolean status, String nameOfTest, Long duration) throws MalformedURLException, IOException {
		
		String result = nameOfTest+"["+(status ? "PASS" : "FAIL")+"]";

		if (!status){
			tags.add(result);
			generalStatus = status;
		}
	}

	public void setFinalResult() throws ConfigurationException, MalformedURLException, IOException, JSONException {
		if(isRemote) { externalTestRunner.changeStatusOfJob(jobID, generalStatus); }
		if(isNotification) { sendSlack(); }
	}

	@SuppressWarnings("unchecked")
	public void sendSlack() throws IOException, JSONException {
		URL u = new URL(slackChannel);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		JSONObject payload = new JSONObject();
		payload.put("icon_emoji", ":ghost:");
		payload.put("username", "Automated Tests Machine");
		payload.put("text", sessionName+" [ "+testEnvironment != null? testEnvironment : "Local Firefox"+"]");
		payload.put("channel", "#automatedtestsresults");
		
		JSONArray attachments = new JSONArray();
		JSONObject attachment = new JSONObject(); 
		attachment.put("fallback", "Status of the Test "+sessionName+": "+(generalStatus ? "PASS" : "FAIL"));
		String testDetailsUrl = getTestDetailsUrl();
		attachment.put("pretext", 
				(jobID == null ? "Job didn't open " : "<"+getJobURL()+"|Watch test video here> | ") + 
				(testDetailsUrl != null ? "<"+testDetailsUrl+" |See details> | " : "") + 
				"Environment: "+environment);
		
		attachment.put("color", generalStatus ? "#00CE00" : "#FC000D"); //AA3939
		
		JSONArray fields = new JSONArray();
		JSONObject field = new JSONObject();
		field.put("title", sessionName);
		System.out.println(getStatusMessageOfTests(tags));
		field.put("value", (generalStatus ? "PASS" : "FAIL")+"\n"+getStatusMessageOfTests(tags));
		field.put("short", false);
		fields.add(field);
		attachment.put("fields", fields);
		attachments.add(attachment);
		
		payload.put("attachments", attachments);
		
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5000);
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(payload.toString());
		wr.flush();
        wr.close();
        
		InputStream is = conn.getInputStream();
		System.out.println(is);
	}
	
	private String getTestDetailsUrl() {
		String testEnvironment = getTestEnvironment();
		return testEnvironment != null ? testEnvironment+"testSuite?id="+testSuiteID : null;
	}

	private String getTestEnvironment() {
		ResourceBundle rb = ResourceBundle.getBundle("application");
		Integer environment = Integer.valueOf(rb.getString("environment"));
		switch (environment) {
		case 2:
			return "https://insynctiveautomation.herokuapp.com/";
		case 3:
			return "https://alpha-insynctiveautomation.herokuapp.com/";
		default:
			return null;
		}
	}

	private String getStatusMessageOfTests(List<String> testsStatus) {
		String results = "";
		if (testsStatus.size() != 0){
			for(int index = 0 ; index < testsStatus.size()-1 ; index++){
				results += (testsStatus.get(index) + " \n ");
			}
			results += testsStatus.get(testsStatus.size()-1);
		}
		
		return results;
	}
	
	public void changeParamObject(Integer testID) throws Exception{
		try{
			TestRun testRun = HibernateUtil.testRunDao.getTestRunByID(testID);
			paramObject = testRun.getParamObject();
			System.out.println("Param Object Changes to: "+testID+" ID");
		} catch(Exception ex) {
			System.out.println(ex);
			throw new Exception("Fail on changeObject "+ex);
		}
	}
	
	@JsonIgnore
	public Integer getRunID() throws IOException {
		URL u = new URL("https://insynctive-support.herokuapp.com/runID");
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		
		InputStream is = conn.getInputStream();
		
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8")); 
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null){
			responseStrBuilder.append(inputStr);
		}
		
		JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
		return jsonObject.getInt("runID");
	}
	
	public String getRunIDAsString(){
		return String.valueOf(runID);
	}
	
	
	//TODO need to be moved
	public LoginPage login() throws Exception {
		return login(paramObject.getLoginUsername(),paramObject.getLoginPassword(), null);
	}
	
	public LoginPage login(String returnURL) throws Exception {
		return login(paramObject.getLoginUsername(),paramObject.getLoginPassword(), returnURL);
	}
	
	public LoginPage loginAsEmployee(String email, String password) throws Exception {
		LoginPage loginPage = new LoginPage(driver, environment);
		loginPage.setReturnAsEmployee();
		loginPage.loadPage();
		loginPage.login(email, password);
		return loginPage;
	}

	public LoginPage login(String username, String password, String returnURL) throws Exception {
		LoginPage loginPage = new LoginPage(driver, environment);
		if(returnURL != null) loginPage.setReturnURL(returnURL);
		loginPage.loadPage();
		loginPage.login(username, password);
		return loginPage;
	}
	
}
