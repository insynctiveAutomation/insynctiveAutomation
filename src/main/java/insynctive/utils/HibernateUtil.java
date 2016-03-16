package insynctive.utils;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import insynctive.dao.AccountDao;
import insynctive.dao.CreatePersonFormDao;
import insynctive.dao.CrossBrowserAccountDao;
import insynctive.dao.InsynctivePropertyDao;
import insynctive.dao.test.TestDao;
import insynctive.dao.test.TestPlanDao;
import insynctive.dao.test.TestPlanRunDao;
import insynctive.dao.test.TestRunDao;
import insynctive.dao.test.TestSuiteDao;
import insynctive.dao.test.TestSuiteRunDao;

public class HibernateUtil {

	private static InsynctivePropertyDao propertyDao;
	private static AccountDao accDao;
	private static CreatePersonFormDao createPersonFormDao;
	private static CrossBrowserAccountDao crossDao;

	private static TestDao testDao;
	private static TestSuiteDao testSuiteDao;
	private static TestPlanDao testPlanDao;

	private static TestRunDao testRunDao;
	private static TestSuiteRunDao testSuiteRunDao;
	private static TestPlanRunDao testPlanRunDao;
	
	private static Semaphore mutex = new Semaphore(1);
	
	public static synchronized void init(TestDao testDao, TestSuiteDao testSuiteDao, TestPlanDao testPlanDao,
		TestRunDao testRunDao, TestSuiteRunDao testSuiteRunDao, TestPlanRunDao testPlanRunDao,
		InsynctivePropertyDao propertyDao, AccountDao accDao, CreatePersonFormDao createPersonFormDao, CrossBrowserAccountDao crossDao) {

		HibernateUtil.propertyDao = propertyDao;
		HibernateUtil.accDao = accDao;
		HibernateUtil.crossDao = crossDao;
		HibernateUtil.createPersonFormDao = createPersonFormDao;

		HibernateUtil.testDao = testDao;
		HibernateUtil.testSuiteDao = testSuiteDao;
		HibernateUtil.testPlanDao = testPlanDao;

		HibernateUtil.testRunDao = testRunDao;
		HibernateUtil.testSuiteRunDao = testSuiteRunDao;
		HibernateUtil.testPlanRunDao = testPlanRunDao;
	}


	private static void init() throws ClientProtocolException, IOException {
		HttpClients.createDefault().execute(new HttpGet("/init"));
	}
	
	public static InsynctivePropertyDao getPropertyDao() throws ClientProtocolException, IOException, InterruptedException {
		mutex.acquire();
			if(propertyDao == null){init();}
		mutex.release();
		
		return propertyDao;
	}


	public static AccountDao getAccDao() throws ClientProtocolException, IOException, InterruptedException { 
		mutex.acquire();
			if(accDao == null){init();}
		mutex.release();
		
		return accDao;
	}


	public static CreatePersonFormDao getCreatePersonFormDao() throws ClientProtocolException, IOException, InterruptedException { 
		mutex.acquire();
			if(createPersonFormDao == null){init();}
		mutex.release();
		
		return createPersonFormDao;
	}


	public static CrossBrowserAccountDao getCrossDao() throws ClientProtocolException, IOException, InterruptedException { 
		mutex.acquire();
			if(crossDao == null){init();}
		mutex.release();	
		
		return crossDao;
	}


	public static TestDao getTestDao() throws ClientProtocolException, IOException, InterruptedException { 
		mutex.acquire();
			if(testDao == null){init();}
		mutex.release();
			
		return testDao;
	}


	public static TestSuiteDao getTestSuiteDao() throws ClientProtocolException, IOException, InterruptedException { 
		mutex.acquire();
			if(testSuiteDao == null){init();}
		mutex.release();
			
		return testSuiteDao;
	}


	public static TestPlanDao getTestPlanDao() throws ClientProtocolException, IOException, InterruptedException { 
		mutex.acquire();
			if(testPlanDao == null){init();}
		mutex.release();
			
		return testPlanDao;
	}


	public static TestRunDao getTestRunDao() throws ClientProtocolException, IOException, InterruptedException { 
		mutex.acquire();
			if(testRunDao == null){init();}
		mutex.release();
		
		return testRunDao;
	}


	public static TestSuiteRunDao getTestSuiteRunDao() throws ClientProtocolException, IOException, InterruptedException { 
		mutex.acquire();
			if(testSuiteRunDao == null){init();}
		mutex.release();
			
		return testSuiteRunDao;
	}


	public static TestPlanRunDao getTestPlanRunDao() throws ClientProtocolException, IOException, InterruptedException { 
		mutex.acquire();
			if(testPlanRunDao == null){init();}
		mutex.release();
			
		return testPlanRunDao;
	}
	
}