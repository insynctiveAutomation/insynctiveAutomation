package insynctive.tests;

import static org.junit.Assert.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import insynctive.annotation.ParametersFront;
import insynctive.pages.insynctive.employee.EmployeeDocumentsPage;
import insynctive.utils.Debugger;
import insynctive.utils.ParamObjectField;
import insynctive.utils.data.TestEnvironment;

/**
 * Created by cristi on 11/6/2015.
 */
public class DocumentTest extends TestMachine{

    @BeforeClass
    @Parameters({"accountID", "runID", "bowser", "testID", "testName"})
    public void tearUp(String accountID, String runID, String bowser, String testSuiteID, String testName) throws Exception {
        super.testSuiteID = Integer.parseInt(testSuiteID);
        super.tearUp(Integer.valueOf(accountID), Integer.valueOf(runID));
        testEnvironment = TestEnvironment.valueOf(bowser);
        this.sessionName = testName;
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
            attrs={ParamObjectField.DOC_NAME, ParamObjectField.DOC_CATEGORY, ParamObjectField.DOC_KEYWORD, ParamObjectField.LOADING_TIME},
            labels={"Document Name", "Document Category", "Document Keyword", "Documents count"})
    public void getDocuments(@Optional("TestID") Integer testID) throws Exception {
        changeParamObject(testID);
        try{
            EmployeeDocumentsPage employeeDocuments = new EmployeeDocumentsPage(driver, properties.getEnvironment());
            Boolean result = true;
            
            for(int index = 1; index <= paramObject.loadingTime; index++){
            	result = result && employeeDocuments.isDocumentInGrid(paramObject.docName+" "+index, paramObject.docCategory);
            	employeeDocuments.viewDocument(paramObject.docName+" "+index);
            	result = result && employeeDocuments.isOpenDocument();
            	employeeDocuments.closeBigOverlay();
            }

            Debugger.log("getDocuments => "+result, isSaucelabs);
            setResult(result, "Documents displayed");
            assertTrue(result);
            
            
            
        } catch(Exception ex){
            failTest("No Documents displayed",  ex, isSaucelabs);
            assertTrue(false);
        }
    }


}
