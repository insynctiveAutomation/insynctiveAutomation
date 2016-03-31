package insynctive.pages.pdf;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import insynctive.utils.Sleeper;

public class I9SectionOne extends I9PDF {
	
	public I9SectionOne(WebDriver driver) {
		super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

	public void completeForm() throws Exception {
		swichToPDFWebiFrame();
//		setTextInPDFField(500, lastName, "Last Name");
//		setTextInPDFField(500, firstName, "First Name");
//		setTextInPDFField(500, middleInitial, "Mid");
		setTextInPDFField(500, maidenName, "Maiden Name");
//		setTextInPDFField(500, streetNumberAndName, "Street Number and Name");
//		setTextInPDFField(500, aptNumber, "112233");
//		setTextInPDFField(500, cityOrTown, "City or Town");
//		setTextInPDFField(500, state, "CA");
//		setTextInPDFField(500, zipCode, "12345");
//		setTextInPDFField(500, dateOfBirth, "11/22/3333");
//		setTextInPDFField(500, ssnFirsts, "111");
//		setTextInPDFField(500, ssnSeconds, "22");
//		setTextInPDFField(500, ssnThirdLasts, "3333");
		setTextInPDFField(500, email, "email@email.com");
		setTextInPDFField(500, telephoneNumber, "123456789");
		swichToTaskiFrame();
		clickAButton(signBtn, 7000);
		clickAButton(doneBtn, 7000);
		
		Sleeper.sleep(15000);
		waitUntilnotVisibility(waitingBar);
	}
	
}
