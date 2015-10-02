package insynctive.pages.insynctive.settings;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;

public class OtherSettingPage extends Page implements PageInterface {

	String enviroment;

	@FindBy(id = "btnEmailSetup_CD")
	WebElement emailSetupBtn;
	@FindBy(id = "mailBoxFrame")
	WebElement mailBoxFrame;

	@Override
	public boolean isPageLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public OtherSettingPage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://" + enviroment + ".insynctiveapps.com/Insynctive.Hub/Protected/V3Settings.aspx?mode=c&SkipGuide=True";
		this.PAGE_TITLE = "Checklists";
		PageFactory.initElements(driver, this);
	}
	
	@Override
	public void waitPageIsLoad() throws Exception {
		swichToIframe(mailBoxFrame);
		waitUntilIsLoaded(emailSetupBtn);
		swichToFirstFrame(driver);
	}
}
