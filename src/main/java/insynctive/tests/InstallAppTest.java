package insynctive.tests;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import insynctive.model.ParamObject;
import insynctive.utils.data.TestEnvironment;

@Deprecated
public class InstallAppTest extends TestMachine {

	ParamObject person;

	@BeforeClass(alwaysRun = true)
	public void tearUp() throws Exception {
		super.tearUp();
		this.sessionName = "Install Apps";
	}
	
	@DataProvider(name = "hardCodedBrowsers", parallel = true)
	public static Object[][] sauceBrowserDataProvider(Method testMethod) {
		return new Object[][] { new Object[] { TestEnvironment.CHROME }
		};
	}
	
	@Test(dataProvider = "hardCodedBrowsers")
	public void marketInstallTest(TestEnvironment testEnvironment)
			throws Exception {
		
//		startTest(testEnvironment);
//		
//		MarketPage marketPage = new MarketPage(driver);
//		
//		for(App app : properties.getApps()){ 
//			marketPage.installApp(app, 
//					properties.getEnvironment(), 
//					properties.getLoginUsername(), 
//					properties.getLoginPassword());
//			
//			assertTrue(marketPage.isAppInstallSuccessfully());
//		}
		
	}
}
