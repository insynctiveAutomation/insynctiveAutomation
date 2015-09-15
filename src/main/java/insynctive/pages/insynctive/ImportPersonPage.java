package insynctive.pages.insynctive;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ImportPersonPage extends Page implements PageInterface{

	//TODO importPersonButton FindBy
	@FindBy(className = "qq-upload-button")
	WebElement importPersonButton;
	@FindBy(id = "pi")
	WebElement pipeline;
	
	public ImportPersonPage(WebDriver driver, String enviroment) {
		super(driver);
		this.PAGE_URL = "http://" + enviroment + ".insynctiveapps.com/Insynctive.Hub/Protected/HRAdminImport.aspx";
		this.PAGE_TITLE = "Import persons";
		PageFactory.initElements(driver, this);
	}
	
	/* Actions **/
	public void importPersons() throws Exception{
		waitPageIsLoad();
//		importPersonButton.sendKeys("C:/Users/Eugenio/Desktop/CPM_census_import.xls");
		importPersonButton.click();
	}

	/* Waits **/
	public void waitPageIsLoad() throws Exception {
		waitUntilIsLoaded(importPersonButton);
	}
	
	/* Private Actions **/
	
	
	/* Checks **/
	public boolean isPageLoad(){
		return importPersonButton.isDisplayed();
	}

}
