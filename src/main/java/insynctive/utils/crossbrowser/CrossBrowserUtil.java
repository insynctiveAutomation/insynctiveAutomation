package insynctive.utils.crossbrowser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import insynctive.utils.ExternalTestRunner;
import insynctive.utils.MakeCurl;
import insynctive.utils.data.TestEnvironment;

public class CrossBrowserUtil implements ExternalTestRunner {

	private String username = "rgonzalez@insynctive.com";
	private String password = "u89bf8bdf84b358d";
	
	public RemoteWebDriver getRemoteWebDriver(String sessionName, TestEnvironment testEnvironment) throws MalformedURLException {

		DesiredCapabilities caps = new DesiredCapabilities();

		caps.setCapability("name", sessionName+" ["+testEnvironment+"]");
	    caps.setCapability("build", "1.0");
	    caps.setCapability("browser_api_name", testEnvironment.browserCrossBrowser+testEnvironment.versionCrossBrowser);
	    caps.setCapability("os_api_name", testEnvironment.osCrossBrowser);
	    caps.setCapability("screen_resolution", testEnvironment.screenSizeCrossBrowser);
	    caps.setCapability("record_video", "true");
	    caps.setCapability("record_network", "true");
	    caps.setCapability("record_snapshot", "false");
	    caps.setCapability("max_duration", 3600);

	    return new RemoteWebDriver(new URL("http://"+getEmailForCurl()+":"+password+"@hub.crossbrowsertesting.com:80/wd/hub"), caps);
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
	
	private String getEmailForCurl() {
		String returnEmail = "";
		String[] returnUsernameForCurl = username.split("\\+");
		returnEmail += (returnUsernameForCurl.length == 2) ? returnUsernameForCurl[0]+"%2B"+returnUsernameForCurl[1] : returnUsernameForCurl[0];
		returnUsernameForCurl = returnEmail.split("@");
		return returnUsernameForCurl[0]+"%40"+returnUsernameForCurl[1];
	}

	@Override
	public void changeStatusOfJob(String jobID, Boolean generalStatus) throws MalformedURLException, IOException, JSONException{
		String url = "http://app.crossbrowsertesting.com/api/v3/selenium/" + jobID + "?action=set_score&score="+(generalStatus ? "pass" : "fail");
		MakeCurl.makeCurl(url, username, password, "PUT");
	}
	
	@Override
	public String getPublicVideoLinkOfJob(String jobID) throws IOException, JSONException{
		if (jobID != null){
			String url = "http://crossbrowsertesting.com/api/v3/selenium/" + jobID;
			JSONObject response = MakeCurl.makeCurl(url, username, password, "GET");
			
			org.json.JSONArray videos = response.getJSONArray("videos");
			JSONObject video = (JSONObject) videos.get(0);
			return video.getString("show_result_public_url");
 		} else {
 			return null;
 		}
	}
	
}
