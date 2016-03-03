package insynctive.pages.pdf;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import insynctive.exception.ElementNotFoundException;
import insynctive.utils.Sleeper;

public class W4PDF extends PDFForm {

	@FindBy(id = "pdfe_oo1")
	protected WebElement responseA;
	
	@FindBy(id = "pdfe_oo2")
	protected WebElement responseB;
	
	@FindBy(id = "pdfe_oo3")
	protected WebElement responseC;
	
	@FindBy(id = "pdfe_oo4")
	protected WebElement responseD;
	
	@FindBy(id = "pdfe_oo5")
	protected WebElement responseE;
	
	@FindBy(id = "pdfe_oo6")
	protected WebElement responseF;
	
	@FindBy(id = "pdfe_oo7")
	protected WebElement responseG;
	
	@FindBy(id = "pdfe_oo8")
	protected WebElement responseH;
	
	public W4PDF(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void swichToRapPDFFrame() throws IOException, InterruptedException, ElementNotFoundException {
		swichToTaskiFrame();
		swichToIframe(rapPdfiFrame);
	}

	public void fillInformation() throws Exception{
		swichToPDFWebiFrame();
		setTextInPDFField(200, responseA, "A");
		setTextInPDFField(200, responseB, "B");
		setTextInPDFField(200, responseC, "C");
		setTextInPDFField(200, responseD, "D");
		setTextInPDFField(200, responseE, "E");
		setTextInPDFField(200, responseF, "F");
		setTextInPDFField(200, responseG, "G");
		setTextInPDFField(200, responseH, "H");
		swichToTaskiFrame();
		clickAButton(signBtn, 7000);
		clickAButton(doneBtn, 7000);
	}
	
}
