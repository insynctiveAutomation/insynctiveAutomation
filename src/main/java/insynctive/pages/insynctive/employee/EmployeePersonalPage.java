package insynctive.pages.insynctive.employee;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import insynctive.pages.PageInterface;
import insynctive.pages.PersonalPage;

public class EmployeePersonalPage extends PersonalPage implements PageInterface {

	public EmployeePersonalPage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://" + enviroment + ".insynctiveapps.com/Insynctive.Hub/Protected/Personal.aspx?SkipGuide=True";
		this.PAGE_TITLE = "Personal";
		PageFactory.initElements(driver, this);
	}

	@Override
	public boolean isPageLoad() {
		// TODO Auto-generated method stub
		return false;
	}

}
