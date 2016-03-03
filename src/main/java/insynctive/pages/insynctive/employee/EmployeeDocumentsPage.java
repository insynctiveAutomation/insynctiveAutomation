package insynctive.pages.insynctive.employee;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;
import insynctive.utils.Sleeper;

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
	@FindBy(id = "bigoverlayiframe")
	WebElement documentIFrame;
	@FindBy(xpath = "//*[@id='viewer1']]")
	WebElement docViewer;
	
	@FindBy(id = "pcc-pageList-viewer1")
	WebElement documentElement;

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

	public Boolean isDocumentInGrid(String documentName, String documentCategory) throws Exception{
		WebElement docTitle = findElementByText("div", documentName);
		WebElement docCategory = findElementByText("div", documentCategory);
		return equals(docTitle, documentName) && equals(docCategory, documentCategory) ;
	}

	public void viewDocument(String documentName) throws Exception{
		clickAButton(2000, getViewButton(documentName));
		swichToIframe(documentIFrame);
	}
	
	//TODO Duplicate code
	public boolean isDocumentOpened() throws Exception {
		Sleeper.sleep(3000, driver);
		swichToFirstFrame(driver);
		swichToIframe(documentIFrame);
		waitUntilIsLoaded(documentElement);
		List<WebElement> pageInDocument = documentElement.findElements(By.xpath(".//div[contains(@class, 'igAnchor')]"));
		return pageInDocument.size() > 0;
	}

	public void searchKeyword(String benefitName) throws Exception {

	}
	
	public WebElement getViewButton (String text){
		String sel = "//div[contains(text(), '"+text+"')]/ancestor::div[3]/div[@class='col-view']//img";
		WebElement element = driver.findElement(By.xpath(sel));
		return element;
	}


}
