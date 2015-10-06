package insynctive.pages.insynctive.employee;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;

public class EmployeeBenefitsPage extends Page implements PageInterface {

	public EmployeeBenefitsPage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://" + enviroment + ".insynctiveapps.com/Insynctive.Hub/Protected/Documents.aspx?SkipGuide=True";
		this.PAGE_TITLE = "Documents";
		PageFactory.initElements(driver, this);
	}
	
	@Override
	public boolean isPageLoad() {
		// TODO Auto-generated method stub
		return false;
	}
}
