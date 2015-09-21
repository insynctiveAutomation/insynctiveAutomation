package insynctive.pages.insynctive.hr;

import insynctive.exception.ElementNotFoundException;
import insynctive.pages.Page;
import insynctive.pages.PageInterface;
import insynctive.pages.insynctive.TemplatePage;
import insynctive.utils.Checklist;
import insynctive.utils.Sleeper;
import insynctive.utils.data.Employee;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckListsPage extends Page implements PageInterface {

	String enviroment;
	
	/* Checklists */
	@FindBy(id = "body_body_checklistsTab_gwProcesses_DXMainTable")
	WebElement personsTable;
	@FindBy(id = "btnStartChecklist")
	WebElement btnStartChecklist;
	@FindBy(css = "#statusesList > li:nth-child(3)")
	WebElement btnTemplate;
	@FindBy(id = "body_body_checklistsTab_TC")
	WebElement tabsBody;
	@FindBy(id = "body_body_popupChecklist_lkpChoosePerson_I")
	WebElement personName;
	@FindBy(id = "body_body_popupChecklist_cboTemplateTransition_I")
	WebElement checklistName;
	@FindBy(id = "btnStartChecklist")
	WebElement startChecklist;
	@FindBy(id = "body_body_popupChecklist_cboTemplateTransition_DDD_L_LBI4T0")
	WebElement firstChecklist;
	@FindBy(id= "body_body_popupChecklist_cbSkip_S_D")
	WebElement skipChecklists;
	@FindBy(xpath = ".//*[@id='body_body_popupChecklist_lkpChoosePerson_DDD_gv_tcrow0']/div")
	WebElement firstName;
	@FindBy(css = "#ddChecklist_chosen > div > ul > li > span")
	WebElement noResultCombo;
	@FindBy(css = "#contentHeight > iframe")
	WebElement startCheckListiFrame;
	@FindBy(className = "searchTextInput")
	WebElement searchTemplate;
	@FindBy(css = ".runningchecklist_runningforheader > input:nth-child(2)")
	WebElement searchRunningFor;
	@FindBy(css = ".runningchecklist_checklistheader > input:nth-child(2)")
	WebElement searchChecklist;
	@FindBy(css = "div.row:nth-child(7) > div:nth-child(3) > img:nth-child(1)")
	WebElement deleteTemplate;

	
	/* ADD Template iFrame */
	@FindBy(css = "#popupCustom_CIF-1")
	WebElement iframeAddTemplate;
	@FindBy(id = "btnAddChecklistTemplate")
	WebElement addTemplateBtn;
	@FindBy(id = "newTemplateName")
	WebElement checkListNameField;
	@FindBy(id = "saveNewTemplate")
	WebElement saveNewTemplate;
	@FindBy(css = "div.row:nth-child(7) > div:nth-child(1) > p:nth-child(1) > span:nth-child(1)")
	WebElement firstTemplateSpan;
	@FindBy(css = "div.row:nth-child(7) > div:nth-child(1) > p:nth-child(1)")
	WebElement firstTemplate;

	@FindBy(className = "close")
	WebElement closeiFrame;

	@FindBy(className = "chosen-default")
	WebElement choosePerson;

	@FindBy(id = "btnStartChecklist")
	WebElement startNewChecklist;

	@FindBy(css = "#ddChecklist_chosen > a:nth-child(1)")
	WebElement chooseChecklist;

	@FindBy(className = "ladda-button")
	WebElement deleteFinalTemplate;
	
	public CheckListsPage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://" + enviroment + ".insynctiveapps.com/Insynctive.Hub/Protected/HRChecklists.aspx?SkipGuide=True";
		this.PAGE_TITLE = "Checklists";
		PageFactory.initElements(driver, this);
	}

	/* Actions **/
	public void createTemplate(Checklist checkList) throws Throwable{
		String newCheckListName = "Test Process "+checkList.getName();

		goToChecklistTemplate();
		Sleeper.sleep(1500, driver);
		waitUntilIsLoaded(addTemplateBtn);
		clickAButton(addTemplateBtn);
		
		Sleeper.sleep(2000, driver);
		setTextInField(checkListNameField, newCheckListName);
		Sleeper.sleep(1500, driver);
		clickAButton(saveNewTemplate);
		Sleeper.sleep(5000, driver);
		waitUntilNameIsVisible(firstTemplateSpan, newCheckListName);
		clickAButton(firstTemplate);
		
		TemplatePage templatePage = new TemplatePage(driver, enviroment);
		templatePage.editTemplate(checkList);
	}

	private void goToChecklistTemplate() throws Exception {
		swichToFirstFrame(driver);
		waitUntilIsLoaded(btnTemplate);
		clickAButton(btnTemplate);
	}
	
	public void startChecklist(String checkList, String person) throws Exception {
		clickAButton(btnStartChecklist);
		
		clickAButton(personName);
		personName.sendKeys(person);
//		personName.sendKeys(Keys.TAB);
			
		clickAButton(checklistName);
		checklistName.sendKeys(checkList);
		clickAButton(firstChecklist);

		waitUntilIsLoaded(skipChecklists);
		clickAButton(startChecklist);
	}
	
	public void assignChecklist(Checklist checklist, Employee employee, boolean restartChecklist) throws Throwable {
		swichToFirstFrame(driver);
		clickAButton(btnStartChecklist);
		swichToIframe(startCheckListiFrame);
		Sleeper.sleep(1500, driver);
		boolean checklistExist = selectElementInComboLi(chooseChecklist, "Test Process "+checklist.getName());
		Sleeper.sleep(1000, driver);
		if(restartChecklist){
			if(!checklistExist) createChecklist(checklist,true); else deleteThenCreate(checklist);
		}
		clickAButton(btnStartChecklist);
		swichToIframe(startCheckListiFrame);
		selectElementInComboLi(chooseChecklist, "Test Process "+checklist.getName());
		Sleeper.sleep(1000, driver);
		setTextInCombo(choosePerson, employee.personData.toString());
		Sleeper.sleep(1000, driver); 
		clickAButton(startNewChecklist);
		
		Sleeper.sleep(5000, driver);
	}
	
	/* Waits **/
	public void waitPageIsLoad() throws Exception {
		swichToFirstFrame(driver);
		waitUntilIsLoaded(startChecklist);
		waitUntilIsLoaded(searchRunningFor);
		waitUntilIsLoaded(searchChecklist);
	}

	/* Checks **/
	public boolean isPageLoad() {
		return btnStartChecklist.isDisplayed() && 
				btnTemplate.isDisplayed();
	}
	
	/* Private Methods*/
	private void createChecklist(Checklist checklist, boolean comeFromAssign) throws Throwable {
		if(comeFromAssign) {
			swichToFirstFrame(driver);
			clickAButton(closeiFrame);
		}
		createTemplate(checklist);
		Sleeper.sleep(2500, driver);
		loadPage();
	}
	
	private void deleteThenCreate(Checklist checklist) throws Throwable {
		String newCheckListName = "Test Process "+checklist.getName();
		loadPage();//THIS IS BECAUSE JS IS FULLY TODO
		goToChecklistTemplate();
		Sleeper.sleep(3500, driver);
		setTextInField(searchTemplate, newCheckListName);
		Sleeper.sleep(2000, driver);
		deleteChecklist(newCheckListName);
		Sleeper.sleep(4500, driver);
		createChecklist(checklist, false);
	}

	private void deleteChecklist(String newCheckListName) throws Exception {
		int times = 1;
		boolean elementIsNotClickeable = true;
		WebElement deleteTask = null;
		while(elementIsNotClickeable && (times <= 20)){
			try{
				Sleeper.sleep(1000, driver);
				deleteTask = findElementByText("span", newCheckListName)
						.findElement(By.xpath(".."))
						.findElement(By.xpath(".."))
						.findElement(By.xpath(".."))
						.findElement(By.cssSelector("img.clickable"));
				clickAButton(deleteTask);
				clickAButton(deleteFinalTemplate);
				elementIsNotClickeable = false;
			} catch (Exception ex){times++;/*THE ELEMENT IS NOT FOUND*/}
		}
		if(deleteTask == null){throw new ElementNotFoundException("The Checklists "+newCheckListName+" is not found after"+ times +" segs wait", null);}
	}
}
