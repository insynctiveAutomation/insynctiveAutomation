package insynctive.pages.insynctive.settings;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PeoplePage extends Page implements PageInterface {

	String enviroment;

	@FindBy(id = "emailPreview")
	WebElement emailPreview;
	@FindBy(id = "AccountActivationTemplateEditor")
	WebElement accountActivationTemplateEditor;
	@FindBy(css = "#emailPreview > p > a")
	WebElement resetEmailLink;
	@FindBy(css = "#emailPreviewContent > div > div.create-password > div")
	WebElement createPasswordNowLink;

	@Override
	public boolean isPageLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public PeoplePage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://" + enviroment + ".insynctiveapps.com/Insynctive.Hub/Protected/AdminSystemPeople.aspx?mode=c&SkipGuide=True";
		this.PAGE_TITLE = "Checklists";
		PageFactory.initElements(driver, this);
	}
	
	@Override
	public void waitPageIsLoad() throws Exception {
		waitUntilIsLoaded(emailPreview);
		waitUntilIsLoaded(accountActivationTemplateEditor);
		waitUntilIsLoaded(resetEmailLink);
		waitUntilIsLoaded(createPasswordNowLink);
	}

}
