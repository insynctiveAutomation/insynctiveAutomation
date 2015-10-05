package insynctive.pages.insynctive;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.model.EmergencyContact;
import insynctive.model.PersonData;
import insynctive.model.PersonData.Gender;
import insynctive.model.PersonData.MaritalStatus;
import insynctive.model.USAddress;
import insynctive.pages.Page;
import insynctive.pages.PageInterface;
import insynctive.utils.Sleeper;
import insynctive.utils.Task;
import insynctive.utils.Wait;

public class PersonFilePage extends Page implements PageInterface {

	String enviroment;

	@FindBy(id = "phone-text")
	WebElement phoneNumberLink;
	@FindBy(id = "title-text")
	WebElement titleLink;
	@FindBy(id = "department-text")
	WebElement departamentLink;

	// Links
	@FindBy(css = "#statusesListHeader > li:nth-child(1)")
	WebElement personalLink;
	@FindBy(xpath = "//*[@id=\"full-name-field\"]/div[1]/span")
	WebElement fullNameLink;
	@FindBy(id = "name")
	WebElement nameLink;
	@FindBy(id = "date-picker")
	WebElement birthDateLink;
	@FindBy(className = "gender-picker")
	WebElement genderLink;
	@FindBy(className = "marital-picker")
	WebElement maritalLink;
	@FindBy(className = "email-primary")
	WebElement primaryEmailLink;
	@FindBy(id = "addPhoneBtn")
	WebElement addPhoneNumberLink;
	@FindBy(id = "add-address")
	WebElement addAddressLink;
	@FindBy(id = "addBtn")
	WebElement addSocialSecurityNumber;

	// iFrames
	@FindBy(id = "tabFrame")
	WebElement tabiFrame;
	@FindBy(css = ".header-name-popover")
	WebElement editNameintoTitleiFrame;
	@FindBy(className = "full-name-popover")
	WebElement editNameiFrame;
	@FindBy(className = "date-picker-popover")
	WebElement birthDateiFrame;
	@FindBy(className = "emails-edit-popover")
	WebElement primaryEmailiFrame;
	@FindBy(className = "phones-add-popover")
	WebElement editPhoneNumberiFrame;
	@FindBy(className = "ssn-popover")
	WebElement socialSecurtyiFrame;
	@FindBy(className = "address-add-popover")
	WebElement usAddressiFrame;
	@FindBy(className = "address-edit-popover")
	WebElement usAddressEditiFrame;
	@FindBy(className = "emergency-add-popover")
	WebElement emergencyContactFrame;
	@FindBy(className = "emergency-edit-popover")
	WebElement emergencyContactEditFrame;
	

	// title
	@FindBy(id = "Title")
	WebElement titleField;
	@FindBy(id = "Department")
	WebElement departamentField;
	@FindBy(id = "saveBtn")
	WebElement saveChangeTitle;

	// FullName
	@FindBy(id = "FirstName")
	WebElement firstName;
	@FindBy(id = "LastName")
	WebElement lastName;
	@FindBy(id = "MiddleName")
	WebElement middleName;
	@FindBy(id = "PreviousName")
	WebElement maidenName;
	@FindBy(xpath = "//*[@id='saveBtn']/span[1]")
	WebElement saveChangeFullName;

	// Birth Date
	@FindBy(id = "dateInput")
	WebElement dateInput;
	@FindBy(id = "saveBtnNewDatePicker")
	WebElement saveChangeBirthDate;

	// Primary Email
	@FindBy(className = "email-input")
	WebElement primaryEmailField;
	@FindBy(className = "save-email")
	WebElement saveChangePrimaryEmail;
	@FindBy(id = "saveBtn")
	WebElement savePrimaryEmailChange;
	@FindBy(css = ".error-msg")
	WebElement errorMessageEmail;

	// Has Not Dependents
	@FindBy(css = "#dependents-grid > span.no-dependents")
	WebElement hasNotDependentsLink;

	@FindBy(css = "#dependents-grid > span.no-dependents")
	WebElement hasNotDependentsLabel;

	// Add Phone Number
	@FindBy(id = "addBtn")
	WebElement addPhoneNumberLink2;
	@FindBy(id = "inputAddPhone")
	WebElement addPhoneNumberInput;
	@FindBy(id = "btnAddPhone")
	WebElement buttonAddPhone;
	@FindBy(id = "saveBtn")
	WebElement saveChangeAddPhoneNumber;
	@FindBy(id = "mobile-phone")
	WebElement mobilePhoneNumber;
	@FindBy(className = "delete-phone-btn")
	WebElement deletePhoneNumber;

	// Add Address
	@FindBy(id = "address1")
	WebElement streetAddressField;
	@FindBy(id = "address2")
	WebElement streetAddressOptionalField;
	@FindBy(id = "city")
	WebElement cityField;
	@FindBy(css = "#state_dd_chosen > a")
	WebElement stateCombo;
	@FindBy(id = "zip-code")
	WebElement zipCodeField;
	@FindBy(css = "#county_dd_chosen > a")
	WebElement countyCombo;
	@FindBy(id = "is-mailing")
	WebElement sameAsHomeAddressCheck;
	@FindBy(id = "save-address")
	WebElement saveAddressButton;
	@FindBy(css = ".address-name > span:nth-child(1)")
	WebElement addressSaved;

	// Tasks
	@FindBy(css = "#statusesListHeader > li:nth-child(1)")
	WebElement personalTab;
	@FindBy(css = "#statusesListHeader > li:nth-child(2)")
	WebElement tasksTab;
	@FindBy(css = "#btnAssignTask > span")
	WebElement assignTaskButton;
	@FindBy(id = "lName")
	WebElement taskName;
	@FindBy(id = "txtTaskInstructions")
	WebElement taskInstuctions;
	@FindBy(className = "froala-element")
	WebElement taskAdditionalInstructions;
	@FindBy(id = "btnAssignTask")
	WebElement btnAssignTask;
	@FindBy(id = "RunningChecklistsTab")
	WebElement runningChecklist;
	@FindBy(id = "btnStartChecklist")
	WebElement startChecklistButton;
	@FindBy(css = "#contentHeight > iframe")
	WebElement startChecklistiFrame;
	@FindBy(css = "#ddChecklist_chosen > a")
	WebElement checkListsCombo;
	@FindBy(id = "btnStartChecklist")
	WebElement assignChecklistButton;
	@FindBy(css = "div.row:nth-child(3) > div:nth-child(1) > span:nth-child(1)")
	WebElement firstChecklist;
	@FindBy(id = "delete-addresses")
	WebElement removeAddressButton;
	@FindBy(css = "#pendingTasksList > div.task-list > div:nth-child(1) > span > div")
	WebElement firstTaskLink;
	@FindBy(id = "froalaEditor")
	WebElement AdditionalInstructioniFrame;
	@FindBy(id = "ssnBtn")
	WebElement ssnInsert;
	@FindBy(id = "ssn-input")
	WebElement ssnTextField;
	@FindBy(id = "saveSSN")
	WebElement saveSsn;
	@FindBy(id = "no-results")
	WebElement noResultCombo;

	@FindBy(id = "Month")
	private WebElement monthInput;
	@FindBy(id = "Day")
	private WebElement dayInput;
	@FindBy(id = "Year")
	private WebElement yearInput;

	// Emergency Contacts
	@FindBy(id = "addContact")
	WebElement addEmergencyContact;
	@FindBy(id = "ContactName")
	WebElement emergencyContactName;
	@FindBy(id = "Relationship")
	WebElement relationship;
	@FindBy(id = "Email")
	WebElement emergencyContactEmail;
	@FindBy(id = "Phone")
	WebElement emergencyContactPhone;
	@FindBy(xpath = ".//*[@id='saveBtn']")
	WebElement emergencyContactSave;
	@FindBy(css = "#content > div:nth-of-type(5) > div > div:nth-last-of-type(2) > span:nth-of-type(1) > span:nth-of-type(1)")
	WebElement lastEmergencyContactName;
	@FindBy(css = "#content > div:nth-of-type(5) > div > div:nth-last-of-type(2) > span:nth-of-type(5) > img:nth-of-type(1)")
	WebElement lastEmergencyContactDelete;
	

	public PersonFilePage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "NO URL";
		this.PAGE_TITLE = "NO TITLE";
		PageFactory.initElements(driver, this);
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

	public void changeTitle(String title, String departament) throws Exception {
		waitPageIsLoad();
		swichToFirstFrame(driver);
		clickAButton(nameLink);
		swichToIframe(editNameintoTitleiFrame);
		setTextInField(titleField, title);
		setTextInField(departamentField, departament);
		clickAButton(saveChangeTitle);
		waitUntilnotVisibility(saveChangeTitle);
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

	private String getPhoneNumber(String phoneNumber, String runID) {
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

	public void assignTask() throws Exception {
		List<Task> tasks = Task.getTasks();
		waitPageIsLoad();
		for (Task task : tasks) {
			swichToFirstFrame(driver);
			clickAButton(tasksTab);
			swichToIframe(tabiFrame);
			clickAButton(assignTaskButton);
			swichToFirstFrame(driver);
			waitUntilIsLoaded(taskName);
			clickAButton(taskName);
			taskName.sendKeys(task.getDetail());
			// taskName.sendKeys(Keys.TAB);
			waitUntilIsLoaded(taskInstuctions);
			setTextInField(taskInstuctions, task.getBasicTaskInstruction());
			swichToIframe(AdditionalInstructioniFrame);
			waitUntilIsLoaded(taskAdditionalInstructions);
			setTextInField(taskAdditionalInstructions,
					task.getAdditionalInstruction());
			swichToFirstFrame(driver);
			clickAButton(btnAssignTask);
		}
	}

	public void assignChecklist() throws Exception {
		waitPageIsLoad();
		openTaskTab();
		swichToIframe(tabiFrame);
		goToRunninChecklist();
		clickOnStartChecklist();
		swichToIframe(startChecklistiFrame);
		waitUntilIsLoaded(checkListsCombo);
		setTextInCombo(checkListsCombo, "Test Template");
		clickAButton(assignChecklistButton);
		Sleeper.sleep(8000, driver);
	}

	public void addSocialSecurityNumber(String ssnNumber, String runID) throws Exception {
		waitPageIsLoad();
		clickAButton(addSocialSecurityNumber);
		swichToIframe(socialSecurtyiFrame);
		clickAButton(ssnInsert);
		setTextInField(ssnTextField, getSSN(ssnNumber, runID));
		clickAButton(saveSsn);
	}
	
	private String getSSN(String ssn, String runID) {
		String ssnRet = "";
		ssnRet = ssn.substring(0,ssn.length()-runID.length());
		return ssnRet+runID;
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

	/* Check if is complete Methods */
	public boolean isChangePrimaryEmail(String email) throws Exception {
		Sleeper.sleep(4000, driver);
		waitPageIsLoad();
		return primaryEmailLink.getText().equals(email);
	}

	public boolean isChangeName(PersonData personData, Wait wait)
			throws Exception {
		if (wait.isWait())
			Sleeper.sleep(18000, driver);
		waitPageIsLoad();

		waitUntilIsLoaded(fullNameLink);
		boolean fullNameAssert = fullNameLink.getText().equals(getNameToAssertInPersonalDetails(personData));

		swichToFirstFrame(driver);
		String assertTitleName = personData.getName()+ " " + personData.getLastName();
		boolean titleNameAssert = nameLink.getText().equals(assertTitleName);

		return fullNameAssert && titleNameAssert;
	}
	
	private Object getNameToAssertInPersonalDetails(PersonData personData) {
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

	public boolean isChangePeronDetailBeforeCreatePerson(PersonData personData, Wait wait)
			throws Exception {
		if (wait.isWait()) Sleeper.sleep(18000, driver);
		waitPageIsLoad();
		
		String assertTitleName = personData.getName()+ " " + personData.getLastName();
				waitUntilIsLoaded(fullNameLink);
				boolean fullNameAssert = fullNameLink.getText().equals(assertTitleName);
				
				swichToFirstFrame(driver);
				boolean titleNameAssert = nameLink.getText().equals(assertTitleName);
				
				return fullNameAssert && titleNameAssert;
	}

	public boolean isChangeGender(Gender genderType) throws Exception {
		waitPageIsLoad();
		return genderLink.getText().equals(genderType.name);
	}

	public boolean isChangeMaritalStatus(MaritalStatus maritalStat)
			throws Exception {
		Sleeper.sleep(4000, driver);
		waitPageIsLoad();
		return maritalLink.getText().equals(maritalStat.status);
	}

	public boolean isChangeTitle(String title, String departament)
			throws Exception {
		Sleeper.sleep(5000, driver);
		waitPageIsLoad();
		swichToFirstFrame(driver);
		boolean assertTitle = ((titleLink == null && departament.equals("")) || titleLink
				.getText().equals(title));
		boolean assertDepartament = ((departamentLink == null && departament
				.equals("")) || departamentLink.getText().equals(departament));
		return assertDepartament && assertTitle;
	}

	public boolean isNotDependents() {
		return hasNotDependentsLabel.getText().equals("Has no dependents");
	}

	public boolean isAddPhoneNumber(String phoneNumber, String runID) throws Exception {
		Sleeper.sleep(7000, driver);
		waitPageIsLoad();
		waitUntilnotVisibility(loadingSpinner);
		String phoneNumberFinal = getPhoneNumber(phoneNumber, runID);
		String assertNumber = "(" + phoneNumberFinal.substring(0, 3) + ") "
				+ phoneNumberFinal.substring(3, 6) + "-"
				+ phoneNumberFinal.substring(6, 10);
		return mobilePhoneNumber.getText().equals(assertNumber);
	}

	public boolean isAddUSAddress(USAddress usAddress) throws Exception {
		waitPageIsLoad();
		return addressSaved.getText().contains(usAddress.getStreet());
	}

	public boolean isChangeBirthDate(String birthDate) throws Exception {
		Sleeper.sleep(5000, driver);
		waitPageIsLoad();
		String[] birthDateSplit = birthDate.split("/");
		String birthDateAssert = getShortMonth(birthDateSplit[0])  + "-" + birthDateSplit[1]
			 + "-" + birthDateSplit[2];
		return birthDateLink.getText().equals(birthDateAssert);
	}

	public boolean isTaskAssigned() throws Exception {
		boolean result;
		try{
			Sleeper.sleep(8000, driver);
			waitTaskTabIsLoad();
			List<Task> tasks = Task.getTasks();
			result = firstTaskLink.getText().equals(tasks.get(0).getDetail());
		} catch(Exception ex) {
			result = false;
		} finally {
			goToPersonalTab();
		}
		return result;
	}
	
	public void goToPersonalTab() throws Exception{
		swichToFirstFrame(driver);
		clickAButton(personalLink);
	}

	public boolean isChecklistAssigned() throws Exception {
		boolean result;
		try {
			Sleeper.sleep(3000, driver);
			waitUntilnotVisibility(loadingSpinner);
			swichToFirstFrame(driver);
			clickAButton(tasksTab);
			swichToIframe(tabiFrame);
			clickAButton(runningChecklist);
			waitUntilIsLoaded(firstChecklist);
			result  =  firstChecklist.getText().equals("Test Template");
		} catch(Exception ex){
			throw ex;
		} finally {
			goToPersonalTab();
		}
		return result;
	}

	public boolean isRemoveUsAddress(USAddress usAddress) {
		Sleeper.sleep(4000, driver);
		return searchAddress(usAddress) == null;
	}

	public boolean isSocialSecurityNumberAdded(String ssnNumber, String runID)
			throws Exception {
		Sleeper.sleep(5000, driver);
		waitPageIsLoad();
		return addSocialSecurityNumber.getText().substring(7, 11)
				.equals(getSSN(ssnNumber, runID).substring(5, 9));
	}

	public boolean isEmergencyContactAdded(EmergencyContact emg)
			throws Exception {
		Sleeper.sleep(5000, driver);
		waitPageIsLoad();		
		return lastEmergencyContactName.getText().equals(emg.getName());

	}
	
	public boolean isEmergencyContactRemoved(int count)
			throws Exception {
		Sleeper.sleep(5000, driver);
		waitPageIsLoad();		
		return this.getNumberOfEmergencyContacts() < count;

	}
	
	
	/* Waits Methods */
	@Override
	public void waitPageIsLoad() throws Exception {
		swichToFirstFrame(driver);
		waitUntilnotVisibility(loadingSpinner);
		waitUntilIsLoaded(nameLink);
		swichToIframe(tabiFrame);
		waitUntilIsLoaded(genderLink);
		waitUntilIsLoaded(maritalLink);
		waitUntilIsLoaded(primaryEmailLink);
		waitUntilIsLoaded(fullNameLink);
		Sleeper.sleep(2000, driver);
	}

	public void waitTaskTabIsLoad() throws Exception {
		swichToFirstFrame(driver);
		waitUntilnotVisibility(loadingSpinner);
		waitUntilIsLoaded(nameLink);
		swichToIframe(tabiFrame);
		waitUntilIsLoaded(firstTaskLink);
		Sleeper.sleep(2000, driver);
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
	/* Utilities */
	public int getNumberOfEmergencyContacts () throws Exception
	{
		waitPageIsLoad();
     	return driver.findElements(By.cssSelector("#content > div:nth-of-type(5) > div > div")).size();
	
	}

	/* Private Methods */
	private void waitUsAddresFrameIsLoad() throws Exception {
		waitUntilIsLoaded(streetAddressField);
		waitUntilIsLoaded(streetAddressOptionalField);
		waitUntilIsLoaded(cityField);
		waitUntilIsLoaded(stateCombo);
		waitUntilIsLoaded(zipCodeField);
		waitUntilIsLoaded(countyCombo);
	}
	
	private WebElement searchAddress(USAddress usAddress) {
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
	
	private void completeAddressForm(USAddress usAddress) throws Exception {
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
	private String getDay(String number) {
		if(Integer.parseInt(number) < 10) {
			Integer numberInt = Integer.parseInt(number);
			return numberInt.toString();
		} else {
			return number;
		}
	}

	private String getMonth(String number) {
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

	private String getShortMonth(String number) {
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
	private boolean isTheSameAddress(WebElement address, USAddress usAddress) {
		return address.getText().contains(usAddress.getCity())
				&& address.getText().contains(usAddress.getCounty())
				&& address.getText().contains(usAddress.getStreet())
				&& address.getText().contains(usAddress.getZipCode());
	}

	private void openTaskTab() throws Exception {
		swichToFirstFrame(driver);
		clickAButton(tasksTab);
	}

	private void goToRunninChecklist() throws Exception {
		waitUntilIsLoaded(runningChecklist);
		clickAButton(runningChecklist);
	}

	private void clickOnStartChecklist() throws Exception {
		Sleeper.sleep(3000, driver);
		waitUntilIsLoaded(startChecklistButton);
		clickAButton(startChecklistButton);
	}
	

	// TODO METHODS
	@Override
	public boolean isPageLoad() {
		// TODO Auto-generated method stub
		return false;
	}

	public void updateUsAddress(USAddress usAddress) throws Exception {
		// TODO Auto-generated method stub
	}

	public boolean isUpdateUsAddress(USAddress usAddress) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isThisPerson(PersonData personData) throws Exception {
		boolean changeTitle = isChangeTitle(personData.getTitleOfEmployee(),
				personData.getDepartamentOfEmployee());
		boolean changeFullName = isChangePeronDetailBeforeCreatePerson(personData, Wait.NOWAIT);

		return changeTitle && changeFullName;
	}
}
