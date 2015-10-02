package insynctive.utils.process;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import insynctive.exception.ElementNotFoundException;
import insynctive.pages.PageInterface;
import insynctive.utils.Sleeper;
import insynctive.utils.WhenStart;
import insynctive.utils.data.Employee;

public class AssignTask extends Process implements PageInterface {

	@FindBy(css = "#searchAppsResult > table > tbody > tr:nth-child(1) > td:nth-child(2) > table > tbody > tr:nth-child(1) > td > a")
	public WebElement processLink;
	public Employee processedEmployee;
	public String instruction;
	public String additionalInstruction;

	@FindBy(id = "assignedKey")
	public WebElement processedPersonLink;
	@FindBy(id = "task")
	public WebElement configureTask;
	@FindBy(id = "PersonPickerSearchAssigned")
	public WebElement personPickerSearchAssigned;
	
	//Configure Task
	@FindBy(id = "froalaEditor")
	WebElement AdditionalInstructioniFrame;
	@FindBy(id = "lName")
	WebElement taskNameElement;
	@FindBy(id = "txtTaskInstructions")
	WebElement taskInstuctions;
	@FindBy(className = "froala-element")
	WebElement taskAdditionalInstructions;
	@FindBy(id = "btnSave")
	public WebElement saveTaskConfig;
	
	public AssignTask(WhenStart whenStart, Employee processedEmployee, WebDriver driver) {
		super(driver);
		this.whenStart = whenStart;
		this.processedEmployee = processedEmployee;
		this.taskName = "Assign Task";
	}
	
	@Override
	public void completeStepsToCreate() throws Exception {
		waitPageIsLoad();
		//Step 1
		swichToIframe(contentiframe);
		clickAButton(processedPersonLink);
		clickAButton(findElementByText("td", processedEmployee.personData.toString()));
		swichToFirstFrame(driver);
		
		//CONFIG TASK
		clickAButton(configureTask);
		swichToFirstFrame(driver);
		waitUntilIsLoaded(taskInstuctions);
		setTextInField(taskInstuctions, instruction);
		swichToIframe(AdditionalInstructioniFrame);
		waitUntilIsLoaded(taskAdditionalInstructions);
		setTextInField(taskAdditionalInstructions,additionalInstruction);
		swichToFirstFrame(driver);
		clickAButton(saveTaskConfig);
		waitPageIsLoad();
		clickAButton(nextButton);
		
		//Step 2
		Sleeper.sleep(4000, driver);
		clickAButton(nextButton);
		Sleeper.sleep(4000, driver);
	}
	
	@Override
	public boolean isPageLoad() {
		return false;
	}

	@Override
	public void waitPageIsLoad() throws Exception {
		waitUntilIsLoaded(nextButton);
		swichToIframe(contentiframe);
		waitUntilIsLoaded(processedPersonLink);
		waitUntilIsLoaded(configureTask);
		swichToFirstFrame(driver);
	}
	
	@Override
	public WebElement getElement() throws IOException, InterruptedException, ElementNotFoundException{
		waitUntilIsLoaded(processLink);
		return processLink;
	}
}
