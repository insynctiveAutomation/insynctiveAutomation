package insynctive.pages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import insynctive.exception.ElementIsAllwaysVisibleException;
import insynctive.exception.ElementNotFoundException;
import insynctive.exception.MethodNoImplementedException;
import insynctive.exception.WrongMessageException;
import insynctive.utils.Sleeper;

public class Page {

	public static int SELENIUM_TIMEOUT_SEC = 30;
    public WebDriver driver;
    public String PAGE_URL;
    public String PAGE_TITLE;
    public String enviroment;
    
    public JavascriptExecutor jsx;
	
    
    /* InApp */
	@FindBy(css = "#jqxNotificationDefaultContainer-top-right > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > div:nth-child(2)")
	public WebElement inAppNotification;
	
	@FindBy(css = "#jqxNotificationDefaultContainer-top-right > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(3) > div:nth-child(1)")
	public WebElement closeInAppNotification;

	@FindBy(id = "popupCustom_CIF-1")
	public WebElement taskPopup;
	
	@FindBy(id = "frmTask")
	public WebElement taskiFrame;
	
	@FindBy(id = "loadingSpinner")
	public WebElement loadingSpinner;

	@FindBy(id = "arrowImage")
	public WebElement arrowImage;
	
	@FindBy(id = "lblName")
	public WebElement labelNameInHeader;
	
	@FindBy(id = "imgPhoto")
	public WebElement imgPhotoInHeader;
	
	@FindBy(id = "popupAccount_linkLogout")
	public WebElement singoutLink;
	
	@FindBy(xpath = "//div[contains(@id, 'JQWindowBigOverlay')]//div[contains(@class, 'jqx-window-close-button-background')]//div[contains(@class, 'jqx-window-close-button')]")
	public WebElement closeOverlayBtn; 
    
    public Page(){
    	
    }
    
    public Page(WebDriver driver) {
        this.driver = driver;
        jsx = (JavascriptExecutor)driver;
        PageFactory.initElements(driver, this);
    }

    public void swichToIframe(WebElement iframe) throws IOException, InterruptedException, ElementNotFoundException{
    	try{
    		waitUntilIsLoaded(iframe);
    		driver.switchTo().frame(iframe);
    	} catch (Exception ex){
    		throw new ElementNotFoundException(getMessageFromWebElement(iframe) +" is not found.", null);
    	}
    }
    
    public static boolean exists(WebElement element) {
        try {
            element.isDisplayed();
            return true;
        } catch (org.openqa.selenium.NoSuchElementException ignored) {
            return false;
        }
    }

    public String getPageUrl() {
        return PAGE_URL;
    }

    public String getPageTitle() {
        return PAGE_TITLE;
    }

    public void loadPage() {
        driver.get(getPageUrl());
    }

    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void setElementText(WebElement element, String text) throws ElementNotFoundException {
        try{
        	element.clear();
        	element.sendKeys(text);
        	assertEquals(element.getAttribute("value"), text);
        } catch (NullPointerException nEx){
        	throw new ElementNotFoundException(getMessageFromWebElement(element)+" is NULL", null);
        }
    }

    public void assertElementText(WebElement element, String text) {
        assertEquals(element.getAttribute("value"), text);
    }

    public void checkIfElementVisible(WebElement element) {
        assertTrue("element isn't found", element.isDisplayed());
    } 

    public String getDriverTitle() {
        return driver.getTitle();
    }

    public void waitUntilIsLoaded(WebElement element, Integer segs) throws IOException, InterruptedException, ElementNotFoundException {
       try{
    	   new WebDriverWait(driver, segs).until(ExpectedConditions.visibilityOf(element));
       } catch (Exception ex){
    	   throw new ElementNotFoundException(getMessageFromWebElement(element)+" is not found", null);
       }
    }
    
    public void waitUntilIsLoaded(WebElement element) throws IOException, InterruptedException, ElementNotFoundException {
       try{
    	   new WebDriverWait(driver, SELENIUM_TIMEOUT_SEC).until(ExpectedConditions.visibilityOf(element));
       } catch (Exception ex){
    	   throw new ElementNotFoundException(getMessageFromWebElement(element)+" is not found", null);
       }
    }

    public void waitUntilnotVisibility(WebElement element) throws IOException, InterruptedException, Exception {
    	int times = 0;
    	try{
    		while(element != null && times < 10){
    			Sleeper.sleep(2, driver);
    			times++;
    		}
    	} catch (Exception ex){
    		throw new ElementIsAllwaysVisibleException("The element "+getMessageFromWebElement(element)+" is still visible", null);
    	}
    }

    public void waitUntilNameIsVisible(WebElement element, String name) throws IOException, InterruptedException, ElementIsAllwaysVisibleException {
    	int times = 0;
    	try{
    		while(!element.getText().equals(name) && times < 10){
    			Sleeper.sleep(2, driver);
    			times++;
    		}
    	} catch (Exception ex){
    		throw new ElementIsAllwaysVisibleException("The text "+element.getText()+" is different from "+name, null);
    	}
    }

    public void waitUntilTitleContains(String str, Integer segs) throws IOException, InterruptedException {
    	new WebDriverWait(driver, segs).until(ExpectedConditions.titleContains(str));
    }
    
    public void waitUntilTitleContains(String str) throws IOException, InterruptedException {
    	new WebDriverWait(driver, SELENIUM_TIMEOUT_SEC).until(ExpectedConditions.titleContains(str));
    }

    public void selectValueInDropdown(WebElement dropdown, String value) {
        Select select = new Select(dropdown);
        select.selectByValue(value);
    }

    public boolean isElementPresent(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementExisting(WebElement we) {
        try {
            we.isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected void turnOffImplicitWaits() {
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    protected void turnOnImplicitWaits(int time) {
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }

    protected boolean isElementHiddenNow(String locator) {
        turnOffImplicitWaits();
        boolean result = false;
        try {
            result = ExpectedConditions.invisibilityOfElementLocated(By.id(locator)).apply(driver);
        } finally {
            turnOnImplicitWaits(5);
        }
        return result;
    }

    public boolean isElementPresentbySize(String locator) {
        return driver.findElements(By.id(locator)).size() > 0;
    }

    public boolean isElementDisplayedNowOLD(WebElement element) {
        turnOffImplicitWaits();
        try {
            return element.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        } finally {
            turnOnImplicitWaits(5);
        }
    }
    
    public void swichToFirstFrame(WebDriver driver){
    	driver.switchTo().defaultContent();
    }
    
    public void clickAButton(WebElement element) throws Exception {
    	clickAButton(element, null);
    }
    
    public void clickAButton(WebElement element, Integer msSleepAfterCommand) throws Exception {
    	waitUntilIsLoaded(element);
    	element.click();
    	if(msSleepAfterCommand != null) Sleeper.sleep(msSleepAfterCommand, driver);
    }
    
    public void clickAButton(Integer msSleepBeforeCommand, WebElement element) throws Exception {
    	waitUntilIsLoaded(element);
    	if(msSleepBeforeCommand != null) Sleeper.sleep(msSleepBeforeCommand, driver);
    	element.click();
    }
    
    public void setTextInField(WebElement textField, String text) throws ElementNotFoundException, IOException, InterruptedException {
    	try {
			waitUntilIsLoaded(textField);
			textField.clear();
			textField.sendKeys(text);
		} catch (NullPointerException nEx){
			throw new ElementNotFoundException(getMessageFromWebElement(textField)+" is not found",null);
		}
    }
    
	public void setTextInField(WebElement textField, String text, Integer msSleepAfterCommand) throws ElementNotFoundException, IOException, InterruptedException {
		try {
			setTextInField(textField, text);
			if(msSleepAfterCommand != null) Sleeper.sleep(msSleepAfterCommand, driver);
		} catch (NullPointerException nEx){
			throw new ElementNotFoundException(getMessageFromWebElement(textField)+" is not found",null);
		}
	}
    
	public void setTextInField(Integer msSleepBeforeCommand, WebElement textField, String text) throws ElementNotFoundException, IOException, InterruptedException {
		try {
			if(msSleepBeforeCommand != null) Sleeper.sleep(msSleepBeforeCommand, driver);
			setTextInField(textField, text);
		} catch (NullPointerException nEx){
			throw new ElementNotFoundException(getMessageFromWebElement(textField)+" is not found",null);
		}
	}
    
	public void setTextInCombo(WebElement combo, String text) throws ElementNotFoundException, IOException, InterruptedException {
		try{
			waitUntilIsLoaded(combo);
			combo.sendKeys(text);
			Sleeper.sleep(1000, driver);
			combo.sendKeys("\n");
		} catch (NullPointerException nEx){
			throw new ElementNotFoundException(getMessageFromWebElement(combo)+" is not found",null);
		}
	}
	
	public boolean selectElementInComboLi(WebElement combo, String text) throws Exception{
		return selectElementInCombo(combo, text, "li");
	}
	
	public boolean selectElementInComboOption(WebElement combo, String text) throws Exception{
		return selectElementInCombo(combo, text, "option");
	}

	public boolean selectElementInComboOption(WebElement combo, String text, Integer msSleepAfterCommand) throws Exception{
		Boolean result = selectElementInCombo(combo, text, "option");
		if(msSleepAfterCommand != null) Sleeper.sleep(msSleepAfterCommand, driver);
		return result;
	}
	
	public boolean selectElementInDefaultCombo(WebElement combo, String text){
		new Select(combo).selectByVisibleText(text);
		return true;
	}

	public boolean selectElementInCombo(WebElement combo, String text, String typeOfContainer) throws Exception {
		try{
			clickAButton(combo);
			clickAButton((driver.findElement(By.xpath("//"+typeOfContainer+"[contains(text(),'"+text+"')]" ))));
			return true;
		} catch (NullPointerException nEx){
			throw new ElementNotFoundException(getMessageFromWebElement(combo)+" is not found",null);
		} catch (org.openqa.selenium.NoSuchElementException nSEx){
			return false;
		}
	}
	
	public boolean selectElementInComboByIndex(WebElement combo, Integer index, String typeOfContainer) throws Exception{
		try{
			clickAButton((driver.findElement(By.xpath("//"+typeOfContainer+"["+index+"]" ))));
			return true;
		} catch (NullPointerException nEx){
			throw new ElementNotFoundException(getMessageFromWebElement(combo)+" is not found",null);
		} catch (org.openqa.selenium.NoSuchElementException nSEx){
			return false;
		}
	}
	
	public boolean selectElementInComboWithoutClickCombo(WebElement combo, String text, String typeOfContainer) throws Exception {
		try{
			clickAButton((driver.findElement(By.xpath("//"+typeOfContainer+"[contains(text(),'"+text+"')]" ))));
			return true;
		} catch (NullPointerException nEx){
			throw new ElementNotFoundException(getMessageFromWebElement(combo)+" is not found",null);
		} catch (org.openqa.selenium.NoSuchElementException nSEx){
			return false;
		}
	}
	
	public void checkInAppMessage(String assertMessage) throws Exception {
		swichToFirstFrame(driver);
		waitUntilIsLoaded(inAppNotification);
		String ErrorMessage = "InApp message Do not match > InApp<"+inAppNotification.getText()+"> Expected<"+assertMessage+">";
		if(!inAppNotification.getText().equals(assertMessage)) throw new WrongMessageException(ErrorMessage, null);	
		clickAButton(closeInAppNotification);
	}
	
	public WebElement findElementByText(String tagName, String text) throws ElementNotFoundException{
		WebElement element = null;
		int times = 1;
		boolean notFound = true;
		while(times <= 20 && notFound){
			try{
				element = driver.findElement(By.xpath("//"+tagName+"[contains(text(), '"+text+"')]"));
				notFound = false;
			} catch(Exception ex) {
				times++;
				Sleeper.sleep(500, driver);
			}
		}
		if(element == null){throw new ElementNotFoundException("<"+tagName+"> "+ text + " <"+tagName+"> is not found", null);}
		return element;
	}
	
	public WebElement findElementByTextInList(List<WebElement> elements, String text) throws ElementNotFoundException{
		WebElement returnElement = null;
		for(WebElement element : elements){
			try{ 
				returnElement = element.findElement(By.xpath("./div[contains(text(), '"+text+"')]"));
				break;
			} catch(Exception ex){ }
		}
		if(returnElement == null){throw new ElementNotFoundException("Text:"+text+ "in "+elements+" is not found", null);}
		return returnElement;
		
	}

	
	public WebElement findElementByTagAndClass(String tagName, String clazz) throws ElementNotFoundException{
		WebElement element = null;
		int times = 1;
		boolean notFound = true;
		while(times <= 20 && notFound){
			try{
				//div[contains(concat(' ', text(), ' '), ' File ')]
				element = driver.findElement(By.xpath("//"+tagName+"[contains(@class, '"+clazz+"')]"));
				notFound = false;
			} catch(Exception ex) {
				times++;
				Sleeper.sleep(500, driver);
			}
		}
		if(element == null){throw new ElementNotFoundException("<"+tagName+"class="+clazz+"> is not found", null);}
		return element;
	}
	
	public List<WebElement> findElementsWithTag(String tagName) throws ElementNotFoundException{
		List<WebElement> elements = null;
		int times = 1;
		boolean notFound = true;
		while(times <= 20 && notFound){
			try{
				elements = driver.findElements(By.xpath("//"+tagName));
				notFound = false;
			} catch(Exception ex) {
				times++;
				Sleeper.sleep(500, driver);
			}
		}
		if(elements == null){throw new ElementNotFoundException(tagName+"elements are not found", null);}
		return elements;
	}
	
	public String getMessageFromWebElement(WebElement element){
		String[] elementSplit = element.toString().split("-> ");
		try{
			return (elementSplit.length > 0) ? 
					(elementSplit[1].split("]").length > 0 ? 
							elementSplit[1].split("]")[0]+"]" : elementSplit[1]) 
					: element.toString(); 
		} catch (Exception ex){
			return element.toString();
		}
	}
	
	public void logout() throws Exception{
		swichToFirstFrame(driver);
		clickAButton(arrowImage);
		clickAButton(singoutLink);
	}
	
	public boolean isElementTextEquals(WebElement element, String lastName) throws IOException, InterruptedException, ElementNotFoundException {
		waitUntilIsLoaded(element);
		return element.getText().equals(lastName);
	}
	
	public boolean isElementValueEquals(WebElement element, String lastName) throws IOException, InterruptedException, ElementNotFoundException {
		waitUntilIsLoaded(element);
		return element.getAttribute("value").equals(lastName);
	}
	
	public void waitPageIsLoad() throws Exception {
		throw new MethodNoImplementedException("waitPageIsLoad is not implemented");
	}
	
	public boolean equals(WebElement elem, String text){
		return elem.getText().equals(text);
	}
	
	public long getTimeToLoad() throws Exception{
		loadPage();
		long startTime = System.nanoTime();
		waitPageIsLoad();
		long endTime = System.nanoTime();
		return (endTime-startTime)/1000000;
	}
	
	public void closeBigOverlay() throws Exception{
		swichToFirstFrame(driver);
		clickAButton(closeOverlayBtn);
	}


}