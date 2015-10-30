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
import java.util.concurrent.Semaphore;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import insynctive.exception.ConfigurationException;
import insynctive.model.Account;
import insynctive.model.CrossBrowserAccount;
import insynctive.model.InsynctiveProperty;
import insynctive.model.ParamObject;
import insynctive.model.Test;
import insynctive.pages.insynctive.LoginPage;
import insynctive.pages.insynctive.agent.hr.HomeForAgentsPage;
import insynctive.utils.Debugger;
import insynctive.utils.HibernateUtil;
import insynctive.utils.Sleeper;
import insynctive.utils.TestResults;
import insynctive.utils.data.TestEnvironment;

public abstract class TestMachine {

	//Hibernate connection
	public SessionFactory sessionFactory;
	
	//Session Name
	public String sessionName = "Insynctive Session";
	public String testName = "Test Name";
	public String suiteName = "Suite Name";
	public boolean isSaucelabs;
	public TestEnvironment testEnvironment;
	
	public InsynctiveProperty properties;
	public Account account; 
	public ParamObject paramObject;
	
	public Session session = null;
	
	public WebDriver driver;
	
	/** TAGS => Add tests result test.*/
	List<String> tags = new ArrayList<String>();
	
	boolean generalStatus = true;
	
	public ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
	public ThreadLocal<String> sessionId = new ThreadLocal<String>();
	public String jobID;
	public Integer testSuiteID;
	
	//SLACK
	private String slackChannel = "https://hooks.slack.com/services/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";

	//CROSSBROWSING
	String username;
	String password;
	
	private String getJobURL() throws IOException, JSONException {
		return getPublicVideoLinkOfJob();
	}
	
	public WebDriver getWebDriver() {
		System.out.println("WebDriver" + webDriver.get());
		return webDriver.get();
	}

	public String getSessionId() {
		return sessionId.get();
	}
	
	public void tearUp() throws Exception {
		tearUp(1, null);
	}
	
	Semaphore mutex = new Semaphore(1);
	public void tearUp(Integer accountID, Integer runID) throws Exception{
		mutex.acquire();
		try{
			sessionFactory = HibernateUtil.getSessionFactory();
			
			CrossBrowserAccount crossBrowserAccount = (CrossBrowserAccount) HibernateUtil.get(CrossBrowserAccount.class, 1);

			username = crossBrowserAccount.getEmail();
			password = crossBrowserAccount.getPassword();
			
		
			account = (Account) HibernateUtil.get(Account.class, accountID);
			account.setRunID(runID != null ? runID : account.getRunID());
			
			paramObject = account.getParamObject();
			properties = account.getAccountProperty();
			Sleeper.setIsRemote(properties.isRemote());
			
			TestResults.addResult("<h2>"+sessionName+"</h2>");
		} catch(Exception ex){
			System.out.println(ex);
			throw new Exception("Fail on TearUp "+ex);
		} finally {
			mutex.release();
		}
	}
	
	public Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public Session openSession(){
		return sessionFactory.openSession();
	}
	
	@AfterClass(alwaysRun = true)
	public void teardown() throws ConfigurationException, MalformedURLException, IOException, JSONException {
		try{ if(properties.isRemote()){this.driver.quit();}} 
		catch(Exception ex) {}
		setFinalResult();
	}
	
	public WebDriver createDriver(TestEnvironment testEnvironment) throws MalformedURLException {

		this.testEnvironment = testEnvironment;
		
		DesiredCapabilities caps = new DesiredCapabilities();

		caps.setCapability("name", sessionName+" ["+testEnvironment+"]");
	    caps.setCapability("build", "1.0");
	    caps.setCapability("browser_api_name", testEnvironment.browser+testEnvironment.version);
	    caps.setCapability("os_api_name", testEnvironment.os);
	    caps.setCapability("screen_resolution", testEnvironment.screenSize);
	    caps.setCapability("record_video", "true");
	    caps.setCapability("record_network", "true");
	    caps.setCapability("record_snapshot", "false");
	    caps.setCapability("max_duration", 3600);

	    ("http://"+getEmailForCurl()+":"+password+"@hub.crossbrowsertesting.com:80/wd/hub").equals("http://eugenio.valeiras%2b9%40gmail.com:uca60139cbdf183b@hub.crossbrowsertesting.com:80/wd/hub");
	    webDriver.set(new RemoteWebDriver(new URL("http://"+getEmailForCurl()+":"+password+"@hub.crossbrowsertesting.com:80/wd/hub"), caps));
	    jobID = ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
		sessionId.set(jobID);
		if(testEnvironment.isDesktop) webDriver.get().manage().window().maximize();
		
	    return webDriver.get();
	}

	public void startTest(TestEnvironment testEnvironment) throws ConfigurationException, JSONException, IOException {
		if (properties.isRemote()) {
			driver = createDriver(testEnvironment);
		} else {
			FirefoxProfile firefoxProfile = new FirefoxProfile();
			firefoxProfile.setAcceptUntrustedCertificates(true);
			firefoxProfile.setAssumeUntrustedCertificateIssuer(true);
			driver = new FirefoxDriver(firefoxProfile);
			driver.manage().window().maximize();
		}
		TestResults.addVideo(testSuiteID, getJobURL());
	}
	
	public void openPersonFile(String emailSearch) throws Throwable{
		HomeForAgentsPage homePage = new HomeForAgentsPage(driver, properties.getEnvironment()); 
		homePage.openPersonFile(emailSearch);
	}

	public LoginPage login(String username, String password, String returnURL) throws Exception {
		LoginPage loginPage = new LoginPage(driver, properties.getEnvironment());
		if(returnURL != null) loginPage.setReturnURL(returnURL);
		loginPage.loadPage();
		loginPage.login(username, password);
		return loginPage;
	}
	
	public LoginPage login() throws Exception {
		return login(paramObject.getLoginUsername(),paramObject.getLoginPassword(), null);
	}
	
	public LoginPage login(String returnURL) throws Exception {
		return login(paramObject.getLoginUsername(),paramObject.getLoginPassword(), returnURL);
	}
	
	public LoginPage loginAsEmployee(String email, String password) throws Exception {
		LoginPage loginPage = new LoginPage(driver, properties.getEnvironment());
		loginPage.setReturnAsEmployee();
		loginPage.loadPage();
		loginPage.login(email, password);
		return loginPage;
		
	}

	public void failTest(String testName,Exception ex, boolean isSaucelabs) throws Exception{
		failTest(testName, ex, isSaucelabs, null);
	}
	
	public void failTest(String testName,Exception ex, boolean isSaucelabs, Long duration) throws Exception{
		Reporter.log( testName, true );
		System.out.println(ex.getStackTrace()[7]);
		System.out.println(ex.getStackTrace()[6]);
		System.out.println(ex.getStackTrace()[5]);
		System.out.println(ex.getStackTrace()[4]);
		System.out.println(ex.getStackTrace()[3]);
		System.out.println(ex.getStackTrace()[2]);
		System.out.println(ex.getStackTrace()[1]);
		System.out.println(ex.getStackTrace()[0]);
		
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
		
		Debugger.log(nameAndCause, isSaucelabs);
		
		setResult(false, nameAndCause, duration);
	}
	
	public void setResult(boolean status, String nameOfTest) throws MalformedURLException, IOException {
		setResult(status, nameOfTest, null);
	}
	
	public void setResult(boolean status, String nameOfTest, Long duration) throws MalformedURLException, IOException {
		
		String result = nameOfTest+"["+(status ? "PASS" : "FAIL")+"]";

		if(duration != null){
			TestResults.addResult(result+" (Duration: "+duration/1000000+" ms)"); 
		} else {
			TestResults.addResult(result); 
		}
		
		if (!status){
			tags.add(result);
			generalStatus = status;
		}
	}

	public void setFinalResult() throws ConfigurationException, MalformedURLException, IOException, JSONException {
		if(properties.isRemote()){
			TestResults.addResult("<a href=\""+getPublicVideoLinkOfJob()+"\">Watch Video</a>");
			makeCurlToChangeStatus();
		}
		if(properties.isNotification()) {sendSlack();}
	}
	
	public JSONObject makeCurl(String url, String type) throws IOException, JSONException{
		String userPassword = username + ":" + password;
		
		java.util.Base64.Encoder encoded = java.util.Base64.getEncoder();
		String crud = encoded.encodeToString(userPassword.getBytes());
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		
		conn.setRequestMethod(type);
		conn.setRequestProperty("Authorization", "Basic " + crud);
        		
		InputStream is = conn.getInputStream();
		
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8")); 
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null)
		    responseStrBuilder.append(inputStr);
		return new JSONObject(responseStrBuilder.toString());
	}
	
	public void makeCurlToChangeStatus() throws MalformedURLException, IOException, JSONException{
		String url = "http://app.crossbrowsertesting.com/api/v3/selenium/" + jobID + "?action=set_score&score="+(generalStatus ? "pass" : "fail");
		makeCurl(url, "PUT");
	}
	
	public String getPublicVideoLinkOfJob() throws IOException, JSONException{
		if (jobID != null){
			String url = "http://crossbrowsertesting.com/api/v3/selenium/" + jobID;
			JSONObject response = makeCurl(url, "GET");
			
			org.json.JSONArray videos = response.getJSONArray("videos");
			JSONObject video = (JSONObject) videos.get(0);
			return video.getString("show_result_public_url");
 		} else {
 			return null;
 		}
	}
	
	private String getEmailForCurl() {
		String returnEmail = "";
		String[] returnUsernameForCurl = username.split("\\+");
		returnEmail += (returnUsernameForCurl.length == 2) ? returnUsernameForCurl[0]+"%2B"+returnUsernameForCurl[1] : returnUsernameForCurl[0];
		returnUsernameForCurl = returnEmail.split("@");
		return returnUsernameForCurl[0]+"%40"+returnUsernameForCurl[1];
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
		attachment.put("pretext", (jobID == null ? "Local Test" : "<"+getJobURL()+"|Watch test video here>")+" | Environment: "+properties.getEnvironment());
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
	
	Semaphore changeObjectMutex = new Semaphore(1);
	public void changeParamObject(Integer testID) throws Exception{
		try{
			changeObjectMutex.acquire();
			Test test = (Test) HibernateUtil.get(Test.class, testID);
			paramObject = test.getParamObject();
		} catch(Exception ex) {
			System.out.println(ex);
			throw new Exception("Fail on changeObject "+ex);
		} finally {
			changeObjectMutex.release();
		}
	}
	
	public void setparamObjectAsAccount(Integer testID) {
		paramObject = account.getParamObject();
	}
	
	/* RUNNABLE TEST */
//	public static void main(String[] args) throws IOException, JSONException, ConfigurationException {
//		String xmlFileName = "testRun.xml";
//		List<XmlSuite> suite;
//		try
//		{
//		    suite = (List <XmlSuite>)(new Parser(xmlFileName).parse());
//			TestNG testNG = new TestNG();
//			testNG.setXmlSuites(suite);
//			testNG.run();
//		}
//		catch (ParserConfigurationException e)
//		{
//		    e.printStackTrace();
//		}
//		catch (SAXException e)
//		{
//		    e.printStackTrace();
//		}
//		catch (IOException e)
//		{
//		    e.printStackTrace();
//		}
//	}
}
