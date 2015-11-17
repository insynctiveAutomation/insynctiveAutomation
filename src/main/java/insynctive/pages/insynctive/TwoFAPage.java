package insynctive.pages.insynctive;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;
import insynctive.utils.MailManager;
import insynctive.utils.Sleeper;
import insynctive.utils.TwilioUtil;

public class TwoFAPage extends Page implements PageInterface {

	@FindBy(id = "SendOneTimeCodeViaEmail")
	WebElement sendViaPrimaryEmailBox;
	@FindBy(id = "SendOneTimeCodeViaText")
	WebElement sendViaPhone;

	@FindBy(id = "txtVerificationCode")
	WebElement verificationCodeInput;
	
	@FindBy(id = "btnSendVerificationCode")
	WebElement sendVerificationBtn;
	@FindBy(id = "btnCheckVerificationCode")
	WebElement checkVerificationCode;
	
	public TwoFAPage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://" + enviroment + ".insynctiveapps.com/Insynctive.Hub/TwoFA/ChannelSelect?mode=c&SkipGuide=True";
		this.PAGE_TITLE = "Authentication";
		PageFactory.initElements(driver, this);
	}
	
	@Override
	public boolean isPageLoad() {
		// TODO Auto-generated method stub
		return false;
	}

	public void sendViaPrimaryEmail(String gmailEmail, String gmailPassword) throws Exception {
		clickAButton(sendViaPrimaryEmailBox);
		clickAButton(sendVerificationBtn);

		String verificationCode = MailManager.getVerificationCode(gmailEmail, gmailPassword);
		
		setTextInField(verificationCodeInput, verificationCode);
		clickAButton(checkVerificationCode);
	}

	public void sendViaPhone() throws Exception {
		clickAButton(sendViaPhone);
		clickAButton(sendVerificationBtn);

		Sleeper.sleep(5000, driver);
		String verificationCode = TwilioUtil.getVerificationCode();
		
		setTextInField(verificationCodeInput, verificationCode);
		clickAButton(checkVerificationCode);
	}
	
	
}
