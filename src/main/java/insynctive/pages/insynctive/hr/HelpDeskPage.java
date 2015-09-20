package insynctive.pages.insynctive.hr;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;

public class HelpDeskPage extends Page implements PageInterface {

	String enviroment;
	
	@FindBy(id = "body_body_callbackPanel_btnAddQuestion_CD")
	WebElement logAQuestionBtn;
	@FindBy(id = "body_body_callbackPanel_lnkMailToHRAdmin")
	WebElement sendEmailToLink;
	
	
	@Override
	public boolean isPageLoad() {
		// TODO Auto-generated method stub
		return false;
	}

	public HelpDeskPage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://" + enviroment + ".insynctiveapps.com/Insynctive.Hub/Protected/HrAdminHelpDesk.aspx?SkipGuide=True";
		this.PAGE_TITLE = "Checklists";
		PageFactory.initElements(driver, this);
	}
	
	@Override
	public void waitPageIsLoad() throws Exception {
		waitUntilIsLoaded(logAQuestionBtn);
		waitUntilIsLoaded(sendEmailToLink);
	}
}
