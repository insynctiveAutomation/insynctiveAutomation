package insynctive.utils.saucelabs;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.saucerest.SauceREST;

import insynctive.utils.ExternalTestRunner;
import insynctive.utils.data.TestEnvironment;

public class SauceLabsUtil implements ExternalTestRunner {

	private String username = "Insynctive1";
	private String password = "363640d1-9cec-43c7-b966-26cff68bbdba";
	private SauceREST rest;
	
	private final int COMMAND_TIMEOUT = 420;
	private final int IDLE_COMMAND_TIMEOUT = 300;
	
	public SauceLabsUtil() {
		rest = new SauceREST(username, password);
	}
	
	@Override
	public String getRemoteTestingUsername() {
		return username;
	}

	@Override
	public void setRemoteTestingUsername(String username) {
		this.username = username;
	}

	@Override
	public String getRemoteTestingPassword() {
		return password;
	}

	@Override
	public void setRemoteTestingPassword(String password) {
		this.password = password;
	}

	@Override
	public RemoteWebDriver getRemoteWebDriver(String sessionName, TestEnvironment testEnvironment) throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		capabilities.setCapability("command-timeout", COMMAND_TIMEOUT);
		capabilities.setCapability("commandTimeout", IDLE_COMMAND_TIMEOUT);
		capabilities.setCapability("name", sessionName+" ["+testEnvironment+"]");
		//Set BROWSER > VERSION > PLATAFORM
		capabilities.setCapability(CapabilityType.BROWSER_NAME, testEnvironment.browserSauceLabs);
		if (testEnvironment.versionSauceLabs != null) { capabilities.setCapability(CapabilityType.VERSION, testEnvironment.versionSauceLabs);	}
		capabilities.setCapability(CapabilityType.PLATFORM, testEnvironment.osSauceLabs);
		
		if(!testEnvironment.isDesktop) {capabilities.setCapability("deviceOrientation", "portrait");}
		
		
		return new RemoteWebDriver(new URL("http://" + username + ":" + password + "@ondemand.saucelabs.com:80/wd/hub"), capabilities);
	}

	@Override
	public void changeStatusOfJob(String jobID, Boolean generalStatus) throws MalformedURLException, IOException, JSONException {
		if(generalStatus) { rest.jobPassed(jobID); } else { rest.jobFailed(jobID);}
	}

	@Override
	public String getPublicVideoLinkOfJob(String jobID) throws IOException, JSONException {
		return "https://saucelabs.com/beta/tests/" + jobID + "/watch";
	}
}
