package insynctive.tests;

import insynctive.annotation.ParametersFront;
import insynctive.model.ParamObject;
import insynctive.pages.insynctive.agent.hr.CheckListsPage;
import insynctive.pages.insynctive.employee.EmployeeDocumentsPage;
import insynctive.utils.Debugger;
import insynctive.utils.ParamObjectField;
import insynctive.utils.data.TestEnvironment;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by cristi on 12/03/2015.
 */
public class SMBChecklistTest extends TestMachine {

    @BeforeClass
    @Parameters({"accountID", "runID", "bowser", "testID", "testName", "environment"})
    public void tearUp(String accountID, String runID, String bowser, String testSuiteID, String testName, String environment) throws Exception {
        super.testSuiteID = Integer.parseInt(testSuiteID);
        super.tearUp(Integer.valueOf(accountID), Integer.valueOf(runID));
        testEnvironment = TestEnvironment.valueOf(bowser);
        this.sessionName = testName;
        properties.setEnvironment(environment);
    }

    @Test
    @Parameters({"TestID"})
    @ParametersFront(attrs = {ParamObjectField.LOGIN_USERNAME, ParamObjectField.LOGIN_PASSWORD}, labels = {"Login Username", "Login Password"})
    public void loginTest(@Optional("TestID") Integer testID)
            throws Exception {
        changeParamObject(testID);
        startTest(testEnvironment);

        try {
            login("/Insynctive.Hub/Protected/HRChecklists.aspx");
            setResult(true, "Login Test");
            Debugger.log("loginTest => " + true, isSaucelabs);
            assertTrue(true);
        } catch (Exception ex) {
            failTest("Login", ex, isSaucelabs);
            assertTrue(false);
        }
    }

    @Test(dependsOnMethods = "loginTest")
    @Parameters({"TestID"})
    @ParametersFront(
            attrs = {ParamObjectField.NAME, ParamObjectField.LAST_NAME, ParamObjectField.CHECKLIST_NAME},
            labels = {"Employee Name", "Employee Last Name", "Checklist Name"})
    public void archiveRunningChecklist(@Optional("TestID") Integer testID) throws Exception {
        changeParamObject(testID);
        try {
            CheckListsPage checklistPage = new CheckListsPage(driver, properties.getEnvironment());
            Boolean result = true;
            checklistPage.archiveChecklist(paramObject.name, paramObject.lastName, paramObject.checklistName);

//            checklistPage.fluentWait();
//            for (int index = 1; index <= paramObject.loadingTime; index++) {
//                result = result && employeeDocuments.isDocumentInGrid(paramObject.docName + " " + index, paramObject.docCategory);
//                employeeDocuments.viewDocument(paramObject.docName + " " + index);
//                result = result && employeeDocuments.isOpenDocument();
//                employeeDocuments.closeBigOverlay();
//            }

            Debugger.log("archiveRunningChecklist => " + result, isSaucelabs);
            setResult(result, "Checklist Arhived");
            assertTrue(result);


        } catch (Exception ex) {
            failTest("No Checklist displayed", ex, isSaucelabs);
            assertTrue(false);
        }
    }

    //TODO Create Individual Enrollment test
    //    public void CreateIndividualEnrollment(@Optional("TestID") Integer testID) throws Exception {
    //
    //    }

    //TODO Running Individual Enrollment test
    //    public void StartIndividualEnrollment(@Optional("TestID") Integer testID) throws Exception {
    //
    //    }
}



