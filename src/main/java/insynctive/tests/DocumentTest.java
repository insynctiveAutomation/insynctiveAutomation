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
	@Parameters({"environment", "browser", "isRemote", "isNotification", "testSuiteID", "testName"})
	public void tearUp(String environment, String browser, String isRemote, String isNotification, String testSuiteID, String testName) throws Exception {
		tearUp(browser, environment, isRemote, isNotification, testSuiteID);
        this.sessionName = testName;
    }

    @Test
    @Parameters({"TestID"})
    @ParametersFront(attrs={ParamObjectField.LOGIN_USERNAME, ParamObjectField.LOGIN_PASSWORD}, labels={"Login Username", "Login Password"})
    public void loginTest(@Optional("TestID") Integer testID)
            throws Exception {
        changeParamObject(testID);
        startTest();

        try{
            login("/Insynctive.Hub/Protected/Documents.aspx");
            setResult(true, "Login Test");
            Debugger.log("loginTest => " + true, isRemote);
            assertTrue(true);
        } catch(Exception ex){
            failTest("Login",  ex, isRemote);
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
            EmployeeDocumentsPage employeeDocuments = new EmployeeDocumentsPage(driver, environment);
            Boolean result = true;
            
            for(int index = 1; index <= paramObject.loadingTime; index++){
            	result = result && employeeDocuments.isDocumentInGrid(paramObject.docName+" "+index, paramObject.docCategory);
            	employeeDocuments.viewDocument(paramObject.docName+" "+index);
            	result = result && employeeDocuments.isDocumentOpened();
            	employeeDocuments.closeBigOverlay();
            	if(!result){ break; }
            	
            }

            Debugger.log("getDocuments => "+result, isRemote);
            setResult(result, "Documents displayed");
            assertTrue(result);
            
        } catch(Exception ex){
            failTest("No Documents displayed",  ex, isRemote);
            assertTrue(false);
        }
    }


}
