package insynctive.pages.insynctive;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;
import insynctive.utils.Sleeper;


public class LoginPage extends Page implements PageInterface{
	
	/* Account of test */
	String enviroment;
	
	/* Login */
    @FindBy(id = "login_UserName_I")
     WebElement loginUsernameField;
    @FindBy(id = "PasswordLabel")
     WebElement passwordLabel;
    @FindBy(id = "login_Password_I")
    WebElement loginPasswordField;
    @FindBy(id = "login_Login_CD")
    WebElement loginButton;
    @FindBy(id = "google-login")
    WebElement googleLoginButton;
   
    
    public LoginPage(WebDriver driver, String enviroment) {
        super (driver);
        this.enviroment = enviroment;
        this.PAGE_URL = "http://"+ enviroment + ".insynctiveapps.com/Insynctive.Hub/Login.aspx?ReturnUrl=/Insynctive.Hub/Protected/Invitations.aspx?SkipGuide=True";
        this.PAGE_TITLE = "Login";
        PageFactory.initElements(driver, this);
    }
    
    public void setReturnAsEmployee(){
    	this.PAGE_URL = "http://"+ enviroment + ".insynctiveapps.com/Insynctive.Hub/Login.aspx?ReturnUrl=/Insynctive.Hub/Protected/HelpDesk.aspx?SkipGuide=True";
    }

    /* Actions **/
    public void login(String email, String password) throws Exception {
    	loginUsernameField.clear();
       	setTextInField(loginUsernameField, email);
       	setText_PassField(password);
       	clickAButton(loginButton);
    }
    
    public  void clickToLogin() {
        loginButton.click();
    }
    
    /* Waits **/
    public void waitPageIsLoad() throws Exception {
    	waitUntilIsLoaded(loginUsernameField);
    	waitUntilIsLoaded(passwordLabel);
    	waitUntilIsLoaded(loginButton);
    }

    private void setText_PassField(String text) throws Exception {
    	clickAButton(passwordLabel);
        loginPasswordField.sendKeys(text);
    }
    
    /* Cheks **/
    public boolean isPageLoad(){
		return loginButton.isDisplayed() 
				&& loginPasswordField.isDisplayed() 
				&& loginUsernameField.isDisplayed();
	}
    
    public boolean isLoggedIn(Integer maxTimeSegs) throws Exception{
    	boolean isLoggedIn = false;
    	int times = 1;
    	while(times < maxTimeSegs){
    		isLoggedIn = !isElementExisting(loginButton) && !isElementExisting(loginUsernameField) && !isElementExisting(loginPasswordField);
    		if(isLoggedIn){
    			break;
    		} else {
    			Sleeper.sleep(1000, driver);
    			times++;
    		}
    	}
    	return isLoggedIn;
    }

    public boolean isLoggedIn() throws Exception{
    	return isLoggedIn(10);
    }
    
    public  boolean isNotLoggedIn() {
        return driver.findElements(By.xpath("//span[@class='js-auth-signin b-navbar__exit h-ml-10']")).size() > 0;
    }

	public void setReturnURL(String returnURL) {
		this.PAGE_URL = "http://"+ enviroment + ".insynctiveapps.com/Insynctive.Hub/Login.aspx?ReturnUrl="+returnURL+"?SkipGuide=True";
	}
}
