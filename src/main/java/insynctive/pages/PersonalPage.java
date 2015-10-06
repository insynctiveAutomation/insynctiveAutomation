package insynctive.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import insynctive.model.PersonData;
import insynctive.model.USAddress;
import insynctive.model.PersonData.Gender;
import insynctive.model.PersonData.MaritalStatus;
import insynctive.utils.Sleeper;

public abstract class PersonalPage extends Page {
	
	public String enviroment;
	
	//iFrames
	@FindBy(id = "tabFrame")
	public WebElement tabiFrame;
	@FindBy(css = ".header-name-popover")
	public WebElement editNameintoTitleiFrame;
	@FindBy(className = "full-name-popover")
	public WebElement editNameiFrame;
	@FindBy(className = "date-picker-popover")
	public WebElement birthDateiFrame;
	@FindBy(className = "emails-edit-popover")
	public WebElement primaryEmailiFrame;
	@FindBy(className = "phones-add-popover")
	public WebElement editPhoneNumberiFrame;
	@FindBy(className = "ssn-popover")
	public WebElement socialSecurtyiFrame;
	@FindBy(className = "address-add-popover")
	public WebElement usAddressiFrame;
	@FindBy(className = "address-edit-popover")
	public WebElement usAddressEditiFrame;
	@FindBy(className = "emergency-add-popover")
	public WebElement emergencyContactFrame;
	@FindBy(className = "emergency-edit-popover")
	public WebElement emergencyContactEditFrame;
	
	@FindBy(id = "Month")
	public WebElement monthInput;
	@FindBy(id = "Day")
	public WebElement dayInput;
	@FindBy(id = "Year")
	public WebElement yearInput;

	// Emergency Contacts
	@FindBy(id = "addContact")
	public WebElement addEmergencyContact;
	@FindBy(id = "ContactName")
	public WebElement emergencyContactName;
	@FindBy(id = "Relationship")
	public WebElement relationship;
	@FindBy(id = "Email")
	public WebElement emergencyContactEmail;
	@FindBy(id = "Phone")
	public WebElement emergencyContactPhone;
	@FindBy(xpath = ".//*[@id='saveBtn']")
	public WebElement emergencyContactSave;
	@FindBy(css = "#content > div:nth-of-type(5) > div > div:nth-last-of-type(2) > span:nth-of-type(1) > span:nth-of-type(1)")
	public WebElement lastEmergencyContactName;
	@FindBy(css = "#content > div:nth-of-type(5) > div > div:nth-last-of-type(2) > span:nth-of-type(5) > img:nth-of-type(1)")
	public WebElement lastEmergencyContactDelete;

	@FindBy(id = "btnAddNewJob")
	public WebElement addJobButon;
	@FindBy(id = "employmentFrm")
	public WebElement employmentFrm;
	@FindBy(id = "btnSave")
	public WebElement saveBtn;
	@FindBy(id = "statesPicker")
	public WebElement statesPicker;
	@FindBy(id = "searchResult")
	public WebElement searchResult;
	@FindBy(id = "categoryPicker")
	public WebElement categoryPicker;
	@FindBy(id = "rateEditor")
	public WebElement rateEditor;
	@FindBy(css = "#ui-tooltip-0 > div > div > form > div > div:nth-child(1) > div.editable-input > input")
	public WebElement rateInput;
	@FindBy(className = "editable-submit")
	public WebElement saveRate;
	@FindBy(id = "paymentUnitKey")
	public WebElement paymentUnitKey;
	@FindBy(id = "btnActivate")
	public WebElement btnActivate;
	@FindBy(id = "datePicker")
	public WebElement datePicker;
	@FindBy(id = "statesPickerSearch")
	public WebElement statesPickerSearch;
	@FindBy(css = "div.searchItemDocument:nth-child(1) > p:nth-child(1)")
	public WebElement firstSearchJobState;
	@FindBy(css = "div.searchItemDocument:nth-child(1)")
	public WebElement chefExecutives;
	
	// Add Address
	@FindBy(id = "address1")
	public WebElement streetAddressField;
	@FindBy(id = "address2")
	public WebElement streetAddressOptionalField;
	@FindBy(id = "city")
	public WebElement cityField;
	@FindBy(css = "#state_dd_chosen > a")
	public WebElement stateCombo;
	@FindBy(id = "zip-code")
	public WebElement zipCodeField;
	@FindBy(css = "#county_dd_chosen > a")
	public WebElement countyCombo;
	@FindBy(id = "is-mailing")
	public WebElement sameAsHomeAddressCheck;
	@FindBy(id = "save-address")
	public WebElement saveAddressButton;
	@FindBy(css = ".address-name > span:nth-child(1)")
	public WebElement addressSaved;
	
	// FullName
	@FindBy(id = "FirstName")
	public WebElement firstName;
	@FindBy(id = "LastName")
	public WebElement lastName;
	@FindBy(id = "MiddleName")
	public WebElement middleName;
	@FindBy(id = "PreviousName")
	public WebElement maidenName;
	@FindBy(xpath = "//*[@id='saveBtn']/span[1]")
	public WebElement saveChangeFullName;

	// Birth Date
	@FindBy(id = "dateInput")
	public WebElement dateInput;
	@FindBy(id = "saveBtnNewDatePicker")
	public WebElement saveChangeBirthDate;

	// Primary Email
	@FindBy(className = "email-input")
	public WebElement primaryEmailField;
	@FindBy(className = "save-email")
	public WebElement saveChangePrimaryEmail;
	@FindBy(id = "saveBtn")
	public WebElement savePrimaryEmailChange;
	@FindBy(css = ".error-msg")
	public WebElement errorMessageEmail;

	// Has Not Dependents
	@FindBy(css = "#dependents-grid > span.no-dependents")
	public WebElement hasNotDependentsLink;

	@FindBy(css = "#dependents-grid > span.no-dependents")
	public WebElement hasNotDependentsLabel;

	// Add Phone Number
	@FindBy(id = "addBtn")
	public WebElement addPhoneNumberLink2;
	@FindBy(id = "inputAddPhone")
	public WebElement addPhoneNumberInput;
	@FindBy(id = "btnAddPhone")
	public WebElement buttonAddPhone;
	@FindBy(id = "saveBtn")
	public WebElement saveChangeAddPhoneNumber;
	@FindBy(id = "mobile-phone")
	public WebElement mobilePhoneNumber;
	@FindBy(className = "delete-phone-btn")
	public WebElement deletePhoneNumber;
	
	// Links
	@FindBy(css = "#statusesListHeader > li:nth-child(1)")
	public WebElement personalLink;
	@FindBy(xpath = "//*[@id=\"full-name-field\"]/div[1]/span")
	public WebElement fullNameLink;
	@FindBy(id = "name")
	public WebElement nameLink;
	@FindBy(id = "date-picker")
	public WebElement birthDateLink;
	@FindBy(className = "gender-picker")
	public WebElement genderLink;
	@FindBy(className = "marital-picker")
	public WebElement maritalLink;
	@FindBy(className = "email-primary")
	public WebElement primaryEmailLink;
	@FindBy(id = "addPhoneBtn")
	public WebElement addPhoneNumberLink;
	@FindBy(id = "add-address")
	public WebElement addAddressLink;
	@FindBy(id = "delete-addresses")
	WebElement removeAddressButton;
	@FindBy(id = "addBtn")
	public WebElement addSocialSecurityNumber;
	@FindBy(id = "phone-text")
	public WebElement phoneNumberLink;
	@FindBy(id = "title-text")
	public WebElement titleLink;
	@FindBy(id = "department-text")
	public WebElement departamentLink;

	//SSN
	@FindBy(id = "ssnBtn")
	WebElement ssnInsert;
	@FindBy(id = "ssn-input")
	WebElement ssnTextField;
	@FindBy(id = "saveSSN")
	WebElement saveSsn;
	@FindBy(id = "no-results")
	WebElement noResultCombo;
	
	public PersonalPage(WebDriver driver) {
		super(driver);
	}
	
	/* Complete Test Methods */
	public void changeName(String nameIn, String lastNameIn,
			String middleNameIn, String maidenNameIn) throws Exception {
		waitPageIsLoad();
		clickAButton(fullNameLink);
		swichToIframe(editNameiFrame);
		setTextInField(firstName, nameIn);
		setTextInField(lastName, lastNameIn);
		setTextInField(middleName, middleNameIn);
		setTextInField(maidenName, maidenNameIn);
		clickAButton(saveChangeFullName);
		waitUntilnotVisibility(saveChangeFullName);
	}

	public void changeNameIntoTitle(String nameIn, String lastNameIn,
			String middleNameIn, String maidenNameIn) throws Exception {
		waitPageIsLoad();
		swichToFirstFrame(driver);
		clickAButton(nameLink);
		swichToIframe(editNameintoTitleiFrame);
		setTextInField(firstName, nameIn);
		setTextInField(lastName, lastNameIn);
		setTextInField(middleName, middleNameIn);
		setTextInField(maidenName, maidenNameIn);
		clickAButton(saveChangeFullName);
		waitUntilnotVisibility(saveChangeFullName);
	}

	public void changeGender(Gender gender) throws Exception {
		waitPageIsLoad();
		clickAButton(genderLink);
		clickAButton(driver.findElement(
				By.xpath("//div[@class='enum-item' and contains(., '"
						+ gender.name + "')]")));
	}

	public void changeBirthDate(String birthDate) throws Exception {
		String[] birthParts = birthDate.split("/");
		waitPageIsLoad();
		clickAButton(birthDateLink);
		swichToIframe(birthDateiFrame);
		waitUntilnotVisibility(loadingSpinner);
		selectElementInDefaultCombo(monthInput, getMonth(birthParts[0]));
		selectElementInDefaultCombo(dayInput, getDay(birthParts[1]));
		selectElementInDefaultCombo(yearInput, birthParts[2]);
		clickAButton(saveChangeBirthDate);
	}

	public void changeMaritalStatus(MaritalStatus status) throws Exception {
		waitPageIsLoad();
		clickAButton(maritalLink);
		clickAButton(driver.findElement(
				By.xpath("//div[@class='enum-item' and contains(., '"
						+ status.status + "')]")));
	}

	public void changePrimaryEmail(PersonData person) throws Exception {
		waitPageIsLoad();
		Sleeper.sleep(1000, driver);
		clickAButton(primaryEmailLink);
		waitUntilnotVisibility(loadingSpinner);
		swichToIframe(primaryEmailiFrame);
		waitUntilIsLoaded(primaryEmailField);
		setTextInField(primaryEmailField, person.getEmailToChange());
		waitUntilIsLoaded(saveChangePrimaryEmail);
		clickAButton(saveChangePrimaryEmail);
	}

	public void addHasNotDependents() throws Exception {
		waitPageIsLoad();
		clickAButton(hasNotDependentsLink);
	}

	public void addPhoneNumber(String phoneNumber, String runID) throws Exception {
		waitPageIsLoad();
		clickAButton(addPhoneNumberLink);
		swichToIframe(editPhoneNumberiFrame);
		waitUntilIsLoaded(addPhoneNumberLink2);
		clickAButton(addPhoneNumberLink2);
		waitUntilIsLoaded(addPhoneNumberInput);
		waitUntilIsLoaded(buttonAddPhone);
		setTextInField(addPhoneNumberInput, getPhoneNumber(phoneNumber, runID));
		clickAButton(buttonAddPhone);
		waitUntilIsLoaded(deletePhoneNumber);
		clickAButton(saveChangeAddPhoneNumber);
	}

	public String getPhoneNumber(String phoneNumber, String runID) {
		String phoneNumberRet = "";
		phoneNumberRet = phoneNumber.substring(0,phoneNumber.length()-runID.length());
		return phoneNumberRet+runID;
	}

	public void addUsAddress(USAddress usAddress) throws Exception {
		waitPageIsLoad();
		clickAButton(addAddressLink);
		swichToIframe(usAddressiFrame);
		waitUsAddresFrameIsLoad();
		completeAddressForm(usAddress);
		clickAButton(saveAddressButton);
		waitPageIsLoad();
		waitUntilIsLoaded(addressSaved);
	}

	public void removeUsAddress(USAddress usAddress) throws Exception {
		waitPageIsLoad();
		WebElement address = searchAddress(usAddress);
		clickAButton(address);
		swichToIframe(usAddressEditiFrame);
		waitUsAddresFrameIsLoad();
		waitUntilIsLoaded(removeAddressButton);
		clickAButton(removeAddressButton);
		waitPageIsLoad();
	}

	public void addEmergencyContact(String name, String relationship,
			String phone, String email) throws Exception {
		waitPageIsLoad();
		clickAButton(addEmergencyContact);
		waitEmergencyContactFrameIsLoad();
		clickAButton(emergencyContactName);
		setTextInField(emergencyContactName, name);
		clickAButton(this.relationship);
		setTextInField(this.relationship, relationship);
		clickAButton(emergencyContactPhone);
		setTextInField(emergencyContactPhone, phone);
		clickAButton(emergencyContactEmail);
		setTextInField(emergencyContactEmail, email);
		clickAButton(emergencyContactSave);

	}
	
	public void editEmergencyContact(String name, String relationship,
			String phone, String email) throws Exception {
		waitPageIsLoad();
		clickAButton(lastEmergencyContactName);
		waitEditEmergencyContactFrameIsLoad();
		clickAButton(emergencyContactName);
		emergencyContactName.clear();
		setTextInField(emergencyContactName, name);
		clickAButton(this.relationship);
		this.relationship.clear();
		setTextInField(this.relationship, relationship);
		clickAButton(emergencyContactPhone);
		emergencyContactPhone.clear();
		setTextInField(emergencyContactPhone, phone);
		clickAButton(emergencyContactEmail);
		emergencyContactEmail.clear();
		setTextInField(emergencyContactEmail, email);
		clickAButton(emergencyContactSave);

	}
	
	public void removeLastEmergencyContact() throws Exception {
		waitPageIsLoad();
		clickAButton(lastEmergencyContactDelete);
	}

	public void addSocialSecurityNumber(String ssnNumber, String runID) throws Exception {
		waitPageIsLoad();
		clickAButton(addSocialSecurityNumber);
		swichToIframe(socialSecurtyiFrame);
		clickAButton(ssnInsert);
		setTextInField(ssnTextField, getSSN(ssnNumber, runID));
		clickAButton(saveSsn);
	}
	
	public String getSSN(String ssn, String runID) {
		String ssnRet = "";
		ssnRet = ssn.substring(0,ssn.length()-runID.length());
		return ssnRet+runID;
	}
	
	public Object getNameToAssertInPersonalDetails(PersonData personData) {
		String assertFullName = (personData.getName() != null && personData.getMiddleName() != null) ? personData.getName()
				+ " " + personData.getMiddleName(): personData.getName();
		assertFullName += (personData.getMaidenName() != null) ? " (" + personData.getMaidenName() + ") " : " ";
		assertFullName += personData.getLastName();
		if(assertFullName.length() <= 35){
			return assertFullName;
		} else {
			return assertFullName.substring(0,35)+"...";
		}
	}
	
	public void waitEmergencyContactFrameIsLoad() throws Exception {
		swichToIframe(emergencyContactFrame);
		waitUntilIsLoaded(emergencyContactName);
		waitUntilIsLoaded(emergencyContactPhone);
		waitUntilIsLoaded(emergencyContactEmail);
		waitUntilIsLoaded(relationship);

	}
	
	public void waitEditEmergencyContactFrameIsLoad() throws Exception {
		swichToIframe(emergencyContactEditFrame);
		waitUntilIsLoaded(emergencyContactName);
		waitUntilIsLoaded(emergencyContactPhone);
		waitUntilIsLoaded(emergencyContactEmail);
		waitUntilIsLoaded(relationship);

	}
	
	public void waitUsAddresFrameIsLoad() throws Exception {
		waitUntilIsLoaded(streetAddressField);
		waitUntilIsLoaded(streetAddressOptionalField);
		waitUntilIsLoaded(cityField);
		waitUntilIsLoaded(stateCombo);
		waitUntilIsLoaded(zipCodeField);
		waitUntilIsLoaded(countyCombo);
	}
	
	//UTILITIES
	public void completeAddressForm(USAddress usAddress) throws Exception {
		setTextInField(streetAddressField, usAddress.getStreet());
		setTextInField(streetAddressOptionalField, usAddress.getSecondStreet());
		setTextInField(cityField, usAddress.getCity());
		setTextInCombo(stateCombo, usAddress.getState());
		setTextInField(zipCodeField, usAddress.getZipCode());
		setTextInCombo(countyCombo, usAddress.getCounty());
		if(usAddress.isSameAsHome()){
			//TODO ADD BOX CKICK
		}
	}
	
	public WebElement searchAddress(USAddress usAddress) {
		List<WebElement> allAddress = driver.findElements(By.className("address-name"));
		WebElement address;
		for (int index = 0; index < allAddress.size(); index++) {
			address = allAddress.get(index);
			if(isTheSameAddress(address, usAddress)){
				return address;
			}
		}
		return null;
	}
	
	public boolean isTheSameAddress(WebElement address, USAddress usAddress) {
		return address.getText().contains(usAddress.getCity())
				&& address.getText().contains(usAddress.getCounty())
				&& address.getText().contains(usAddress.getStreet())
				&& address.getText().contains(usAddress.getZipCode());
	}
	
	public String getDay(String number) {
		if(Integer.parseInt(number) < 10) {
			Integer numberInt = Integer.parseInt(number);
			return numberInt.toString();
		} else {
			return number;
		}
	}

	public String getMonth(String number) {
		switch (Integer.parseInt(number)) {
		case 1:
			return "January";
		case 2:
			return "February";
		case 3:
			return "March";
		case 4:
			return "April";
		case 5:
			return "May";
		case 6:
			return "June";
		case 7:
			return "July";
		case 8:
			return "August";
		case 9:
			return "September";
		case 10:
			return "October";
		case 11:
			return "November";
		case 12:
			return "December";

		default:
			return "ERROR";
		}
	}

	public String getShortMonth(String number) {
		switch (Integer.parseInt(number)) {
		case 1:
			return "Jan";
		case 2:
			return "Feb";
		case 3:
			return "Mar";
		case 4:
			return "Apr";
		case 5:
			return "May";
		case 6:
			return "Jun";
		case 7:
			return "Jul";
		case 8:
			return "Aug";
		case 9:
			return "Sep";
		case 10:
			return "Oct";
		case 11:
			return "Nov";
		case 12:
			return "Dec";

		default:
			return "ERROR";
		}
	}
}