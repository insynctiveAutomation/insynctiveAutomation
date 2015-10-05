package insynctive.pages.insynctive;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.exception.ConfigurationException;
import insynctive.pages.Page;
import insynctive.pages.PageInterface;
import insynctive.utils.data.App;

public class MarketPage extends Page implements PageInterface {

	JavascriptExecutor jsExecutor;
	
	/* Market */
	@FindBy(id = "ASPxImageSlider1")
	WebElement slider;
	@FindBy(css = "#ctl01 > div.page > table")
	WebElement appsTable;

	/* App PopUp */
	@FindBy(id = "pcWindow_CIF-1")
	WebElement appIframe;
	@FindBy(id = "pcWindow_PW-1")
	WebElement appPopUp;
	@FindBy(id = "callbackDetails_lblAppName")
	WebElement labelAppName;
	@FindBy(id = "callbackDetails_btnShop_CD")
	WebElement installButton;

	/* Install PopUp */
	@FindBy(id = "callbackDetails_popupShop_PW-1")
	WebElement installPopUp;
	@FindBy(name = "callbackDetails$popupShop$pnlShop$txtShopAccount")
	WebElement accountField;
	@FindBy(name = "callbackDetails$popupShop$pnlShop$txtShopLogin")
	WebElement loginField;
	@FindBy(name = "callbackDetails$popupShop$pnlShop$txtShopPassword")
	WebElement passwordField;
	@FindBy(id = "callbackDetails_popupShop_pnlShop_btnLogin")
	WebElement loginAndInstallButton;
	@FindBy(id = "callbackDetails_popupShopResult_PW-1")
	WebElement popUpResult;
	@FindBy(id = "callbackDetails_popupShopResult_lblResult-1T")
	WebElement resultText;

	public MarketPage(WebDriver driver) throws ConfigurationException {
		super(driver);
		jsExecutor = (JavascriptExecutor) driver;
		this.PAGE_URL = "http://appsmarket.insynctive.com/";
		this.PAGE_TITLE = "Home Page";
		PageFactory.initElements(driver, this);
	}

	/* Actions */
	public void installApp(App app, String enviroment, String userName, String password) throws Exception {
		openAppPopUp(app);
		openInstallPopUp();
		installApp(enviroment, userName, password);
	}

	/* Waits * */
	public void waitPageIsLoad() throws Exception {
		waitUntilIsLoaded(slider);
		waitUntilIsLoaded(appsTable);
	}

	public void waitAppPopUpLoad() throws Exception {
		waitUntilIsLoaded(labelAppName);
		waitUntilIsLoaded(installButton);
	}

	public void waitInstallPopUpLoad() throws Exception {
		waitUntilIsLoaded(installPopUp);
		waitUntilIsLoaded(passwordField);
		waitUntilIsLoaded(loginAndInstallButton);
	}

	/* Private Actions * */
	private void openAppPopUp(App app) throws Exception {
		this.PAGE_URL = "http://appsmarket.insynctive.com/AppDetails.aspx?AppKey="
				+ app.getId();
		loadPage();
		waitAppPopUpLoad();
	}

	private void openInstallPopUp() throws Exception {
		installButton.click();
		waitInstallPopUpLoad();
	}

	private void setText_AccountField(String text) {
		accountField.clear();
		accountField.sendKeys(text);
	}

	private void setText_LoginField(String text) {
		loginField.clear();
		loginField.sendKeys(text);
	}
	
	private void setText_PasswordField(String text) {
		passwordField.clear();
		passwordField.sendKeys(text);
	}

	private void installApp(String enviroment, String userName, String password) throws Exception {
		setText_AccountField(enviroment+".insynctiveapps.com");
		setText_LoginField(userName);
		setText_PasswordField(password);
		loginAndInstallButton.click();
		waitUntilIsLoaded(popUpResult);
	}
	

	/* Checks * */
	public boolean isPageLoad() {
		return slider.isDisplayed() 
				&& appsTable.isDisplayed();
	}

	public boolean isAppPopUpLoad(App app) {
		return labelAppName.getText().contains(app.name());
	}

	public boolean isAppInstallSuccessfully() {
		return resultText.getText().equals(
				"The application was installed successfully.");
	}
}
