package insynctive.pages.insynctive;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.exception.MethodNoImplementedException;
import insynctive.pages.Page;
import insynctive.pages.PageInterface;
import insynctive.utils.Checklist;
import insynctive.utils.Sleeper;
import insynctive.utils.process.Process;

public class TemplatePage extends Page implements PageInterface{

	String enviroment;
	
	/* Templates Page */
	@FindBy(id = "lblTitle")
	WebElement labelTemplates;
	@FindBy(css = "#btnAddTemplate_CD > span")
	WebElement btnAddTemplate;
	@FindBy(id = "gvTemplates_DXMainTable")
	WebElement tableTemplates;
	
	//#gvTemplates_DXDataRow0 > 
		//#gvTemplates_tccell0_0 > 
			//a <== First table row
	
	/* Add Template */
	@FindBy(id = "gvTemplates_DXPEForm_efnew_txtTemplateName_I")
	WebElement txtBoxAddTemplateName;
	@FindBy(css = "#gvTemplates_DXPEForm_efnew_btnUpdate_CD > span")
	WebElement btnOkAddTemplate;
	
	/* Edit Template */
	@FindBy(id = "lnkReturn_CD")
	WebElement btnReturnToTemplate;
	@FindBy(id = "buttonAddProcess")
	WebElement btnAddProcess;
	@FindBy(id = "ASPxButton1_CD")
	WebElement btnAddV3Process;
	@FindBy(id = "btnActivity_CD")
	WebElement btnAddV4Process;
	@FindBy(id = "callbackPanel")
	WebElement tableOfProcess;
	
	@FindBy(id = "popupCustom_CIF-1")
	WebElement iframeTemplate;
	
	public TemplatePage(WebDriver driver, String enviroment) {
		super(driver);
		this.enviroment = enviroment;
		this.PAGE_URL = "http://" + enviroment + ".insynctiveapps.com/Insynctive.Hub/Protected/Templates.aspx?";
		this.PAGE_TITLE = "Templates";
		PageFactory.initElements(driver, this);
	}

	/* Actions **/
	public void addTemplate(Checklist checkList) throws Exception {
//		waitUntilIsLoaded(btnAddTemplate);
//		btnAddTemplate.click();
//		waitAddTemplateLoad();
//		setText_TemplateName(checkList.getName()); //<-- TODO Get ID of Test Run
//		btnOkAddTemplate.click();
//		
//		waitEditTemplateLoad();
//		for(Process proc : checkList.getProcess()){
//			Debugger.subLog(proc, false);
//			proc.createTask();
//		}
		throw new MethodNoImplementedException("Add Template is not implemented");
	}
	
	public void editTemplate(Checklist checklist) throws Throwable{
		for(Process process : checklist.getProcess()){
			process.setDriver(driver);
			process.initElements(driver);
			waitUntilIsLoaded(btnAddProcess);
			clickAButton(btnAddProcess);
			
			waitAddProcessPage();
			Sleeper.sleep(4000, driver);
			clickAButton(process.getElement());
			process.completeStepsToCreate();
		}
		Sleeper.sleep(4000, driver);
	}
	
	private void waitAddProcessPage() {
		// TODO Auto-generated method stub
		
	}

	/* Waits **/
	public void waitPageIsLoad() throws Exception {
		waitUntilIsLoaded(labelTemplates);
		waitUntilIsLoaded(btnAddTemplate);
		waitUntilIsLoaded(tableTemplates);
	}
	
	public void waitAddTemplateLoad() throws Exception {
		waitUntilIsLoaded(txtBoxAddTemplateName);
		waitUntilIsLoaded(btnOkAddTemplate);
	}

	public void waitEditTemplateLoad() throws Exception {
		waitUntilIsLoaded(btnReturnToTemplate);
		waitUntilIsLoaded(btnAddV4Process);
	}
	
	/* Checks **/
	public boolean isPageLoad() {
		return btnAddTemplate.isDisplayed() && 
				tableTemplates.isDisplayed();
	}
	
	public boolean isAddTemplateLoad() {
		return btnOkAddTemplate.isDisplayed() && 
				txtBoxAddTemplateName.isDisplayed();
	}
	
	public boolean isEdiTemplateLoad() {
		return btnReturnToTemplate.isDisplayed() && 
				btnAddV3Process.isDisplayed() &&
				btnAddV4Process.isDisplayed();
	}
}
