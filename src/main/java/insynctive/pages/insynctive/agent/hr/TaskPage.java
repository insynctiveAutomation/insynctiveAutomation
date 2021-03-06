package insynctive.pages.insynctive.agent.hr;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;

public class TaskPage extends Page implements PageInterface {

	String environment;
	
	@FindBy(id = "statusesMenu")
	WebElement statusesMenu;
	
	public TaskPage(WebDriver driver, String environment) {
		super(driver);
		this.environment = environment;
		this.PAGE_URL = "http://" + environment + ".insynctiveapps.com/Insynctive.Hub/Protected/HrTasks.aspx?SkipGuide=True";
		this.PAGE_TITLE = "Checklists";
		PageFactory.initElements(driver, this);
	}
	
	@Override
	public boolean isPageLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void waitPageIsLoad() throws Exception {
		waitUntilIsLoaded(statusesMenu);
	}
	

}
