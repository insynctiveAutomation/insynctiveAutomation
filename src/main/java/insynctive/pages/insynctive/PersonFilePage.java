package insynctive.pages.insynctive;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.exception.ElementNotFoundException;
import insynctive.model.EmergencyContact;
import insynctive.model.ParamObject;
import insynctive.model.ParamObject.Gender;
import insynctive.model.ParamObject.MaritalStatus;
import insynctive.model.USAddress;
import insynctive.pages.PageInterface;
import insynctive.pages.PersonalPage;
import insynctive.utils.Sleeper;
import insynctive.utils.Wait;
import insynctive.utils.data.Task;

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
	@FindBy(css = "#statusesListHeader > li:nth-child(6)")
	WebElement documentTab;
	
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
	@FindBy(xpath = "(//div[contains(@class, 'runningchecklist_checklistcolumn')])[1]/span")
	WebElement firstChecklist;
	@FindBy(css = "#pendingTasksList > div.task-list > div:nth-child(1) > span > div")
	WebElement firstTaskLink;
	@FindBy(id = "froalaEditor")
	WebElement AdditionalInstructioniFrame;
	@FindBy(id = "mobile-phone")
	WebElement mobilePhoneNumber;
	@FindBy(id = "work-phone")
	List<WebElement> phones;
	@FindBy(id = "JQWindowBigOverlayBreadcrumbTitle")
	WebElement goToPerson;

	@FindBy(css = "#content > div:nth-of-type(5) > div > div")
	List<WebElement> emergencyContacts;

	@FindBy(id = "pcc-pageList-viewer1")
	WebElement documentElement;
	@FindBy(id = "bigoverlayiframe")
	WebElement documentiFrame;

	@FindBy(id = "employer-dd")
	WebElement employer;
	@FindBy(css = "#employer-dd > option:nth-child(2)")
	WebElement firstEmployerInCombo;
	@FindBy(id = "worklocation-dd")
	WebElement workLocation;
	@FindBy(css = "div.search-row:nth-child(1)")
	WebElement firstWorkLocation;

	@FindBy(className = "doc-name")
	List<WebElement> docRows;
	
	
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
				setTextInField(taskName, task.getDetail());
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
			returnToPerson();
			throw ex;
		}
	}

	public void assignChecklist() throws Exception {
		assignChecklist("Test Template");
	}
	
	
	public void assignChecklist(String checklistName) throws Exception {
		waitPageIsLoad();
		openTaskTab();
		swichToIframe(tabiFrame);
		goToRunninChecklist();
		clickAButton(startChecklistButton);
		swichToIframe(startChecklistiFrame);
		Sleeper.sleep(3000, driver);
		//TODO MOVE Test Template TO DB
		clickAButton(checkListsCombo);
		Sleeper.sleep(1500, driver);
		selectElementInComboWithoutClickCombo(selectChecklist, checklistName, "li");
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
			clickAButton(addJobButon);//CHANGE
			swichToFirstFrame(driver);
			setTextInField(dateInput, "10-05-2000", 1000);
			setTextInField(categoryField, "My Role", 1000);
			try{ selectElementInDefaultCombo(employer, "Staging"); } catch(Exception ex){}
			clickAButton(workLocation, 1000);
			clickAButton(firstWorkLocation, 1000);
			setTextInField(rateInput, "1000", 1000);
			clickAButton(btnActivate);
		} catch(Exception ex){
			goToPersonalTab();   
			throw ex;
		}
	}

	/* Check if is complete Methods */
	public boolean isChangePrimaryEmail(String email) throws Exception {
		Sleeper.sleep(6000, driver);
		waitPageIsLoad();
		return primaryEmailLink.getText().equals(email);
	}

	public boolean isChangeNameInPersonalDetails(ParamObject personData, Wait wait)
			throws Exception {
		if (wait.isWait())
			Sleeper.sleep(18000, driver);
		waitPageIsLoad();
		return isElementTextEquals(fullNameLink, getNameToAssertInPersonalDetails(personData)); 
	}
	
	public boolean isChangeNameInTitle(ParamObject personData, Wait wait){
		if (wait.isWait()){Sleeper.sleep(18000, driver);}
		
		swichToFirstFrame(driver);
		String assertTitleName = personData.getName()+ " " + personData.getLastName();
		
		return nameLink.getText().equals(assertTitleName);
	}

	public boolean isChangePeronDetailBeforeCreatePerson(ParamObject personData, Wait wait)
			throws Exception {
		if (wait.isWait()) Sleeper.sleep(18000, driver);
		waitPageIsLoad();
		
		String assertTitleName = personData.getName()+ " " + personData.getLastName();
		boolean fullNameAssert = isElementTextEquals(fullNameLink, assertTitleName);
		
		swichToFirstFrame(driver);
		boolean titleNameAssert = isElementTextEquals(nameLink, assertTitleName);
		
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
		try{
			Sleeper.sleep(7000, driver);
			waitTaskTabIsLoad();
			List<Task> tasks = Task.getTasks();
			return isElementTextEquals(firstTaskLink, tasks.get(0).getDetail());
		} catch(Exception ex) {
			throw ex;
		}
	}

	public boolean isChecklistAssigned() throws Exception {
		return isChecklistAssigned("Employee Onboarding");
	}
//	Checklist Test Template started successfully for Automated 64 Valeiras Test
	public boolean isChecklistAssigned(String checklistName) throws Exception {
		try {
			swichToFirstFrame(driver);
			swichToIframe(tabiFrame);
			Boolean result = isElementTextEquals(firstChecklist, checklistName);
			swichToFirstFrame(driver);
			return result;
		} catch(Exception ex){
			throw ex;
		}
	}

	public boolean isRemoveUsAddress(USAddress usAddress) {
		Sleeper.sleep(6000, driver);
		return searchAddress(usAddress) == null;
	}

	public boolean isSocialSecurityNumberAdded(String ssnNumber, String runID)
			throws Exception {
		Sleeper.sleep(8000, driver);
		waitPageIsLoad();
		return addSocialSecurityNumber.getText().substring(7, 11)
				.equals(getSSN(ssnNumber, runID).substring(5, 9));
	}

	public boolean isEmergencyContactAdded(EmergencyContact emg)
			throws Exception {
		Sleeper.sleep(5000, driver);
		waitPageIsLoad();		
		return lastEmergencyContactGrid.get(0).getText().equals(emg.getName());

	}
	
	public boolean isEmergencyContactRemoved(int count)
			throws Exception {
		Sleeper.sleep(5000, driver);
		waitPageIsLoad();		
		return this.getNumberOfEmergencyContacts() < count;
	}

	public boolean isChangePrimaryPhone(Map<String, String> resultsPhones) throws Exception {
		Sleeper.sleep(9000, driver);
		waitPageIsLoad();
		return phoneNumberLink.getText().equals(resultsPhones.get("newPrimaryPhone")) && alternativePhoneLink.getText().equals(resultsPhones.get("oldPrimaryPhone"));
	}

	public Boolean isJobAdded() throws Exception {
		try { 
			//TODO
		} 
		
		catch (Exception ex){ throw ex;} 
		
		finally {
			Sleeper.sleep(7500);
			goToPersonalTab();
		}
		
		return true;
	}
	
	public boolean isThisPerson(ParamObject personData) throws Exception {
		boolean changeFullName = isChangePeronDetailBeforeCreatePerson(personData, Wait.NOWAIT);

		return changeFullName;
	}

	public boolean isAddAlternativePhoneNumber(String primaryPhone, String runIDString) throws Exception {
		Sleeper.sleep(8000, driver);
		waitPageIsLoad();
		List<WebElement> elements = phones;
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
     	return emergencyContacts.size();
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

	public void openDocument(String documentName) throws Exception {
		clickAButton(3000, findViewDocumentBtn(documentName));
	}

	public WebElement findViewDocumentBtn(String documentName) throws ElementNotFoundException, Exception {
		swichToFirstFrame(driver);
		swichToIframe(tabiFrame);
		WebElement docWithText = findElementByTextInList(docRows, documentName);
		WebElement viewBtn = docWithText.findElement(By.xpath("./../../..//div[@class = 'doc-view']")); 
				
		return viewBtn;
	}
	
	public boolean isOpenDocument() throws Exception {
		Sleeper.sleep(3000, driver);
		swichToFirstFrame(driver);
		swichToIframe(documentiFrame);
		waitUntilIsLoaded(documentElement);
		List<WebElement> pageInDocument = documentElement.findElements(By.xpath(".//div[contains(@class, 'igAnchor')]"));
		return pageInDocument.size() > 0;
	}
	
	public void returnToPerson() throws Exception{
		swichToFirstFrame(driver);
		clickAButton(goToPerson);
	}
	
	public void goToPersonalTab() throws Exception{
		swichToFirstFrame(driver);
		clickAButton(personalTab);
	}

	public void goToDocumentsTab() throws Exception {
		swichToFirstFrame(driver);
		clickAButton(documentTab);
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
