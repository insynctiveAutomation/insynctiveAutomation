package insynctive.pages.insynctive.hr;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;

public class TaskPage extends Page implements PageInterface {

	String enviroment;
	
	@FindBy(css = "#statusesList > li:nth-child(1)")
	WebElement myTaskTab;
	@FindBy(css = "#statusesList > li:nth-child(2)")
	WebElement allTaskTab;
	@FindBy(id = "cboSortBy")
	WebElement mostRecentStartCombo;
	@FindBy(id = "select-option")
	WebElement pendingTaskCombo;
	
	public TaskPage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://" + enviroment + ".insynctiveapps.com/Insynctive.Hub/Protected/HrTasks.aspx?SkipGuide=True";
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
		waitUntilIsLoaded(myTaskTab);
		waitUntilIsLoaded(allTaskTab);
		waitUntilIsLoaded(mostRecentStartCombo);
		waitUntilIsLoaded(pendingTaskCombo);
	}
	

}
