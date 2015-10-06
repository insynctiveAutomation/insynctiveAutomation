package insynctive.pages.insynctive.employee;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import insynctive.pages.Page;

public abstract class EmployeePage extends Page{
	
	public String enviroment;
	
	//TABS Elements
	@FindBy(id = "linkEmployeeDashboard")
	public WebElement dashboardTab;
	@FindBy(id = "linkMyTasks")
	public WebElement taskTab;
	@FindBy(id = "linkMyInfo")
	public WebElement personalTab;
	@FindBy(id = "linkMyDocs")
	public WebElement documentsTab;
	@FindBy(id = "linkMyBenefits")
	public WebElement benefitsTab;
	@FindBy(id = "linkMyLifeEvents")
	public WebElement lifeEventsTab;
	@FindBy(id = "linkAskHR")
	public WebElement askHRTab;
	
	public EmployeePage(WebDriver driver) {
		super(driver);
	}
}
