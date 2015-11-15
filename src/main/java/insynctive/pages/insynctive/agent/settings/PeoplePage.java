package insynctive.pages.insynctive.agent.settings;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;
import insynctive.utils.Sleeper;

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

	@FindBy(id = "SecurityTab")
	WebElement securityTab;
	
	@FindBy(id = "ActiveForAgent")
	WebElement forAgentBox;
	@FindBy(id = "SendOneTimeCodeViaForAgent")
	WebElement forAgentComboType;
	@FindBy(id = "VerificationPeriodForAgent")
	WebElement forAgentVerifyTimeDays;
	
	@FindBy(id = "ActiveForEmployee")
	WebElement forEmployeeBox;
	@FindBy(id = "SendOneTimeCodeViaForEmployee")
	WebElement forEmployeeComboType;
	@FindBy(id = "VerificationPeriodForEmployee")
	WebElement forEmployeeVerifyTimeDays;
	
	@FindBy(id = "btnSave2FA")
	WebElement btnSave2FA;
	
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
		waitUntilIsLoaded(resetEmailLink);
		waitUntilIsLoaded(createPasswordNowLink);
	}
	
	public void configure2FaForAgent(boolean turnOn, String codeVia) throws Exception{
		if(turnOn){
			clickAButton(forAgentBox);
			selectElementInDefaultCombo(forAgentComboType, codeVia);
			setTextInField(forAgentVerifyTimeDays, "0");
		} else {
			clickAButton(forAgentBox);
		}
	}
	
	public void configure2FaForEmployee(boolean turnOn, String codeVia) throws Exception{
		if(turnOn){
			clickAButton(forEmployeeBox);
			selectElementInDefaultCombo(forEmployeeComboType, codeVia);
			setTextInField(forEmployeeVerifyTimeDays, "0");
		} else {
			clickAButton(forEmployeeBox);
		}
	}
	
	public void save2FA() throws Exception{
		clickAButton(btnSave2FA);
		Sleeper.sleep(4000, driver);
	}
	

}
