package insynctive.pages.insynctive.apps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;

public class AppsPage extends Page implements PageInterface{

	String enviroment;
	String emailSubject;

	/* Apps */
	@FindBy(id = "appSearch")
	WebElement appSearch;
	@FindBy(id = "install-title")
	WebElement installTitle;
	@FindBy(id = "app-market-button")
	WebElement marketButton;
	@FindBy(id = "main-apps-container")
	WebElement appsContainer;

	public AppsPage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://" + enviroment
				+ ".insynctiveapps.com/Insynctive.Hub/Protected/Apps.aspx";
		this.PAGE_TITLE = "Apps";
		PageFactory.initElements(driver, this);
	}
	
	/* Actions **/
	public void goToMarket() throws Exception {
		waitPageIsLoad();
		clickAButton(marketButton);
	}

	/* Waits **/
	public void waitPageIsLoad() throws Exception {
		waitUntilIsLoaded(appSearch);
		waitUntilIsLoaded(installTitle);
		waitUntilIsLoaded(marketButton);
		waitUntilIsLoaded(appsContainer);
	}

	/* Private Actions **/
	
	
	/* Checks **/
	public boolean isPageLoad() {
		return appsContainer.isDisplayed() 
				&& installTitle.isDisplayed() 
				&& marketButton.isDisplayed() 
				&& appsContainer.isDisplayed();
	}
}
