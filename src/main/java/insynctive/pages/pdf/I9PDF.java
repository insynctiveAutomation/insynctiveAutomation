package insynctive.pages.pdf;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import insynctive.exception.ElementNotFoundException;

public class I9PDF extends PDFForm {

	@FindBy(id = "pdfe_content")
	protected WebElement pdfContent;

	@FindBy(xpath = "//div[contains(@title, 'Last Name')]")
	protected WebElement lastName;
	
	@FindBy(xpath = "//div[contains(@title, 'First Name')]")
	protected WebElement firstName;
	
	@FindBy(xpath = "//div[contains(@title, 'Middle Initial')]")
	protected WebElement middleInitial;
	
	@FindBy(xpath = "//div[contains(@title, 'Maiden Name')]")
	protected WebElement maidenName;
	
	@FindBy(xpath = "//div[contains(@title, 'Street Number and Name')]")
	protected WebElement streetNumberAndName;
	
	@FindBy(xpath = "//div[contains(@title, 'Apartment Number')]")
	protected WebElement aptNumber;
	
	@FindBy(xpath = "//div[contains(@title, 'City or Town')]")
	protected WebElement cityOrTown;
	
	@FindBy(xpath = "//*[@id='pdfe_oo51']") //TODO WAIT FOR LABEL IN FIELD
	protected WebElement state;
	
	@FindBy(xpath = "//div[contains(@title, 'Zip Code')]")
	protected WebElement zipCode;
	
	@FindBy(xpath = "//div[contains(@title, 'Date of Birth')]")
	protected WebElement dateOfBirth;
	
	@FindBy(xpath = "//div[contains(@title, 'Enter 3 digit')]")
	protected WebElement ssnFirsts;
	
	@FindBy(xpath = "//div[contains(@title, 'Enter 2 digits')]")
	protected WebElement ssnSeconds;
	
	@FindBy(xpath = "//div[contains(@title, 'Enter the last 4 digits')]")
	protected WebElement ssnThirdLasts;
	
	@FindBy(xpath = "//div[contains(@title, 'email')]")
	protected WebElement email;
	
	@FindBy(xpath = "//div[contains(@title, 'Telephone Number')]")
	protected WebElement telephoneNumber;
	
	@FindBy(xpath = "//div[contains(@title, 'Employee Family Name in Caps (last name) Given Name (first name), and Middle Initial')]")
	protected WebElement initialInformationFromSectionOne;
	
	@FindBy(xpath = "//div[contains(@title, 'Section 2., Title of Employer or Authorized Representative')]")
	protected WebElement sectionTwotitleOfEmployer;
	
	@FindBy(xpath = "//div[contains(@title, 'Section 2., Employer or Authorized Representative. Last Name (Family Name)')]")
	protected WebElement sectionTwoLastName;
	
	@FindBy(xpath = "//div[contains(@title, 'Section 2., Employer or Authorized Representative. First Name (Given Name)')]")
	protected WebElement sectionTwoFirstName;
	
	@FindBy(id = "linkPrev")
	protected WebElement previousPage;
    
	public I9PDF(WebDriver driver) {
		super(driver);
	}
	
	public boolean checkInformationOfSectionOne() throws Exception {
		swichToPDFWebiFrame();
		checkInformation(100, lastName, "Last Name");
		checkInformation(100, firstName, "First Name");
		checkInformation(100, middleInitial, "Mid");
		checkInformation(100, maidenName, "Maiden Name");
		checkInformation(100, streetNumberAndName, "Street Number and Name");
		checkInformation(100, aptNumber, "112233");
		checkInformation(100, cityOrTown, "City or Town");
		checkInformation(100, state, "CA");
		checkInformation(100, zipCode, "12345");
		checkInformation(100, dateOfBirth, "11/22/3333");
		checkSSNInformation(100, ssnFirsts, "111");
		checkSSNInformation(100, ssnSeconds, "22");
		checkSSNInformation(100, ssnThirdLasts, "3333");
		checkInformation(100, email, "email@email.com");
		checkInformation(100, telephoneNumber, "123456789");
		
		return true; //NOT EXCEPTIONS THROWS
	}

	public boolean checkInformationOfSectionTwo() throws Exception {
		swichToPDFWebiFrame();
		checkInformation(100, sectionTwotitleOfEmployer, "Agent");
		checkInformation(100, sectionTwoFirstName, "Eugenio");
		checkInformation(100, sectionTwoLastName, "PDF Agent");
		checkInformation(100, initialInformationFromSectionOne, "First Name Last Name");
		
		return true; //NOT EXCEPTIONS THROWS
	}
	
	@Override
	public void swichToRapPDFFrame() throws IOException, InterruptedException, ElementNotFoundException {
		swichToTaskiFrame();
		swichToIframe(rapPdfiFrame);
	}
	
}
