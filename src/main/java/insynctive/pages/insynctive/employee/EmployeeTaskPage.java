package insynctive.pages.insynctive.employee;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.pages.PageInterface;
import insynctive.utils.Sleeper;

public class EmployeeTaskPage extends EmployeePage implements PageInterface {

	@FindBy(id = "pending-count")
	WebElement pendingTasksNumber;
	
	@FindBy(className = "taskHyperlink")
	private
	WebElement firstTask;
	
	@FindAll({@FindBy(className = "taskHyperlink")})
	public List<WebElement> tasks;
	
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

	public String getFirstTask() {
		return firstTask.getText();
	}
	
	public WebElement findTask(String name)
	{
		WebElement result = null;
		for(int i=0; i<3; i++)
		{
			for(WebElement j : tasks)
			{
				if(j.getText().contains(name))
				{
					result = j;
					break;
				}
			}
			if(result != null || i==2) //if you found the task break out 
				break;
			Sleeper.sleep(3000, driver); //wait 3 seconds
			this.loadPage(); //reload the page
		}
		return result;
	}

}