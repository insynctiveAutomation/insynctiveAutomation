package insynctive.pages.insynctive.employee;

import insynctive.model.ParamObject;
import insynctive.pages.PersonalPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;
import org.testng.Assert;

public class EmployeeDocumentsPage extends Page implements PageInterface {

	@FindBy(className = "doc-search-name searchTextInput")
	WebElement searchNameInput;
	@FindBy(className = "doc-search-person searchTextInput")
	WebElement searchUpdatedInput;
	@FindBy(className = "grid-icon-eye")
	WebElement viewBtn;
	@FindBy(className = "doc-name")
	WebElement docNameCls;
	@FindBy(xpath = "//div[@class='doc-name']/div[1]")
	WebElement docName;
	@FindBy(xpath = "//div[@class='doc-name']/div[2]")
	WebElement docCategory;

	public EmployeeDocumentsPage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://" + enviroment + ".insynctiveapps.com/Insynctive.Hub/Protected/Documents.aspx?SkipGuide=True";
		this.PAGE_TITLE = "Documents";
		PageFactory.initElements(driver, this);
	}

	@Override
	public boolean isPageLoad() {
		return false;
	}

	public Boolean getDocument(ParamObject document) throws Exception{
		WebElement docTitle = findElementByText("div", document.docName);
		WebElement docCategory = findElementByText("div", document.docCategory);
		return equals(docTitle, document.getDocName()) && equals(docCategory, document.getDocCategory()) ;
	}

	public void openDocument(ParamObject document){
//		return equals(docName, document.getDocName());
	}

	public void searchKeyword(String benefitName) throws Exception {

	}

	public void goToDocumentsPage() throws Exception {
//		waitUntilIsLoaded(Dashboard);
//		clickAButton(Documents);
	}



}
