package insynctive.pages.insynctive.employee;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.exception.ElementNotFoundException;
import insynctive.pages.PageInterface;

public class EmployeeTaskPage extends EmployeePage implements PageInterface {

	@FindBy(id = "pending-count")
	WebElement pendingTasksNumber;
	
	public EmployeeTaskPage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://" + enviroment + ".insynctiveapps.com/Insynctive.Hub/Protected/Tasks.aspx?SkipGuide=True";
		this.PAGE_TITLE = "Tasks";
		PageFactory.initElements(driver, this);
	}
	
	@Override
	public boolean isPageLoad() {
		// TODO Auto-generated method stub
		return false;
	}

	public void openTask(String name) throws ElementNotFoundException, Exception{
		clickAButton(1500, findElementByText("div", name));
	}
	
	public Boolean isTaskPresent(String name) throws ElementNotFoundException, Exception{
		try{
			swichToFirstFrame(driver);
			findElementByText("div", name).getTagName();
			return true;
		} catch(Exception ex) {
			return false;
		}
	}
	
}
