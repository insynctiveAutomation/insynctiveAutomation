package insynctive.utils.process;

import insynctive.pages.PageInterface;
import insynctive.pages.insynctive.exception.ElementNotFoundException;
import insynctive.utils.Sleeper;
import insynctive.utils.Task;
import insynctive.utils.UploadRobot;
import insynctive.utils.WhenStart;
import insynctive.utils.data.Employee;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PDFForm extends Process implements PageInterface {

	Task task;
	
	/* Create Form */
	@FindBy(id = "contentframe")
	WebElement contentiFrame;
	@FindBy(id = "nextButton")
	WebElement nextButton;
	
	//Process Setting
	@FindBy(id = "btnSaveFillSign")
	WebElement saveFillAndSign;
	@FindBy(id = "filePicker")
	WebElement uploadFile;
	@FindBy(id = "drop")
	WebElement addFileButton;
	@FindBy(id = "btnSave")
	WebElement saveButton;
	@FindBy(className = "configureTask")
	WebElement configTaskButton;
	@FindBy(id = "chkFill")
	WebElement fillCheckBox;
	@FindBy(id = "chkSign")
	WebElement signCheckBox;
	
	//Task Config
	@FindBy(id = "pctIFrame")
	WebElement configFrame;
	@FindBy(id = "txtTaskInstructions")
	WebElement instructionOfTask;
	@FindBy(className = ".fillAndSign > span:nth-child(1)")
	WebElement fillAndSignChange;
	
	@FindBy(css = "#searchAppsResult > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > a:nth-child(1)")
	public WebElement processLink;
	
	public PDFForm(WhenStart whenStart, Employee employee, Task task, WebDriver driver) {
		super(driver);
		this.whenStart = whenStart;
		this.employee = employee; 
		this.task = task;
	}

	@Override
	public void waitPageIsLoad() throws IOException, InterruptedException, ElementNotFoundException {
		waitUntilIsLoaded(nextButton);
		swichToIframe(contentiFrame);
		waitUntilIsLoaded(uploadFile);
		swichToFirstFrame(driver);
	}
	
	@Override
	public boolean isPageLoad() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void completeStepsToCreate() throws Exception {
		waitPageIsLoad();
		swichToIframe(contentiFrame);
		
		/*UPLOAD A FILE*/
		clickAButton(uploadFile);
		clickAButton(addFileButton);
		Sleeper.sleep(2000, driver);
		UploadRobot.uploadPDF(driver);
		Sleeper.sleep(10000, driver);
		clickAButton(saveButton);
		
//		/*Fill and Sign CheckBox*/
//		clickAButton(fillAndSignChange);
//		if(task.isFill()) clickAButton(fillCheckBox);
//		if(!task.isSign()) clickAButton(signCheckBox);
//		clickAButton(saveFillAndSign);
//		
		/*Config Task Button*/
		clickAButton(configTaskButton);
		swichToFirstFrame(driver);
		swichToIframe(configFrame);
		setTextInField(instructionOfTask, task.getBasicTaskInstruction());
		clickAButton(saveButton);
		Sleeper.sleep(2000, driver);
		
		/*Go to Next Step*/
		swichToFirstFrame(driver);
		clickAButton(nextButton);
		Sleeper.sleep(1000, driver);
		
		/*Select When Start The Process*/
		clickAButton(nextButton);
		Sleeper.sleep(4500, driver);
	}
	
	@Override
	public WebElement getElement() throws IOException, InterruptedException, ElementNotFoundException{
		waitUntilIsLoaded(processLink);
		return processLink;
	}
}
