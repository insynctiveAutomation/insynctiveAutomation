package insynctive.utils.saucelabs;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import insynctive.utils.ExternalTestRunner;
import insynctive.utils.data.TestEnvironment;

public class SauceLabsUtil implements ExternalTestRunner {

	private String username = "Insynctive";
	private String password = "ce1eeaf8-abcf-4c05-81ed-33ae8d747c21";

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
		
		capabilities.setCapability("name", sessionName+" ["+testEnvironment+"]");
		//Set BROWSER > VERSION > PLATAFORM
		capabilities.setCapability(CapabilityType.BROWSER_NAME, testEnvironment.browserSauceLabs);
		if (testEnvironment.versionSauceLabs != null) { capabilities.setCapability(CapabilityType.VERSION, testEnvironment.versionSauceLabs);	}
		capabilities.setCapability(CapabilityType.PLATFORM, testEnvironment.osSauceLabs);
		
		if(!testEnvironment.isDesktop) {capabilities.setCapability("deviceOrientation", "portrait");}
		
		
		return new RemoteWebDriver(new URL("http://" + username + ":" + password + "@ondemand.saucelabs.com:80/wd/hub"), capabilities);
	}

	@Override
	public void makeCurlToChangeStatus(String jobID, Boolean generalStatus)
			throws MalformedURLException, IOException, JSONException {

	}

	@Override
	public String getPublicVideoLinkOfJob(String jobID) throws IOException, JSONException {
		// TODO Auto-generated method stub
		return null;
	}
}
