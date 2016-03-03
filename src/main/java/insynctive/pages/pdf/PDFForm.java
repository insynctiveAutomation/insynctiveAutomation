package insynctive.pages.pdf;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import insynctive.exception.ElementNotFoundException;
import insynctive.exception.TextAreNotEquals;
import insynctive.pages.Page;
import insynctive.utils.Sleeper;

public class PDFForm extends Page {

	@FindBy(id = "pdfWebControl")
	protected WebElement pdfWebiFrame;
	
	@FindBy(id = "linkNext")
	protected WebElement nextPage;
	
	@FindBy(id = "rappdf")
	protected WebElement rapPdfiFrame;
	
	@FindBy(id = "buttonDoneLadda")
	protected WebElement signBtn;
	
	@FindBy(id = "buttonDoneLadda")
	protected WebElement doneBtn;
	
	@FindBy(id = "JQWindowBigOverlay")
	protected WebElement waitingBar;
	
	
	public PDFForm(WebDriver driver) {
		super(driver);
	}
	
	public void setTextInPDFField(Integer msSleepBeforeCommand, WebElement textField, String text) throws Exception {
		try {
			if(msSleepBeforeCommand != null) Sleeper.sleep(msSleepBeforeCommand, driver);
			clickAButton(textField);
			setTextInField(textField.findElement(By.xpath("./input")), text);
		} catch (NullPointerException nEx){
			throw new ElementNotFoundException(getMessageFromWebElement(textField)+" is not found",null);
		}
	}
	
	public void checkInformation(Integer msSleepBeforeCommand, WebElement textField, String text) throws Exception {
		try {
			if(msSleepBeforeCommand != null) Sleeper.sleep(msSleepBeforeCommand, driver);
			WebElement findElement = textField.findElement(By.xpath("./div/div"));
			 if(!isElementTextEquals(findElement, text)){
				throw new TextAreNotEquals(findElement.getText(), text); 
			 }
		} catch (NullPointerException nEx){
			throw new ElementNotFoundException(getMessageFromWebElement(textField)+" is not found",null);
		}
	}
	
	public void checkSSNInformation(Integer msSleepBeforeCommand, WebElement textField, String text) throws Exception {
		try {
			if(msSleepBeforeCommand != null) Sleeper.sleep(msSleepBeforeCommand, driver);
			WebElement findElement = textField.findElement(By.xpath("./div/div"));
			List<WebElement> ssnList = textField.findElements(By.xpath("./div/div"));
			char[] textAsArray = text.toCharArray();
			
			for(Integer index = 0 ; index < textAsArray.length ; index++){
				String indexStringText = String.valueOf(textAsArray[index]);
				String webElementIndexValue = ssnList.get(index).getText();
				
				if(!(indexStringText.equals(webElementIndexValue))){
					throw new TextAreNotEquals(findElement.getText(), text); 
				}
			}
		} catch (NullPointerException nEx){
			throw new ElementNotFoundException(getMessageFromWebElement(textField)+" is not found",null);
		}
	}
	
	public void waitLoadingBar() throws Exception {
		Sleeper.sleep(15000);
		waitUntilnotVisibility(waitingBar);
	}
	
	public void swichToRapPDFFrame() throws IOException, InterruptedException, ElementNotFoundException {
		swichToIframe(rapPdfiFrame);
	}
	
	public void goToNextPage() throws Exception {
		swichToRapPDFFrame();
		clickAButton(nextPage);
	}
	
	public void swichToPDFWebiFrame() throws IOException, InterruptedException, ElementNotFoundException {
		swichToRapPDFFrame();
		swichToIframe(pdfWebiFrame);
	}
	
	public void swichToTaskiFrame() throws IOException, InterruptedException, ElementNotFoundException {
		swichToFirstFrame(driver);
		swichToIframe(taskiFrame);
	}
	
}
