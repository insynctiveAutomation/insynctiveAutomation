package insynctive.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class PDFItemsPage extends Page {
	@FindBy(id = "btnAcknowledge")
	public WebElement signBtn;
	@FindBy(css = "#buttonDone > button")
	public WebElement doneBtn;
	@FindBy(id = "popupSign_panelSign_name")
	public WebElement signTextType;
	@FindBy(id = "rappdf")
	public WebElement pdfIframe;
	@FindBy(id = "btnApplySig_CD")
	public WebElement btnApplySignature;
	@FindBy(id = "pdfWebControl")
	public WebElement radPdfIframe;
	@FindBy(id = "pdfe_paged0")
	public WebElement pdfPage;
	@FindBy(css = "#buttonDone > button")
	public WebElement DoneButton;
	@FindBy(id = "pdfe_msgt")
	public WebElement banner;
	@FindBy(id = "pdfe_oo1")
	public WebElement resetFormButton;
	
	//I NEED TO CHANGE THIS FORM TO DO THIS, FOR SOMETHIN ELSE.
	@FindBy(id = "pdfe_oo3")
	public WebElement fullName;
	@FindBy(css = "#pdfe_oo3 > input")
	public WebElement fullNameText;

	@FindBy(id = "pdfe_oo4")
	public WebElement socialSecurityNumber;
	@FindBy(css = "#pdfe_oo4 > input")
	public WebElement socialSecurityNumberText;

	@FindBy(id = "pdfe_oo8")
	public WebElement homeAddress;
	@FindBy(css = "#pdfe_oo8 > input")
	public WebElement homeAddressText;
	
	@FindBy(id = "pdfe_oo9")
	public WebElement cityOrTown;
	@FindBy(css = "#pdfe_oo9 > input")
	public WebElement cityOrTownText;
	
	@FindBy(id = "pdfe_oo10")
	public WebElement state;
	@FindBy(css = "#pdfe_oo10 > input")
	public WebElement stateText;
	
	@FindBy(id = "pdfe_oo11")
	public WebElement zipCode;
	@FindBy(css = "#pdfe_oo11 > input")
	public WebElement zipCodeText;
	
	public enum fillingStatus {
		SINGLE("pdfe_oo5"), MARRIED("pdfe_oo6"), HEADOFHOUSE("pdfe_oo7");
		
		private final String id;
			
		private fillingStatus(String id) {
			this.id = id;
		}
	}
	
	public WebElement getFillingStatus(fillingStatus filling){
		return driver.findElement(By.id(filling.id));
	}

	public PDFItemsPage(WebDriver driver) {
		super(driver);
	 }
}
