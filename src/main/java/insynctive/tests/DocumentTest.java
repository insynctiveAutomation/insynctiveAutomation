package insynctive.tests;

import insynctive.annotation.ParametersFront;
import insynctive.pages.insynctive.employee.EmployeeDashboardPage;
import insynctive.pages.insynctive.employee.EmployeeDocumentsPage;
import insynctive.utils.Debugger;
import insynctive.utils.ParamObjectField;
import insynctive.utils.data.TestEnvironment;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Created by cristi on 11/6/2015.
 */
public class DocumentTest extends TestMachine{

    @BeforeClass
    @Parameters({"accountID", "runID", "bowser", "testID"})
    public void tearUp(String accountID, String runID, String bowser, String testSuiteID) throws Exception {
        super.testSuiteID = Integer.parseInt(testSuiteID);
        super.tearUp(Integer.valueOf(accountID), Integer.valueOf(runID));
        testEnvironment = TestEnvironment.valueOf(bowser);
        this.sessionName = "Document Test";
    }

    @Test
    @Parameters({"TestID"})
    @ParametersFront(attrs={ParamObjectField.LOGIN_USERNAME, ParamObjectField.LOGIN_PASSWORD}, labels={"Login Username", "Login Password"})
    public void loginTest(@Optional("TestID") Integer testID)
            throws Exception {
        changeParamObject(testID);
        startTest(testEnvironment);

        try{
            login("/Insynctive.Hub/Protected/Documents.aspx");
            setResult(true, "Login Test");
            Debugger.log("loginTest => " + true, isSaucelabs);
            assertTrue(true);
        } catch(Exception ex){
            failTest("Login",  ex, isSaucelabs);
            assertTrue(false);
        }
    }

    @Test(dependsOnMethods="loginTest")
    @Parameters({"TestID"})
    @ParametersFront(
            attrs={ParamObjectField.DOC_NAME, ParamObjectField.DOC_CATEGORY, ParamObjectField.DOC_KEYWORD},
            labels={"Document Name", "Document Category", "Document Keyword"})
    public void getDocuments(@Optional("TestID") Integer testID) throws Exception {
        changeParamObject(testID);
        try{
            EmployeeDocumentsPage employeeDocuments = new EmployeeDocumentsPage(driver, properties.getEnvironment());
            Boolean result = employeeDocuments.getDocument(paramObject);
            Debugger.log("getDocuments => "+result, isSaucelabs);
            setResult(result, "Documents displayed");
            assertTrue(result);

        } catch(Exception ex){
            failTest("No Documents displayed",  ex, isSaucelabs);
            assertTrue(false);
        }
    }


}
