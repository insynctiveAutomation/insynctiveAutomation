package insynctive.pages.insynctive.settings;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;

public class V3SettingsPage extends Page implements PageInterface {

	String enviroment;

	@FindBy(id = "tds_body_mainTab_btnEditEmployeeNotificationsSettings_B")
	WebElement editBtn;
	@FindBy(id = "tds_body_mainTab_AT0T")
	WebElement employeeTab;
	@FindBy(id = "tds_body_mainTab_T1T")
	WebElement agentTab;
	@FindBy(id = "tds_body_mainTab_T2T")
	WebElement templateTab;
	@FindBy(id = "tds_tdLeftMenu")
	WebElement menuList;

	@Override
	public boolean isPageLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public V3SettingsPage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://" + enviroment + ".insynctiveapps.com/Insynctive.Hub/Protected/AdminNotifications.aspx?mode=c&SkipGuide=True";
		this.PAGE_TITLE = "Checklists";
		PageFactory.initElements(driver, this);
	}
	
	@Override
	public void waitPageIsLoad() throws Exception {
		waitUntilIsLoaded(editBtn);
		waitUntilIsLoaded(employeeTab);
		waitUntilIsLoaded(agentTab);
		waitUntilIsLoaded(templateTab);
		waitUntilIsLoaded(menuList);
	}
}
