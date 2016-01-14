package insynctive.pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import insynctive.exception.ElementNotFoundException;
import insynctive.model.ParamObject;
import insynctive.model.ParamObject.Gender;
import insynctive.model.ParamObject.MaritalStatus;
import insynctive.model.USAddress;
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
	public WebElement editEmailiFrame;
	@FindBy(className = "emails-add-popover")
	public WebElement addNewEmailiFrame;
	@FindBy(className = "phones-add-popover")
	public WebElement editPhoneNumberiFrame;
	@FindBy(css = "body > div.standard-popover.bottom.in.webui-no-padding > div.standard-popover-inner > div.standard-popover-content > iframe")
	public WebElement editSecondaryPhoneNumberiFrame;
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
	@FindBy(css = "#emergency-contacts-grid > div:nth-child(2) > span.col-sm-3.name-col.em-col.standard-cut-overflow > span:nth-of-type(1)")
	public WebElement lastEmergencyContactName;
	@FindBy(css = "#emergency-contacts-grid > div:nth-child(2) > span.col-sm-1.delete-col.em-col-right > img")
	public WebElement lastEmergencyContactDelete;

	@FindBy(className = "buttonMedium")
	public WebElement addJobButon;
	@FindBy(id = "employmentFrm")
	public WebElement employmentFrm;
	@FindBy(id = "btnSave")
	public WebElement saveBtn;
	@FindBy(id = "statesPicker")
	public WebElement statesPicker;
	@FindBy(id = "searchResult")
	public WebElement searchResult;
	@FindBy(id = "usjobs_roleposition")
	public WebElement categoryField;
	@FindBy(id = "rateEditor")
	public WebElement rateEditor;
	@FindBy(id = "usjobs_amount")
	public WebElement rateInput;
	@FindBy(className = "editable-submit")
	public WebElement saveRate;
	@FindBy(id = "paymentUnitKey")
	public WebElement paymentUnitKey;
	@FindBy(id = "usjobs_btnactivate")
	public WebElement btnActivate;
	@FindBy(id = "inputusjobs_hiredate")
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
	@FindBy(id = "state-dd")
	public WebElement stateCombo;
	//TODO STATE COMBO
	public WebElement californiaState;
	@FindBy(id = "zip-code")
	public WebElement zipCodeField;
	@FindBy(id = "county-dd")
	public WebElement countyCombo;
	@FindBy(css = "#county-dd > option:nth-child(1)")
	public WebElement firstCounty;
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
	@FindBy(id = "inputusjobs_hiredate")
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
	@FindBy(id = "addEmailBtn")
	public WebElement addAlternativeEmail;
	@FindBy(className = "make-primary-email")
	public WebElement makePrimaryEmailBtn;

	// Has Not Dependents 
	@FindBy(css = "#dependents-grid > span.no-dependents")
	public WebElement hasNotDependentsLink;
	@FindBy(css = "#dependents-grid > span.no-dependents")
	public WebElement hasNotDependentsLabel;

	// Phone Number
	@FindBy(id = "phone-type")
	public WebElement phoneTypeInput;
	@FindBy(id = "phone-country")
	public WebElement phoneCountryInput;
	@FindBy(css = "#content > div:nth-child(3) > div:nth-child(3) > input")
	public WebElement phoneNumberInput;
	@FindBy(css = "#phone-type > option:nth-child(1)")
	public WebElement mobileType;
	@FindBy(css = "#phone-type > option:nth-child(2)")
	public WebElement workType;
	@FindBy(css = "#phone-type > option:nth-child(1)")
	public WebElement homeType;
	@FindBy(css = "#phone-country > option")
	public WebElement unitStatesType;
	@FindBy(css = "#content > div.phone-edit-container > button")
	public WebElement savePhoneNumber;
	@FindBy(id = "work-phone")
	public WebElement alternativePhoneLink;
	@FindBy(className = "phones-edit-popover")
	public WebElement phonesEditFrame;
	@FindBy(css = "#content > div.phone-edit-container.input-row > div > button > span.ladda-label")
	public WebElement makePrimaryPhoneBtn;
	
	// Links
	@FindBy(css = "#statusesListHeader > li:nth-child(1)")
	public WebElement personalLink;
	@FindBy(xpath = "//*[@id=\"full-name-field\"]/div[1]/span")
	public WebElement fullNameLink;
	@FindBy(id = "name")
	public WebElement nameLink;
	
	@FindBy(className = "email-primary")
	public WebElement primaryEmailLink;
	
	@FindBy(id = "date-picker")
	public WebElement birthDateLink;
	@FindBy(css = "#birth-date-field > div.field-text-container > div")
	public WebElement birthDateRequired;
	
	@FindBy(className = "gender-picker")
	public WebElement genderLink;
	@FindBy(css = "#gender-field > div:nth-child(1) > div.pp_required_message")
	public WebElement genderRequired;
	
	
	@FindBy(className = "marital-picker")
	public WebElement maritalLink;
	@FindBy(css = "#marital-status-field > div:nth-child(1) > div.pp_required_message")
	public WebElement maritalRequired;
	
	@FindBy(id = "addBtn")
	public WebElement addSocialSecurityNumber;
	@FindBy(css = "div.pp_required_message:nth-child(5)")
	public WebElement socialSecurityNumberRequired;
	
	@FindBy(id = "addPhoneBtn")
	public WebElement addPhoneNumberLink;
	@FindBy(css = "#content > div:nth-child(3) > div.pp_required_message")
	public WebElement phoneRequired;
	@FindBy(id = "mobile-phone")
	public WebElement phoneNumberLink;
	
	@FindBy(id = "add-address")
	public WebElement addAddressLink;
	@FindBy(css = "#grid-address > div:nth-child(3) > div.pp_required_message")
	public WebElement addressRequired;
	@FindBy(id = "delete-addresses")
	WebElement removeAddressButton;
	
	@FindBy(id = "title-text")
	public WebElement titleLink;
	@FindBy(id = "department-text")
	public WebElement departamentLink;

	@FindBy(css = "#dependentsDescription > div.pp_required_message")
	public WebElement dependentRequired;
	
	//SSN
	@FindBy(id = "ssn-input")
	public WebElement ssnTextField;
	@FindBy(css = "body > div.popover-enumeration.bottom.in.popover-enumeration-ssn-itin > div.popover-enumeration-inner > div > div:nth-child(1)")
	public WebElement addSSN;
	@FindBy(id = "saveSSN")
	public WebElement saveSsn;
	@FindBy(id = "no-results")
	public WebElement noResultCombo;
	
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

	public void changePrimaryEmail(String newEmail) throws Exception {
		waitPageIsLoad();
		Sleeper.sleep(1000, driver);
		clickAButton(primaryEmailLink, 4500);
		waitUntilnotVisibility(loadingSpinner);
		swichToIframe(editEmailiFrame);
		setTextInField(primaryEmailField, newEmail);
		clickAButton(saveChangePrimaryEmail);
	}
	
	public void addAlternativeEmail(String newEmail) throws Exception {
		waitPageIsLoad();
		Sleeper.sleep(1000, driver);
		clickAButton(addAlternativeEmail);
		waitUntilnotVisibility(loadingSpinner);
		swichToIframe(addNewEmailiFrame);
		setTextInField(primaryEmailField, newEmail);
		clickAButton(saveChangePrimaryEmail);
	}
	
	public void makeEmailPrimaryEmail(String email) throws Exception {
		waitPageIsLoad();
		Sleeper.sleep(1000, driver);
		clickAButton(findElementByText("span", email));
		waitUntilnotVisibility(loadingSpinner);
		swichToIframe(editEmailiFrame);
		clickAButton(makePrimaryEmailBtn);
		Sleeper.sleep(4000, driver);
	}
	
	public void addHasNotDependents() throws Exception {
		waitPageIsLoad();
		clickAButton(hasNotDependentsLink);
	}
	
	public void addPhoneNumber(String phoneNumber, String runID, WebElement iframe) throws Exception {
		waitPageIsLoad();
		clickAButton(addPhoneNumberLink);
		swichToIframe(iframe);
		Sleeper.sleep(2500, driver);
		selectElementInComboWithoutClickCombo(phoneTypeInput, "Work", "option");
		Sleeper.sleep(1000, driver);
		selectElementInComboWithoutClickCombo(phoneCountryInput, "United Stated", "option");
		setTextInField(phoneNumberInput, getPhoneNumber(phoneNumber, runID));
		Sleeper.sleep(1000, driver);
		clickAButton(savePhoneNumber);
	}

	public void addPhoneNumber(String phoneNumber, String runID) throws Exception {
		addPhoneNumber(phoneNumber, runID, editPhoneNumberiFrame);
	}
	
	public void addSecondaryEmail(String phoneNumber, String runID) throws Exception {
		addPhoneNumber(getSecondaryPhoneNumber(phoneNumber), runID, editSecondaryPhoneNumberiFrame);
	}

	public Map<String, String> makeAsPrimary() throws Exception{
		Map<String, String> result = new HashMap<>();
		waitPageIsLoad();
		result.put("newPrimaryPhone", alternativePhoneLink.getText());
		result.put("oldPrimaryPhone", phoneNumberLink.getText());
		clickAButton(alternativePhoneLink);
		swichToIframe(phonesEditFrame);
		Sleeper.sleep(1500, driver);
		clickAButton(makePrimaryPhoneBtn);
		return result;
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
		clickAButton(addSSN);
		swichToIframe(socialSecurtyiFrame);
		setTextInField(ssnTextField, getSSN(ssnNumber, runID));
		clickAButton(saveSsn);
	} 
	
	/*Wait Methods*/
	@Override
	public void waitPageIsLoad() throws Exception {
		swichToFirstFrame(driver);
		waitUntilnotVisibility(loadingSpinner);
		swichToIframe(tabiFrame);
		waitUntilIsLoaded(maritalLink);
		waitUntilIsLoaded(primaryEmailLink);
		waitUntilIsLoaded(fullNameLink);
		Sleeper.sleep(1000, driver);
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
		waitUntilIsLoaded(zipCodeField);
		waitUntilIsLoaded(countyCombo);
	}
	
	/*UTILITIES*/
	public void completeAddressForm(USAddress usAddress) throws Exception {
		setTextInField(streetAddressField, usAddress.getStreet());
		setTextInField(streetAddressOptionalField, usAddress.getSecondStreet());
		setTextInField(cityField, usAddress.getCity());
		selectElementInComboWithoutClickCombo(stateCombo, usAddress.getState(), "option");
		setTextInField(zipCodeField, usAddress.getZipCode());
		selectElementInComboWithoutClickCombo(countyCombo, usAddress.getCounty(), "option");
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
	
	public String getNameToAssertInPersonalDetails(ParamObject personData) {
		String assertFullName = (personData.getName() != null && personData.getMiddleName() != null) ? personData.getName()
				+ " " + personData.getMiddleName(): personData.getName();
		assertFullName += (personData.getMaidenName() != null) ? " (" + personData.getMaidenName() + ") " : " ";
		assertFullName += personData.getLastName();
		if(assertFullName.length() < 39){
			return assertFullName;
		} else {
			return assertFullName.substring(0,35)+"...";
		}
	}
	
	public String getSSN(String ssn, String runID) {
		String ssnRet = "";
		ssnRet = ssn.substring(0,ssn.length()-runID.length());
		return ssnRet+runID;
	}

	public String getPhoneNumber(String phoneNumber, String runID) {
		String phoneNumberRet = "";
		phoneNumberRet = phoneNumber.substring(0,phoneNumber.length()-runID.length());
		return phoneNumberRet+runID;
	}
	
	public String parsePhoneNumber(String phoneNumber){
		return "("+phoneNumber.substring(0,3)+") "+phoneNumber.substring(3,6)+"-"+phoneNumber.substring(6,phoneNumber.length());
	}
	
	public String getSecondaryPhoneNumber(String phoneNumber) {
		return "9"+phoneNumber.substring(1,phoneNumber.length());
	}

	public boolean isPresent(WebElement element) throws Exception {
		swichToTabiFrame();
		return isElementPresent(element);
	}

	private void swichToTabiFrame() throws IOException, InterruptedException, ElementNotFoundException {
		swichToFirstFrame(driver);
		swichToIframe(tabiFrame);
	}
}