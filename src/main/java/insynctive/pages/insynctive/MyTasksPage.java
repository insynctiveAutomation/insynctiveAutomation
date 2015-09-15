package insynctive.pages.insynctive;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;
import insynctive.utils.Sleeper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyTasksPage extends Page implements PageInterface {

	String enviroment;

	@FindBy(id = "body_body_tasksListN_row0_lnkTaskName_0")
	WebElement firstTask;
	@FindBy(id = "body_body_tasksListN_row0_lnkDate_0")
	WebElement DateOfTask;
	
	public MyTasksPage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://"
				+ enviroment
				+ ".insynctiveapps.com/Insynctive.Hub/Protected/MyTasks.aspx?view=agent";
		this.PAGE_TITLE = "MyTasks";
		PageFactory.initElements(driver, this);
	}

	public boolean SingPDF() throws Exception {
		return new PDFTaskPage(driver).signPDF();
	}
	
	public void openJustNowTask() throws InterruptedException {
		turnOnImplicitWaits(5);
		for (int a = 0; a < 5; a++) {
			if (DateOfTask.getText().equals("Just Now")) {
				firstTask.click();
				break;
			} else {
				Sleeper.sleep(3000, driver);
				this.loadPage();
			}
		}
		turnOffImplicitWaits();
	}

	@Override
	public boolean isPageLoad() {
		return isElementPresent(firstTask);
	}

	@Override
	public void waitPageIsLoad() throws Exception {
		waitUntilIsLoaded(firstTask);
	}

}
