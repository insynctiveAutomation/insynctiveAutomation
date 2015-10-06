package insynctive.pages.insynctive.employee;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;

public class EmployeeDashboardPage extends Page implements PageInterface {

	//Title
	@FindBy(id = "name")
	WebElement fullNameInTitle;
	@FindBy(id = "position")
	WebElement positionInTitle;
	@FindBy(id = "department")
	WebElement departmentInTitle;
	@FindBy(id = "mail")
	WebElement mailInTitle;
	@FindBy(id = "number")
	WebElement phoneNumberInTitle;
	
	//Stats Panel
	@FindBy(id = "open-tasks")
	WebElement openTasksNumber;
	@FindBy(id = "overdue-tasks")
	WebElement overdueTasksNumber;
	@FindBy(id = "questions")
	WebElement openQuestionsNumber;
	
	//Banner Buttons
	@FindBy(id = "buttonFirst")
	WebElement buttonFirstStep;
	@FindBy(id = "buttonSecond")
	WebElement buttonSecondStep;
	@FindBy(id = "buttonThird")
	WebElement buttonThirdStep;
	
	public EmployeeDashboardPage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://" + enviroment + ".insynctiveapps.com/Insynctive.Hub/Protected/Dashboard.aspx?SkipGuide=True";
		this.PAGE_TITLE = "Dashboard";
		PageFactory.initElements(driver, this);
	}

	@Override
	public boolean isPageLoad() {
		return false;
	}
	
}
