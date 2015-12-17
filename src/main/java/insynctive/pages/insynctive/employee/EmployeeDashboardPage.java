package insynctive.pages.insynctive.employee;

import insynctive.exception.ElementNotFoundException;
import insynctive.model.ParamObject;
import insynctive.pages.Page;
import insynctive.pages.PageInterface;
import insynctive.pages.PersonalPage;
import insynctive.utils.Sleeper;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.util.List;

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
    @FindBy(xpath = "//div[@id='buttonFirst' and @class='btnCompleted']")
    WebElement buttonFirstStepCompleted;
    @FindBy(id = "buttonSecond")
    WebElement buttonSecondStep;
    @FindBy(xpath = "//div[@id='buttonSecond' and @class='btnCompleted']")
    WebElement buttonSecondStepCompleted;
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
    WebElement apllySignature;
    @FindBy(id = "rappdf")
    WebElement rapPDFFrame;

    @FindBy(css = "div#selectionpart > div > div > div > div > table > tbody > tr > td > div > div.leftproduct > span")
    List<WebElement> benefitsSpans;

    @FindBy(css = "#hiddenContent > div.validation-box > div")
    WebElement missingInformationDiv;

    //Personal Tab
    @FindBy(id = "linkMyInfo")
    WebElement persoanlTab;
    @FindBy(id = "tds_body_myinfopersonal")
    WebElement personaliFrame;


    public EmployeeDashboardPage(WebDriver driver, String enviroment) {
        super(driver);
        this.enviroment = enviroment;
        this.PAGE_URL = "http://" + enviroment + ".insynctiveapps.com/Insynctive.Hub/Protected/Dashboard.aspx?SkipGuide=True";
        this.PAGE_TITLE = "Dashboard";
        PageFactory.initElements(driver, this);
    }

    public Boolean isAllDataOK(ParamObject person) {
        String personFullName = person.getName() + " " + person.getLastName();
        PersonalPage personalPage = new EmployeePersonalPage(driver, enviroment);

        return equals(mailInTitle, person.getEmail())
                && equals(phoneNumberInTitle, (person.getPrimaryPhone() != null ? personalPage.parsePhoneNumber(person.getPrimaryPhone()) : "No cell phone"))
                && equals(fullNameInTitle, personFullName)
                && equals(labelNameInHeader, personFullName);
    }

    public void updatePersonalInformationHappyPath(ParamObject person, String runID) throws Exception {
        clickAButton(buttonFirstStep);
        Sleeper.sleep(7000, driver);

        PersonalPage personalPage = new EmployeePersonalPage(driver, enviroment);
        personalPage.tabiFrame = updateInformationFrame;

        if (personalPage.isPresent(personalPage.birthDateRequired)) personalPage.changeBirthDate(person.getBirthDate());
        Sleeper.sleep(1500, driver);

        if (personalPage.isPresent(personalPage.genderRequired)) personalPage.changeGender(person.getGender());
        Sleeper.sleep(500, driver);

        if (personalPage.isPresent(personalPage.maritalRequired))
            personalPage.changeMaritalStatus(person.getMaritalStatus());
        Sleeper.sleep(500, driver);

        if (personalPage.isPresent(personalPage.socialSecurityNumberRequired))
            personalPage.addSocialSecurityNumber(person.getSsn(), runID);
        Sleeper.sleep(500, driver);

        if (personalPage.isPresent(personalPage.phoneRequired))
            personalPage.addPhoneNumber(person.getPrimaryPhone(), runID);
        Sleeper.sleep(500, driver);

        if (personalPage.isPresent(personalPage.addressRequired)) personalPage.addUsAddress(person.getUSAddress());
        Sleeper.sleep(500, driver);

        if (personalPage.isPresent(personalPage.dependentRequired)) personalPage.addHasNotDependents();
        clickDoneBtn();

        Sleeper.sleep(35000, driver); //<- This is to much
    }

    public void updatePersonalInformationWithErrors(ParamObject person, String runID) throws Exception {
        clickAButton(buttonFirstStep);
        Sleeper.sleep(18000, driver);

        PersonalPage personalPage = new EmployeePersonalPage(driver, enviroment);
        personalPage.tabiFrame = updateInformationFrame;

        personalPage.waitPageIsLoad();
        clickDoneBtnAndWait(500);

        if (personalPage.isPresent(personalPage.birthDateRequired)) {
            checkIsPresent("Birth Date");
            personalPage.changeBirthDate(person.getBirthDate());
            checkIsNotPresent("Birth Date");
        }

        if (personalPage.isPresent(personalPage.genderRequired)) {
            checkIsPresent("Gender");
            personalPage.changeGender(person.getGender());
            checkIsNotPresent("Gender");
        }

        if (personalPage.isPresent(personalPage.maritalRequired)) {
            checkIsPresent("Marital");
            personalPage.changeMaritalStatus(person.getMaritalStatus());
            checkIsNotPresent("Marital");
        }

        if (personalPage.isPresent(personalPage.socialSecurityNumberRequired)) {
            checkIsPresent("SSN");
            personalPage.addSocialSecurityNumber(person.getSsn(), runID);
            checkIsNotPresent("SSN");
        }

        if (personalPage.isPresent(personalPage.phoneRequired)) {
            checkIsPresent("Phone");
            personalPage.addPhoneNumber(person.getPrimaryPhone(), runID);
            checkIsNotPresent("Phone");
        }

        if (personalPage.isPresent(personalPage.addressRequired)) {
            checkIsPresent("Address");
            personalPage.addUsAddress(person.getUSAddress());
            checkIsNotPresent("Address");
        }

        if (personalPage.isPresent(personalPage.dependentRequired)) {
            checkIsPresent("Dependents");
            personalPage.addHasNotDependents();
            checkIsNotPresent("Dependents");
        }

        clickDoneBtn();
        swichToFirstFrame(driver);

        waitForElement(driver, buttonFirstStepCompleted, Page.SELENIUM_TIMEOUT_SEC_MEDIUM);
    }

    private void clickDoneBtnAndWait(Integer time) throws Exception {
        Sleeper.sleep(time, driver);
        clickDoneBtn();
        Sleeper.sleep(time, driver);
    }

    public void electBenefits(ParamObject person, String runID) throws Exception {
        //Elect Benefits
        swichToFirstFrame(driver);
        clickAButton(buttonSecondStep);
        Sleeper.sleep(20000, driver);
        swichToIframe(formTaskFrame);
        clickAButton(getElectionButton(person.getMedicalBenefit().getName()));
        Sleeper.sleep(2000, driver);
        clickAButton(dentalPanel);
        clickAButton(getElectionButton(person.getDentalBenefit().getName()));
        Sleeper.sleep(2000, driver);
        clickAButton(visionPanel);
        clickAButton(getElectionButton(person.getVisionBenefit().getName()));
        Sleeper.sleep(2000, driver);
        clickAButton(doneBtn); //click Review and Confirm
        Sleeper.sleep(7000, driver);
        clickAButton(signBtn); //Sign Benefit
        swichToIframe(signatureFrame);
        setTextInField(signPanel, "Employee");
        Sleeper.sleep(500, driver);
        clickAButton(applySignature); // TODO update to new signature
        swichToFormTaskFrame();
        Sleeper.sleep(5000, driver);
        clickAButton(doneBtn); //click Done
        swichToFirstFrame(driver);
        waitForElement(driver, buttonSecondStepCompleted, Page.SELENIUM_TIMEOUT_SEC_MEDIUM);
    }

    public void fillAndSignWaiver(String benefitName) throws Exception {
        swichToFirstFrame(driver);
        clickAButton(buttonThirdStep, 10000);
        selectElementInCombo(benefitsToEnroll, benefitName, "div");
        Sleeper.sleep(12000, driver);
        swichToFormTaskFrame();
        clickAButton(signTaskBtn);
        swichToIframe(rapPDFFrame);
        clickAButton(typeBtn);
        setElementText(typeHereField, "Employee");
        clickAButton(apllySignature);// TODO update to new signature
        Sleeper.sleep(4000, driver);
        swichToFormTaskFrame();
        clickAButton(doneButton);
        swichToFirstFrame(driver);
        Sleeper.sleep(10000, driver);
    }

    public void fillAndSignBenefit(String benefitName) throws Exception {
        swichToFirstFrame(driver);
        clickAButton(buttonThirdStep, 10000);
        selectElementInCombo(benefitsToEnroll, benefitName, "div");
        Sleeper.sleep(12000, driver);
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
        Sleeper.sleep(10000, driver);
    }

    @Override
    public boolean isPageLoad() {
        return false;
    }

    public void swichToFormTaskFrame() throws IOException, InterruptedException, ElementNotFoundException {
        swichToFirstFrame(driver);
        swichToIframe(formTaskFrame);
    }

    public WebElement selectBenefit(String benefitName) {
        for (WebElement benefitsSpan : benefitsSpans) {
            if (benefitsSpan.getText().contains(benefitName)) {
                return getButtonTuSelectBenefit(benefitsSpan);
            }
        }
        return null;
    }

    public WebElement getButtonTuSelectBenefit(WebElement benefitsSpan) {
        return benefitsSpan.findElement(By.xpath("./../../../../td[1]/div[1]"));
    }

    public WebElement getMissingRequiredElement(String nameOfField) throws IOException, InterruptedException, ElementNotFoundException {
        swichToFirstFrame(driver);
        swichToIframe(updateInformationFrame);
        return missingInformationDiv.findElement(By.xpath("//div[@class='flex-item'][contains(text(),'" + nameOfField + "')]"));
    }

    public boolean isPresentMissingrequired(String nameOfField) throws IOException, InterruptedException, ElementNotFoundException {
        return isElementPresent(getMissingRequiredElement(nameOfField));
    }

    public void clickDoneBtn() throws Exception {
        swichToFirstFrame(driver);
        clickAButton(doneButton);
    }

    public void checkIsNotPresent(String nameOfField) throws Exception {
        int seconds = 30;
        int count = 1;
        while (count <= seconds) {
            try {
                if (isPresentMissingrequired(nameOfField)) {
                    Sleeper.sleep(1000, driver);
                    count++;
                }
                ;
            } catch (NoSuchElementException ex) {
                break;
            }
        }
        if (count > 30) {
            throw new Exception("The Missing information: '" + nameOfField + "' Is Present");
        }
    }

    public void checkIsPresent(String nameOfField) throws Exception {
        if (!isPresentMissingrequired(nameOfField)) {
            throw new Exception("The Missing information: '" + nameOfField + "' Is Not Present");
        }
    }

    public void changeEmail(String newEmail) throws Exception {
        swichToFirstFrame(driver);
        clickAButton(persoanlTab);
        swichToIframe(personaliFrame);

        PersonalPage personalPage = new EmployeePersonalPage(driver, enviroment);
        personalPage.tabiFrame = personaliFrame;

        personalPage.changePrimaryEmail(newEmail);
    }

    public void addAlternateiveEmail(String newEmail) throws Exception {
        swichToFirstFrame(driver);
        clickAButton(persoanlTab);
        swichToIframe(personaliFrame);

        PersonalPage personalPage = new EmployeePersonalPage(driver, enviroment);
        personalPage.tabiFrame = personaliFrame;

        personalPage.addAlternativeEmail(newEmail);
    }

    public void makePrimaryEmail(String emailToMakePrimary) throws Exception {
        swichToFirstFrame(driver);
        clickAButton(persoanlTab);
        swichToIframe(personaliFrame);

        PersonalPage personalPage = new EmployeePersonalPage(driver, enviroment);
        personalPage.tabiFrame = personaliFrame;

        personalPage.makeEmailPrimaryEmail(emailToMakePrimary);
    }

    public WebElement getElectionButton(String text) {
        String sel = "//span[@class='productname' and contains(text(),'" + text + "')]/ancestor::tr[1]//td/div[@class='circleempty']";
        WebElement element = driver.findElement(By.xpath(sel));
        return element;
    }

    public WebElement getEnrollmentLink(String text) {
        String sel = "//div[@class='enrollmentformlink' and contains(text(),'" + text + "')]";
        WebElement element = driver.findElement(By.xpath(sel));
        return element;
    }
}
