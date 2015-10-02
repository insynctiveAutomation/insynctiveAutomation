package insynctive.pages.insynctive;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;

public class ResetPasswordPage extends Page implements PageInterface{

	/* Change Password */
	@FindBy(id = "newPasswordTextBox")
	WebElement newPasswordField;
	@FindBy(id = "confirmPasswordTextBox")
	WebElement confirmPasswordField;
	@FindBy(className = "ButtonSave")
	WebElement saveButton;
	
	
	public ResetPasswordPage(WebDriver driver, String enviroment, String loginToken) {
		super(driver);
		this.PAGE_URL = "https://"+enviroment+".insynctiveapps.com/Insynctive.Hub/Login.aspx?logintoken="+loginToken;
		this.PAGE_TITLE = "Reset Password";
		PageFactory.initElements(driver, this);
	}

	/* Actions **/
	public void changePassword(String newPassword) throws Exception{
		waitPageIsLoad();
		setText_NewPasswordField(newPassword);
		setText_ConfirmPasswordField(newPassword);
		saveButton.click();
	}
	
	/* Waits **/
	public void waitPageIsLoad() throws Exception {
		waitUntilIsLoaded(newPasswordField);
		waitUntilIsLoaded(confirmPasswordField);
		waitUntilIsLoaded(saveButton);
	}
	
	/* Private Action **/
	private void setText_NewPasswordField(String newPassword) {
		newPasswordField.clear();
		newPasswordField.sendKeys(newPassword);
	}
	
	private void setText_ConfirmPasswordField(String newPassword) {
		confirmPasswordField.clear();
		confirmPasswordField.sendKeys(newPassword);
	}
	
	/* Cheks **/
	public boolean isPageLoad(){
		return newPasswordField.isDisplayed() 
				&& confirmPasswordField.isDisplayed() 
				&& saveButton.isDisplayed();
	}
	
	public boolean checkIfEmployeePasswordWasChange(){
		return driver.getTitle().equals("My Questions");
	}

	public boolean checkIfAgentWasCreated(){
		return driver.getTitle().equals("Invitation");
	}
}
