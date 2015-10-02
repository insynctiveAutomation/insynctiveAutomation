package insynctive.utils.process;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import insynctive.exception.MethodNoImplementedException;
import insynctive.pages.Page;
import insynctive.utils.WhenStart;
import insynctive.utils.data.Employee;

public abstract class Process extends Page{

	public String version;
	public WhenStart whenStart;
	@FindBy(css = "TO OVERRIDE")
	public 	WebElement processLink;
	public String taskName = "DEFAULT TASK NAME";
	public Employee employee;
	
	@FindBy(id = "contentframe")
	WebElement contentiframe;
	@FindBy(id = "nextButton")
	WebElement nextButton;
	
	public Process(WebDriver driver){
		super(driver);
	}
	
	public WebElement getElement() throws Exception{
		waitUntilIsLoaded(processLink);
		return processLink;
	}
	
	public void setDriver(WebDriver driver){
		this.driver = driver;
	}
	
	public void initElements(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	public void setVersion(String version){
		this.version = version;
	}

	public void completeStepsToCreate() throws Exception{
		throw new MethodNoImplementedException("completeStepsToCreate no implemented");
	}
	
	public void completeSteps() throws Exception{
		throw new MethodNoImplementedException("completeSteps no implemented");
	}
}
