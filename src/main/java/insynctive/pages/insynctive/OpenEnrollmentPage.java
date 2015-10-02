package insynctive.pages.insynctive;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.exception.ElementNotFoundException;
import insynctive.pages.Page;
import insynctive.pages.PageInterface;
import insynctive.utils.Sleeper;

public class OpenEnrollmentPage extends Page implements PageInterface{

	String enviroment;
	@FindBy(css = "#frmBanner > iframe")
	WebElement banneriFrame;
	@FindBy(id = "tblBanner")
	WebElement banner;
	@FindBy(id = "lnkInfoStatus")
	WebElement updateInfoButton;
	@FindBy(id = "lnkElectionStatus")
	WebElement electButton;
	@FindBy(id = "lnkEnrollStatus")
	WebElement enrollButton;
	
	public OpenEnrollmentPage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://"+ enviroment+ ".insynctiveapps.com/Insynctive.Hub/Protected/HelpDesk.aspx?page";
		this.PAGE_TITLE = "My Questions";
		PageFactory.initElements(driver, this);
	}

	public boolean startUpdateInfo() throws Throwable {
		updateInfoButton.click();
		UpdateInfoPage updateInfo = new UpdateInfoPage(driver, enviroment);
		updateInfo.startUpdateInfo();
		
		Sleeper.sleep(15, driver);
		
		swichToFirstFrame(driver);
		swichToIframe(banneriFrame);
		
		return updateInfoButton.getText().equals("COMPLETED");
	}
	
	@Override
	public boolean isPageLoad() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void waitPageIsLoad() throws Exception, InterruptedException, ElementNotFoundException {
		swichToIframe(banneriFrame);
		waitUntilIsLoaded(updateInfoButton);
		waitUntilIsLoaded(electButton);
		waitUntilIsLoaded(enrollButton);
	}

}
