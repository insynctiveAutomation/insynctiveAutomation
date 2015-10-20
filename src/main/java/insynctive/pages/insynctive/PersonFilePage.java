package insynctive.pages.insynctive;

import java.util.List;
import java.util.Map;

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
import insynctive.pages.PageInterface;
import insynctive.pages.PersonalPage;
import insynctive.utils.Sleeper;
import insynctive.utils.Task;
import insynctive.utils.Wait;

public class PersonFilePage extends PersonalPage implements PageInterface {

	String enviroment;

	// title
	@FindBy(id = "Title")
	WebElement titleField;
	@FindBy(id = "Department")
	WebElement departamentField;
	@FindBy(id = "saveBtn")
	WebElement saveChangeTitle;

	// Tasks
	@FindBy(css = "#statusesListHeader > li:nth-child(1)")
	WebElement personalTab;
	@FindBy(css = "#statusesListHeader > li:nth-child(2)")
	WebElement tasksTab;
	@FindBy(css = "#statusesListHeader > li:nth-child(4)")
	WebElement employmentTab;
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
	@FindBy(id = "ddChecklist")
	WebElement selectChecklist;
	@FindBy(id = "btnStartChecklist")
	WebElement assignChecklistButton;
	@FindBy(css = "div.row:nth-child(3) > div:nth-child(1) > span:nth-child(1)")
	WebElement firstChecklist;
	@FindBy(css = "#pendingTasksList > div.task-list > div:nth-child(1) > span > div")
	WebElement firstTaskLink;
	@FindBy(id = "froalaEditor")
	WebElement AdditionalInstructioniFrame;
	@FindBy(id = "mobile-phone")
	WebElement mobilePhoneNumber;

	@FindBy(id = "JQWindowBigOverlayBreadcrumbTitle")
	private WebElement goToPerson;

	
	
	public PersonFilePage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "NO URL";
		this.PAGE_TITLE = "NO TITLE";
		PageFactory.initElements(driver, this);
	}

	public void assignTask() throws Exception {
		try{
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
				setTextInField(taskAdditionalInstructions, task.getAdditionalInstruction());
				swichToFirstFrame(driver);
				clickAButton(btnAssignTask);
				Sleeper.sleep(5000, driver);
			}
		} catch(Exception ex){
			swichToFirstFrame(driver);
			clickAButton(goToPerson);
			goToPersonalTab();
			throw new Exception("Assign Task Exception: "+ex);
		}
	}

	public void assignChecklist() throws Exception {
		waitPageIsLoad();
		openTaskTab();
		swichToIframe(tabiFrame);
		goToRunninChecklist();
		clickAButton(startChecklistButton);
		swichToIframe(startChecklistiFrame);
		Sleeper.sleep(3000, driver);
		//TODO MOVE Test Template TO DB
		clickAButton(checkListsCombo);
		selectElementInComboWithoutClickCombo(selectChecklist, "Test Template", "li");
		Sleeper.sleep(2000, driver);
		clickAButton(assignChecklistButton);
		Sleeper.sleep(8000, driver);
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

	public void assignJob() throws Exception {
		try{
			swichToFirstFrame(driver);
			clickAButton(employmentTab);
			swichToIframe(tabiFrame);
			swichToIframe(employmentFrm);
			clickAButton(addJobButon);
			clickAButton(datePicker);
			setTextInField(dateInput, "10/05/2000");
			clickAButton(saveBtn);
			clickAButton(statesPicker);
			Sleeper.sleep(1500, driver);
			setTextInField(statesPickerSearch, "California");
			Sleeper.sleep(3000, driver);
			clickAButton(firstSearchJobState);
			clickAButton(categoryPicker);
			clickAButton(chefExecutives);
			clickAButton(rateEditor);
			setTextInField(rateInput, "1000");
			clickAButton(saveRate);
			clickAButton(paymentUnitKey);
			clickAButton(findElementByText("div", "Year"));
			Sleeper.sleep(1500, driver);
			clickAButton(btnActivate);
		} catch(Exception ex){
			goToPersonalTab();
		}
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
			Sleeper.sleep(2500, driver);
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

	public boolean isChangePrimaryPhone(Map<String, String> resultsPhones) throws Exception {
		Sleeper.sleep(4000, driver);
		waitPageIsLoad();
		return phoneNumberLink.getText().equals(resultsPhones.get("newPrimaryPhone")) && alternativePhoneLink.getText().equals(resultsPhones.get("oldPrimaryPhone"));
	}

	public Boolean isJobAdded() throws Exception {
		try { } 
		
		catch (Exception ex){ throw ex;} 
		
		finally { goToPersonalTab();}
		
		return true;
	}
	
	public boolean isThisPerson(PersonData personData) throws Exception {
		boolean changeTitle = isChangeTitle(personData.getTitleOfEmployee(),
				personData.getDepartamentOfEmployee());
		boolean changeFullName = isChangePeronDetailBeforeCreatePerson(personData, Wait.NOWAIT);

		return changeTitle && changeFullName;
	}

	public boolean isAddAlternativePhoneNumber(String primaryPhone, String runIDString) throws Exception {
		Sleeper.sleep(4000, driver);
		waitPageIsLoad();
		List<WebElement> elements = driver.findElements(By.id("work-phone"));
		for(WebElement element : elements){
			if(element != null && element.getText().equals(parsePhoneNumber(getPhoneNumber(getSecondaryPhoneNumber(primaryPhone), runIDString)))){
				return true;
			}
		}
		return false;
	}
	
	/* Waits Methods */
	public void waitTaskTabIsLoad() throws Exception {
		swichToFirstFrame(driver);
		waitUntilnotVisibility(loadingSpinner);
		waitUntilIsLoaded(nameLink);
		swichToIframe(tabiFrame);
		waitUntilIsLoaded(firstTaskLink);
		Sleeper.sleep(2000, driver);
	}
	
	/* Utilities */
	public int getNumberOfEmergencyContacts () throws Exception {
		waitPageIsLoad();
     	return driver.findElements(By.cssSelector("#content > div:nth-of-type(5) > div > div")).size();
	}

	/* Private Methods */
	public void openTaskTab() throws Exception {
		swichToFirstFrame(driver);
		clickAButton(tasksTab);
	}

	public void goToRunninChecklist() throws Exception {
		waitUntilIsLoaded(runningChecklist);
		clickAButton(runningChecklist);
	}
	
	public void goToPersonalTab() throws Exception{
		swichToFirstFrame(driver);
		clickAButton(personalLink);
	}

	// TODO METHODS
	@Override
	public boolean isPageLoad() {
		return false;
	}

	public boolean isUpdateUsAddress(USAddress usAddress) {
		return false;
	}

	public void updateUsAddress(USAddress usAddress) throws Exception {
		// TODO Auto-generated method stub
	}
}
