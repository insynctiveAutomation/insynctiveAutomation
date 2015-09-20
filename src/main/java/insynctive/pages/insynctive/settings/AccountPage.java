package insynctive.pages.insynctive.settings;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;

public class AccountPage extends Page implements PageInterface {

	String enviroment;

	@FindBy(id = "companyNameContainer")
	WebElement companyNameContainer;
	@FindBy(id = "accountUrlContainer")
	WebElement accountUrlContainer;
	@FindBy(id = "helpDeskEmailContainer")
	WebElement helpDeskEmailContainer;
	@FindBy(id = "accountVersion")
	WebElement accountVersion;
	@FindBy(id = "createdOnContainer")
	WebElement createdOnContainer;
	
	@Override
	public boolean isPageLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public AccountPage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://" + enviroment + ".insynctiveapps.com/Insynctive.Hub/Protected/AdminSystem.aspx?mode=c&SkipGuide=True";
		this.PAGE_TITLE = "Checklists";
		PageFactory.initElements(driver, this);
	}
	
	@Override
	public void waitPageIsLoad() throws Exception {
		waitUntilIsLoaded(companyNameContainer);
		waitUntilIsLoaded(accountUrlContainer);
		waitUntilIsLoaded(helpDeskEmailContainer);
		waitUntilIsLoaded(accountVersion);
		waitUntilIsLoaded(createdOnContainer);
	}
}
