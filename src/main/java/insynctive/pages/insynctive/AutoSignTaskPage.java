package insynctive.pages.insynctive;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;
import insynctive.utils.Sleeper;

//This is the implementation with DocuSign
public class AutoSignTaskPage extends Page implements PageInterface {
	
	@FindBy(id = "buttonDoneLadda")
    WebElement signButton;
	
	@FindBy(id = "adopt-and-sign")
	WebElement adoptSignatureButton;
	
	@FindBy(className = "standard-popover pop bottom-left in webui-no-padding")
	WebElement adoptPopover;
	
	@FindBy(id = "JQWindowBigOverlay")
	WebElement taskBigOverlay;
	
	@FindBy(id = "frmTask")
	WebElement taskIframe;
	
	@FindBy(id = "rappdf")
	WebElement radPDFIframe;
	
	public AutoSignTaskPage(WebDriver driver) {
		super(driver);
		this.PAGE_URL = "NO URL";
		this.PAGE_TITLE = "Checklists";
		PageFactory.initElements(driver, this);
	}

	@Override
	public boolean isPageLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean SignDocument () throws Exception
	{
		boolean result  = false;
		this.swichToIframe(taskIframe);
		this.waitUntilIsLoaded(signButton);
		this.waitUntilIsLoaded(radPDFIframe);
		Sleeper.sleep(3000, driver);
		this.clickAButton(signButton);
		Sleeper.sleep(1000, driver);
		if(this.isElementPresent(adoptPopover))
		{
			this.waitUntilIsLoaded(adoptSignatureButton);
			this.clickAButton(adoptSignatureButton);
			this.waitUntilnotVisibility(adoptPopover);

		}
		Sleeper.sleep(1000, driver);
		this.clickAButton(signButton); //To make the task done
		this.waitUntilnotVisibility(taskBigOverlay);
		if(this.isElementPresent(radPDFIframe)==false)
		{
			result = true;
		}
		
		return result;
	}

	

}