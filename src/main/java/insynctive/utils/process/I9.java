package insynctive.utils.process;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import insynctive.exception.ElementNotFoundException;
import insynctive.pages.PageInterface;
import insynctive.utils.Sleeper;
import insynctive.utils.USAddress;
import insynctive.utils.WhenStart;
import insynctive.utils.data.Employee;

public class I9 extends Process implements PageInterface {

	@FindBy(css = "#searchAppsResult > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > a:nth-child(1)")
	public WebElement processLink;
	
	/* Create Form */
	//Process Setting
	@FindBy(id = "representativeKey")
	WebElement companyRepresentative;
	@FindBy(id = "btnYourselfRepresentative")
	WebElement pickYourselt;
	
	//Start Conditions
	@FindBy(id = "changeStartConditions")
	WebElement changeStartCondition;
	@FindBy(id = "immeditStartConditions")
	WebElement startInmediatlyAfterCertainAct;
	@FindBy(id = "asapStartConditions")
	WebElement startASAP;
	
	/* Fill and Sign */
	//First Step
	@FindBy(id = "frmTask")
	WebElement taskiFrame;
	@FindBy(id = "ucI9_pnlWizard_tab1_tLastName_I")
	WebElement lastNameTask;
	@FindBy(id = "ucI9_pnlWizard_tab1_tFirstName_I")
	WebElement firstNameTask;
	@FindBy(id = "ucI9_pnlWizard_tab1_tMiddleName_I")
	WebElement middleNameTask;
	@FindBy(id = "ucI9_pnlWizard_tab1_tOtherName_I")
	WebElement otherNameTask;
	@FindBy(id = "ucI9_pnlWizard_footer1_btnTab1Next")
	WebElement nextToSecondTaskButton;

	//Second Step
	@FindBy(id = "ucI9_pnlWizard_tab1_ucAddress_lnkSort_CD")
	WebElement addAddressButton;
	@FindBy(id = "txtStreetAddress_I")
	WebElement streetAddressField;
	@FindBy(id = "txtApt_I")
	WebElement aptField;
	@FindBy(id = "txtCity_I")
	WebElement cityField;
	@FindBy(id = "txtState_I")
	WebElement stateField;
	@FindBy(id = "txtZIPCode_I")
	WebElement zipCodeField;
	@FindBy(id = "cmbCounty_I")
	WebElement countryCombo;
	@FindBy(id = "txtDescription_I")
	WebElement shortDescriptionField;
	@FindBy(id = "btnOk_CD")
	WebElement saveAddressButton;
	@FindBy(id = "ucI9_pnlWizard_footer1_btnTab1Next_CD")
	WebElement nextToThirdStepTaskButton;

	//Third Step
	@FindBy(id = "ucI9_pnlWizard_tab1_tSSN_I")
	WebElement ssnField;
	@FindBy(id = "ucI9_pnlWizard_tab1_tBirthDate_I")
	WebElement dateBirthField;
	@FindBy(id = "ucI9_pnlWizard_footer1_btnTab1Next_CD")
	WebElement nextToFourthButton;

	//Fourth Step
	@FindBy(id = "ucI9_pnlWizard_tab1_txtEmail_I")
	WebElement emailField;
	@FindBy(id = "ucI9_pnlWizard_tab1_txtMobile_I")
	WebElement mobilePhoneField;
	@FindBy(id = "ucI9_pnlWizard_footer1_btnTab1Next")
	WebElement nextToFifthButton;
	
	//Fifth Step
	@FindBy(id = "ucI9_pnlWizard_tab1_cmbStatus_I")
	WebElement prejuryField;
	@FindBy(id = "ucI9_pnlWizard_footer1_btnTab1Next_CD")
	WebElement nextToFinalStep;
	
	//Final Step
	@FindBy(css = "#filesUpload > div > div > input[type='file']")
	WebElement addDocumentButton;
	@FindBy(id = "panelDocs_cmdUpload_CD")
	WebElement uploadButton;
	@FindBy(id = "panelSign_name")
	WebElement signatureField;
	@FindBy(id = "panelSign_cmdFinish_CD")
	WebElement submitForm;
	@FindBy(id = "ucI9_pnlWizard_tab1_cmbStatus_DDD_L_LBI1T0")
	WebElement citizenOfTheUS;
	@FindBy(id = "panelDocs_cmdSkipUpload_CD")
	WebElement skipButton;
	
	
	public I9(WhenStart whenStart, Employee employee, WebDriver driver) {
		super(driver);
		this.whenStart = whenStart;
		this.employee = employee;
		taskName = "Fill in and sign I-9 Form";
	}

	@Override
	public void completeStepsToCreate() throws Exception {
		waitPageIsLoad();
		swichToIframe(contentiframe);
		clickAButton(companyRepresentative);
		clickAButton(pickYourselt);
		swichToFirstFrame(driver);
		clickAButton(nextButton);
		//TODO
//		clickAButton(changeStartCondition);
//		clickAButton(startASAP);
		Sleeper.sleep(4000, driver);
		clickAButton(nextButton);
		Sleeper.sleep(3000, driver);
	}
	
	@Override
	public void completeSteps() throws Exception{
		//Step 1
		completeStepOne();
		//Step 2
		completeStepTwo();
		//Step 3
		completeStepThree();
		//Step 4
		completeStepFourth();
		//Step 5
		completeStepFifth();
		//Final Step
		completeFinalStep();
	}

	private void completeFinalStep() throws Exception,
			ElementNotFoundException, IOException, InterruptedException {
		checkIfAllInformationIsOk();
//		swichToFirstFrame(driver);
//		swichToIframe(taskiFrame);
//		clickAButton(addDocumentButton);
//		UploadRobot.uploadPDF(driver);
//		clickAButton(uploadButton);
		clickAButton(skipButton);
		setTextInField(signatureField, employee.personData.toString());
		Sleeper.sleep(2000, driver);
		clickAButton(submitForm);
	}

	private void completeStepFifth() throws Exception {
		clickAButton(prejuryField);
		clickAButton(citizenOfTheUS);//select a citizen of the united states
		clickAButton(nextToFinalStep);
		Sleeper.sleep(6000, driver);
	}

	private void completeStepFourth() throws ElementNotFoundException,
			IOException, InterruptedException, Exception {
		//		if(!isElementTextEquals(emailField, employee.personData.getEmail())){throw new Exception("The information autocompleted in the fourth Step is wrong");};
				setTextInField(mobilePhoneField, employee.personData.getPrimaryPhone());
				clickAButton(nextToFifthButton);
				Sleeper.sleep(4000, driver);
	}

	private void completeStepThree() throws ElementNotFoundException,
			IOException, InterruptedException, Exception {
		setTextInField(ssnField, employee.personData.getSsn());
		setTextInField(dateBirthField, employee.personData.getBirthDate());
		clickAButton(nextToFourthButton);
		Sleeper.sleep(4000, driver);
	}

	private void completeStepTwo() throws Exception, ElementNotFoundException,
			IOException, InterruptedException {
		addUsAddress();
		clickAButton(nextToThirdStepTaskButton);
		Sleeper.sleep(4000, driver);
	}

	private void completeStepOne() throws IOException, InterruptedException,
			ElementNotFoundException, Exception {
		swichToFirstFrame(driver);
		swichToIframe(taskiFrame);
		checkIdFirstStepIsCompletedOk();
		clickAButton(nextToSecondTaskButton);
		Sleeper.sleep(4000, driver);
	}

	private void checkIfAllInformationIsOk() {
		// TODO Auto-generated method stub
	}

	private void checkIdFirstStepIsCompletedOk()
			throws IOException, InterruptedException, ElementNotFoundException,
			Exception {
		boolean isAllOk = true;
		isAllOk &= isElementValueEquals(lastNameTask, employee.personData.getLastName());
		isAllOk &= isElementValueEquals(firstNameTask, employee.personData.getName());
		isAllOk &= isElementValueEquals(middleNameTask, employee.personData.getMiddleName());
		if(!isAllOk){throw new Exception("The information autocompleted in the first Step is wrong");}
	}

	private void addUsAddress() throws Exception, ElementNotFoundException,
			IOException, InterruptedException {
		clickAButton(addAddressButton);
		USAddress address = employee.personData.getUSAddress();
		setTextInField(streetAddressField, address.getStreet());
		setTextInField(aptField, address.getApt());
		setTextInField(cityField, address.getCity());
		setTextInField(stateField, address.getState());
		setTextInField(zipCodeField, address.getZipCode());
		setTextInCombo(countryCombo, address.getCounty());
		setTextInField(shortDescriptionField, address.getShortDescription());
		clickAButton(saveAddressButton);
		Sleeper.sleep(6000, driver);
	}
	
	@Override
	public void waitPageIsLoad() throws IOException, InterruptedException, ElementNotFoundException {
		waitUntilIsLoaded(nextButton);
		swichToIframe(contentiframe);
		waitUntilIsLoaded(companyRepresentative);
		swichToFirstFrame(driver);
	}

	@Override
	public boolean isPageLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public WebElement getElement() throws IOException, InterruptedException, ElementNotFoundException{
		waitUntilIsLoaded(processLink);
		return processLink;
	}
}
