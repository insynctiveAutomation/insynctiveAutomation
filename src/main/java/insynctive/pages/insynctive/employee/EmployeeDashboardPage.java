package insynctive.pages.insynctive.employee;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.model.Account;
import insynctive.model.InsynctiveProperty;
import insynctive.model.PersonData;
import insynctive.pages.Page;
import insynctive.pages.PageInterface;
import insynctive.pages.PersonalPage;

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
	
	public EmployeeDashboardPage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://" + enviroment + ".insynctiveapps.com/Insynctive.Hub/Protected/Dashboard.aspx?SkipGuide=True";
		this.PAGE_TITLE = "Dashboard";
		PageFactory.initElements(driver, this);
	}
	
	public Boolean isAllDataOK(PersonData person){
		String personFullName = person.getName()+" "+person.getLastName();
		
		return equals(mailInTitle, person.getEmail()) 
				&& equals(phoneNumberInTitle, (person.getPrimaryPhone() != null ? person.getPrimaryPhone() : "No cell phone")) 
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
	
}
