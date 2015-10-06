package insynctive.pages.insynctive.employee;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;

public class EmployeeLifeEventsPage  extends Page implements PageInterface {

	@FindBy(id = "add-life-event")
	WebElement addLifeEventButton;
	
	public EmployeeLifeEventsPage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://" + enviroment + ".insynctiveapps.com/Insynctive.Hub/Protected/LifeEvents.aspx?SkipGuide=True";
		this.PAGE_TITLE = "Documents";
		PageFactory.initElements(driver, this);
	}
	
	@Override
	public boolean isPageLoad() {
		// TODO Auto-generated method stub
		return false;
	}
}
