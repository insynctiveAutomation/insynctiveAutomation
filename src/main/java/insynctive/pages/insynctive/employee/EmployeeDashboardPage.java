package insynctive.pages.insynctive.employee;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.exception.ElementNotFoundException;
import insynctive.model.Account;
import insynctive.model.InsynctiveProperty;
import insynctive.model.PersonData;
import insynctive.pages.Page;
import insynctive.pages.PageInterface;
import insynctive.pages.PersonalPage;
import insynctive.utils.Sleeper;

public class EmployeeDashboardPage extends Page implements PageInterface {

	//Title
	@FindBy(id = "name")
	WebElement fullNameInTitle;
	@FindBy(id = "position")
	WebElement positionInTitle;
	@FindBy(id = "department")
	WebElement departmentInTitle;
	@FindBy(id = "mail")
	WebElement mailInTitle;
	@FindBy(id = "number")
	WebElement phoneNumberInTitle;
	
	//Stats Panel
	@FindBy(id = "open-tasks")
	WebElement openTasksNumber;
	@FindBy(id = "overdue-tasks")
	WebElement overdueTasksNumber;
	@FindBy(id = "questions")
	WebElement openQuestionsNumber;
	
	//Banner Buttons
	@FindBy(id = "buttonFirst")
	WebElement buttonFirstStep;
	@FindBy(id = "buttonSecond")
	WebElement buttonSecondStep;
	@FindBy(id = "buttonThird")
	WebElement buttonThirdStep;

	//Update Personal Information
	@FindBy(id = "buttonDoneLadda")
	WebElement doneButton;
	@FindBy(id = "frmUpdateInfoFromBanner")
	WebElement updateInformationFrame;
	
	//Elect Your Benefits
	@FindBy(id = "frmTask")
	WebElement formTaskFrame;
	@FindBy(className = "address-edit-popover")
	WebElement signatureFrame;
	
	@FindBy(id = "categoryheader-1")
	WebElement medicalPanel;
	@FindBy(id = "categoryheader-2")
	WebElement dentalPanel;
	@FindBy(id = "categoryheader-3")
	WebElement visionPanel;
	@FindBy(id = "circle-empty-1-1")
	WebElement medicalSelectorFirst;
	@FindBy(id = "circle-empty-1-2")
	WebElement medicalSelectorSecond;
	@FindBy(id = "circle-empty-1-3")
	WebElement medicalSelectorThird;
	@FindBy(id = "circle-empty-2-1")
	WebElement dentalSelectorFirst;
	@FindBy(id = "circle-empty-2-2")
	WebElement dentalSelectorSecond;
	@FindBy(id = "circle-empty-2-3")
	WebElement dentalSelectorThird;
	@FindBy(id = "circle-empty-3-1")
	WebElement visionSelectorFirst;
	@FindBy(id = "circle-empty-3-2")
	WebElement visionSelectorSecond;
	@FindBy(id = "circle-empty-3-3")
	WebElement visionSelectorThird;
	@FindBy(id = "buttonDoneLadda")
	WebElement btnSendInvite;
	@FindBy(className = "buttonMedium")
	WebElement signBtn;
	@FindBy(id = "panelSign_name")
	WebElement signPanel;
	@FindBy(id = "btnApplySig_CD")
	WebElement applySignature;
	@FindBy(id = "buttonDoneLadda")
	WebElement doneBtn;
	@FindBy(id = "btnfinalizeelecttask")
	WebElement acknowledgeBtn;
	@FindBy(css = "#BodyTag > div.standard-popover.top.in > div.standard-popover-inner > div.standard-popover-content")
	WebElement benefitsToEnroll;
	@FindBy(id = "buttonDoneLadda")
	WebElement signTaskBtn;
	@FindBy(css = "#popupSign_panelSign > div.sigPad > ul > li.drawIt > a")
	WebElement drawBtn;
	@FindBy(css = "#popupSign_panelSign > div.sigPad > ul > li.typeIt > a")
	WebElement typeBtn;
	@FindBy(id = "popupSign_panelSign_name")
	WebElement typeHereField;
	@FindBy(css = "#btnApplySig_CD > span")
	private WebElement apllySignature;
	@FindBy(id = "rappdf")
	private WebElement rapPDFFrame;
	
	
	public EmployeeDashboardPage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://" + enviroment + ".insynctiveapps.com/Insynctive.Hub/Protected/Dashboard.aspx?SkipGuide=True";
		this.PAGE_TITLE = "Dashboard";
		PageFactory.initElements(driver, this);
	}
	
	public Boolean isAllDataOK(PersonData person){
		String personFullName = person.getName()+" "+person.getLastName();
		PersonalPage personalPage = new EmployeePersonalPage(driver, enviroment);
		
		return equals(mailInTitle, person.getEmail()) 
				&& equals(phoneNumberInTitle, (person.getPrimaryPhone() != null ? personalPage.parsePhoneNumber(person.getPrimaryPhone()) : "No cell phone")) 
				&& equals(fullNameInTitle, personFullName)
				&& equals(labelNameInHeader, personFullName);
	}
	
	public void updatePersonalInformation(PersonData person, String runID) throws Exception{
		clickAButton(buttonFirstStep);
		PersonalPage personalPage = new EmployeePersonalPage(driver, enviroment);
		personalPage.tabiFrame = updateInformationFrame;
		
		personalPage.changeBirthDate(person.getBirthDate());
		personalPage.changeGender(person.getGender());
		personalPage.changeMaritalStatus(person.getMaritalStatus());
		personalPage.addSocialSecurityNumber(person.getSsn(), runID);
		personalPage.addPhoneNumber(person.getPrimaryPhone(), runID);
		personalPage.addUsAddress(person.getUSAddress());
		personalPage.addHasNotDependents();
		swichToFirstFrame(driver);
		clickAButton(doneButton);
	}

	@Override
	public boolean isPageLoad() {
		return false;
	}

	public void electBenefits(PersonData person, String runID) throws Exception {
		//Elect Benefits
		clickAButton(buttonSecondStep);
		swichToIframe(formTaskFrame);
		clickAButton(medicalSelectorFirst);
		Sleeper.sleep(1000, driver);
		clickAButton(dentalPanel);
		clickAButton(dentalSelectorFirst);
		Sleeper.sleep(1000, driver);
		clickAButton(visionPanel);
		clickAButton(visionSelectorFirst);
		Sleeper.sleep(1000, driver);
		clickAButton(btnSendInvite);
		
		//Sign Benefits
		clickAButton(signBtn);
		swichToIframe(signatureFrame);
		setTextInField(signPanel, "Employee");
		Sleeper.sleep(500, driver);
		clickAButton(applySignature);
		
		swichToFormTaskFrame();
		clickAButton(doneBtn);
		clickAButton(acknowledgeBtn);
	}
	
	public void fillAndSignBenefit(String benefitName) throws Exception{
		clickAButton(buttonThirdStep);
		selectElementInCombo(benefitsToEnroll, benefitName, "div");
		Sleeper.sleep(8000, driver);
		swichToFormTaskFrame();
		clickAButton(signTaskBtn);
		swichToIframe(rapPDFFrame);
		clickAButton(typeBtn);
		setElementText(typeHereField, "Employee");
		clickAButton(apllySignature);
		Sleeper.sleep(4000, driver);
		swichToFormTaskFrame();
		clickAButton(doneButton);
		swichToFirstFrame(driver);
	}
	
	public void swichToFormTaskFrame() throws IOException, InterruptedException, ElementNotFoundException{
		swichToFirstFrame(driver);
		swichToIframe(formTaskFrame);
	}
	
}