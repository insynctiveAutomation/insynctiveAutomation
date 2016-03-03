package insynctive.pages.insynctive;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import insynctive.pages.PDFItemsPage;
import insynctive.pages.PageInterface;
import insynctive.utils.Sleeper;
import insynctive.utils.reader.PDFPropertiesReader;

public class PDFTaskPage extends PDFItemsPage implements PageInterface {

	
	public PDFTaskPage(WebDriver driver) {
		super(driver);
		this.PAGE_URL = "NO URL";
		this.PAGE_TITLE = "Checklists";
		PageFactory.initElements(driver, this);
	}
	
	public boolean signPDF() throws Exception {
		PDFPropertiesReader properties = new PDFPropertiesReader();
		Sleeper.sleep(20000, driver);
		String outerIframe = driver.getWindowHandle();
		
		//Wait Page Load and go to PDF from the basic window
		waitPageIsLoad();
		goToRadPDFiFrame(outerIframe);
		
		//Complete all employee information.
		setPdfText(fullName, fullNameText, properties.getFullName());
		setPdfText(homeAddress, homeAddressText, properties.getHomeAddress());
		setPdfText(cityOrTown, cityOrTownText, properties.getCity());
		setPdfText(state, stateText, properties.getState());
		setPdfText(zipCode, zipCodeText, properties.getZipCode());
		checkABox(getFillingStatus(fillingStatus.valueOf(properties.getFillingStatus())));

		//Go to task frame and click on sign
		goToTaskiFrame(outerIframe);
		signBtn.click();
		
		//Wait sign frame is load, and swich to this. 
		Sleeper.sleep(5000, driver);
		waitUntilIsLoaded(pdfIframe);
		swichToIframe(pdfIframe);
		
		//wait everything is complete and sign.
		waitUntilIsLoaded(signTextType);
		sign("Insynctive test");
		
		//return to radPDF frame.
		swichToIframe(radPdfIframe);
		Sleeper.sleep(10000, driver);
		waitUntilIsLoaded(pdfPage);

		//Click in the on offSet(15,15) from top-left corner.
		Actions act = new Actions(driver);
		act.moveToElement(pdfPage, 15, 15).click().build().perform();
		
		Sleeper.sleep(5000, driver);
		driver.switchTo().window(outerIframe);
		
		//Wait until is signed, change to the task frame and click on Done.
		waitPDFIsSigned();
		DoneButton.click();
		
		//Check if the button disappear, and return if is complete.
		Sleeper.sleep(5000, driver);
		
		driver.switchTo().window(outerIframe);
		boolean isHere = driver.findElements(By.xpath(".//*[@id='buttonDone']/button")).size() > 0;

		return !isHere;
	}

	private void goToTaskiFrame(String outerIframe) throws Exception {
		driver.switchTo().window(outerIframe);
		swichToIframe(taskPopup);
		swichToIframe(taskiFrame);
	}

	private void goToRadPDFiFrame(String outerIframe) throws Exception {
		goToTaskiFrame(outerIframe);
		waitUntilIsLoaded(pdfIframe);
		swichToIframe(pdfIframe);
		waitUntilIsLoaded(radPdfIframe);
		swichToIframe(radPdfIframe);
	}
	
	private void waitPDFIsSigned() throws Exception {
		swichToIframe(taskPopup);
		swichToIframe(taskiFrame);
		waitUntilIsLoaded(DoneButton);
	}

	private void sign(String name) {
		signTextType.sendKeys(name);
		btnApplySignature.click();
	}

	@Override
	public boolean isPageLoad() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void waitPageIsLoad() throws Exception {
		waitUntilIsLoaded(taskPopup);
		swichToIframe(taskPopup);
		
		waitUntilIsLoaded(taskiFrame);
		swichToIframe(taskiFrame);
		
		waitUntilIsLoaded(signBtn);
	}
	
	private void setPdfText(WebElement pdfElement, WebElement input, String text){
		pdfElement.click();
		input.sendKeys(text);
	}
	
	private void checkABox(WebElement element){
		element.click();
	}
	
}
