package insynctive.pages.pdf;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import insynctive.utils.Sleeper;

public class I9SectionTwo extends I9PDF {

	public I9SectionTwo(WebDriver driver) {
        super(driver);
		this.driver = driver;
        PageFactory.initElements(driver, this);
    }

	public void fillInformation() throws Exception {
		goToNextPage();
		swichToPDFWebiFrame();
		setTextInPDFField(200, sectionTwotitleOfEmployer, "Agent");
		setTextInPDFField(200, sectionTwoFirstName, "Eugenio");
		setTextInPDFField(200, sectionTwoLastName, "PDF Agent");
		setTextInPDFField(200, initialInformationFromSectionOne, "First Name Last Name");
		swichToTaskiFrame();
		clickAButton(signBtn, 7000);
		clickAButton(doneBtn, 7000);
	}
	
}
