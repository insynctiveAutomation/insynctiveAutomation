package insynctive.pages.insynctive;

import insynctive.pages.Page;
import insynctive.pages.PageInterface;
import insynctive.pages.insynctive.exception.ElementNotFoundException;
import insynctive.utils.Checklist;
import insynctive.utils.PersonData;
import insynctive.utils.Sleeper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

//TODO get subject of email.
public class HomeForAgentsPage extends Page implements PageInterface {

	String enviroment;
	String emailSubject;

	/* Home */
	@FindBy(id = "btnAddPerson")
	WebElement addPersonButton;
	@FindBy(id = "lblName")
	WebElement loginUserFullName;
	@FindBy(id = "grid")
	WebElement personTable;
	@FindBy(id = "txtName")
	WebElement nameSearch;
	@FindBy(id = "txtPrimaryEmail")
	WebElement emailSearch;
	@FindBy(xpath = "//*[@id='grid']/div[1]/div[2]/p")
	WebElement personLink;
	@FindBy(xpath = "//*[@id='grid']/div[1]/div[6]/p")
	WebElement emailAssert;
	@FindBy(id = "loadingSpinner")
	WebElement loadingSpinner;
	@FindBy(id = "lTasks")
	private WebElement tasksLink;
	
	/* ADD Person */
	@FindBy(id = "addperson_firstname")
	WebElement firstNameTextBox;
	@FindBy(id = "addperson_lastname")
	WebElement lastNameTextBox;
	@FindBy(id = "addperson_email")
	WebElement emailTextBox;
	@FindBy(id = "addperson_title")
	WebElement titleTextBox;
	@FindBy(id = "addperson_department")
	WebElement departmentTextBox;
	@FindBy(id = "addperson_chkopenpersonfile")
	WebElement openPersonFileCheckBox;
	@FindBy(id = "addperson_chkskipselfservice")
	WebElement skipInvitationToSelfServiceCheckBox;
	@FindBy(id = "btnSave")
	WebElement saveNewEmployeeButton;
	
	//Invitation
	@FindBy(id = "turnonss_email")
	WebElement inviteEmailField;
	@FindBy(id = "btnSkipInvite")
	WebElement skipInviteButton;
	@FindBy(id = "btnSendInvite")
	WebElement sendInviteButton;
	@FindBy(id = "txtSubject")
	WebElement subjectOfEmail;
	@FindBy(id = "lnkRestore")
	WebElement restoreLink;
	
	/* Person File */
	@FindBy(id = "popupCustom_CIF-1")
	WebElement personFIleIframe;
	@FindBy(id = "body_txtEmail")
	WebElement emailLink;
	@FindBy(id = "JQWindowBigOverlayCenterHeader")
	WebElement personFileTitle;

	/* Import Persons */
	@FindBy(id = "btnImportAndSync")
	WebElement importPersonButton;
	
	/* Template Page */
	@FindBy(id = "tab_Apps")
	WebElement tabApp;

	public HomeForAgentsPage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://" + enviroment + ".insynctiveapps.com/Insynctive.Hub/Protected/Invitations.aspx?SkipGuide=True";
		this.PAGE_TITLE = "Invitations";
		PageFactory.initElements(driver, this);
	}
	
	/* Actions **/
	public void createPersonCheckingInviteSS(PersonData personData) throws Exception {
		openCreatePersonFrame();
		setTextInField(firstNameTextBox, personData.getName());
		setTextInField(lastNameTextBox, personData.getLastName());
		setTextInField(titleTextBox, personData.getTitleOfEmployee());
		setTextInField(departmentTextBox, personData.getDepartamentOfEmployee());
		setTextInField(emailTextBox, personData.getEmail());
		clickAButton(openPersonFileCheckBox);
		Sleeper.sleep(3500, driver);
		clickAButton(saveNewEmployeeButton);
		//TODO To the correct message [<User> added [admin/agent/employee]  <Person Name>]
		String assertMessage = loginUserFullName.getText()+" added employee "+personData.getName()+" "+personData.getLastName();
		checkInAppMessage(assertMessage); 
	}

	private void openCreatePersonFrame() throws Exception {
		waitPageIsLoad();
		clickAButton(addPersonButton);
		waitAddPersonIsLoad();
	}
	
	public void sendInviteEmail(PersonData personData) throws Exception {
		waitUntilInvitePanelIsLoad();
		setTextInField(inviteEmailField, personData.getEmail());
		Sleeper.sleep(3500, driver);
		clickAButton(sendInviteButton);
		
		String assertMessage = "Invitation to Sign up was sent to "+personData.getName()+" "+personData.getLastName();
		checkInAppMessage(assertMessage);
	}

	public void openPersonFile(String personName) throws Exception {
		waitPageIsLoad(); 
		emailSearch.sendKeys(personName);
		waitUntilnotVisibility(loadingSpinner);
		Sleeper.sleep(8000,driver);
		waitUntilIsLoaded(personLink);
		clickAButton(personLink);
	}
	
	public void importPersons() throws Exception{
		waitUntilIsLoaded(importPersonButton);
		clickAButton(importPersonButton);
		
		ImportPersonPage importPersonPage = new ImportPersonPage(driver, enviroment);
		importPersonPage.importPersons();
	}
	
	public void goToApps() throws Exception{
		waitUntilIsLoaded(tabApp);
		tabApp.click();
	}
	
	/* Waits **/
	public void waitPageIsLoad() throws Exception {
		waitUntilIsLoaded(importPersonButton);
		waitUntilIsLoaded(personTable);
	}

	public void waitAddPersonIsLoad() throws Exception {
		waitUntilIsLoaded(firstNameTextBox);
		waitUntilIsLoaded(lastNameTextBox);
		waitUntilIsLoaded(emailTextBox);
		waitUntilIsLoaded(titleTextBox);
		waitUntilIsLoaded(departmentTextBox);
		waitUntilIsLoaded(skipInvitationToSelfServiceCheckBox);
		waitUntilIsLoaded(saveNewEmployeeButton);
		waitUntilIsLoaded(openPersonFileCheckBox);
	}
	
	public void waitUntilInvitePanelIsLoad() throws Exception {
		waitUntilIsLoaded(skipInviteButton);
		waitUntilIsLoaded(inviteEmailField);
		waitUntilIsLoaded(sendInviteButton);
	}

	/* Checks **/
	public boolean isPageLoad(){
		return importPersonButton.isDisplayed() 
				&& personFIleIframe.isDisplayed();
	}
	
	public boolean checkIfPersonIsCreated(PersonData personData) throws Exception {
			PersonFilePage personFile = new PersonFilePage(driver, enviroment);
			personFile.waitPageIsLoad();
			return personFile.isThisPerson(personData);
	}

	public boolean isPersonFileOpened() {
		return personFileTitle.getText().equals("Person");
	}

	public void goToTasks() throws Exception{
		clickAButton(tasksLink);
		Sleeper.sleep(3000, driver);
	}

	
	public boolean isTaskAssign(Checklist checklist) throws ElementNotFoundException {
		return isElementPresent(findElementByText("a", checklist.getProcess().get(0).taskName));
	}

	public void openTask(Checklist checklist) throws ElementNotFoundException {
		findElementByText("a", checklist.getProcess().get(0).taskName).click();;
	}
}
