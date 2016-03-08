package insynctive.utils;

import java.io.IOException;

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
	
	public static InsynctivePropertyDao getPropertyDao() throws ClientProtocolException, IOException {
		if(propertyDao == null){init();}
		return propertyDao;
	}


	public static AccountDao getAccDao() throws ClientProtocolException, IOException { 
		if(accDao == null){init();}
		return accDao;
	}


	public static CreatePersonFormDao getCreatePersonFormDao() throws ClientProtocolException, IOException { 
		if(createPersonFormDao == null){init();}
		return createPersonFormDao;
	}


	public static CrossBrowserAccountDao getCrossDao() throws ClientProtocolException, IOException { 
		if(crossDao == null){init();}
		return crossDao;
	}


	public static TestDao getTestDao() throws ClientProtocolException, IOException { 
		if(testDao == null){init();}
		return testDao;
	}


	public static TestSuiteDao getTestSuiteDao() throws ClientProtocolException, IOException { 
		if(testSuiteDao == null){init();}
		return testSuiteDao;
	}


	public static TestPlanDao getTestPlanDao() throws ClientProtocolException, IOException { 
		if(testPlanDao == null){init();}
		return testPlanDao;
	}


	public static TestRunDao getTestRunDao() throws ClientProtocolException, IOException { 
		if(testRunDao == null){init();}
		return testRunDao;
	}


	public static TestSuiteRunDao getTestSuiteRunDao() throws ClientProtocolException, IOException { 
		if(testSuiteRunDao == null){init();}
		return testSuiteRunDao;
	}


	public static TestPlanRunDao getTestPlanRunDao() throws ClientProtocolException, IOException { 
		if(testPlanRunDao == null){init();}
		return testPlanRunDao;
	}
	
}