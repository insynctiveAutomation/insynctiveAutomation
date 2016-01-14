package insynctive.utils;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.openqa.selenium.remote.RemoteWebDriver;

import insynctive.utils.data.TestEnvironment;

public interface ExternalTestRunner {

	public String getRemoteTestingUsername();
	public void setRemoteTestingUsername(String username);
	public String getRemoteTestingPassword();
	public void setRemoteTestingPassword(String password);
	
	public RemoteWebDriver getRemoteWebDriver(String sessionName, TestEnvironment testEnvironment) throws MalformedURLException;
	public void changeStatusOfJob(String jobID, Boolean generalStatus) throws MalformedURLException, IOException, JSONException;
	public String getPublicVideoLinkOfJob(String jobID) throws IOException, JSONException;
}
