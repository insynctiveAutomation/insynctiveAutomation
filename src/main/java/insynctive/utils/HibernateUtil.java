package insynctive.utils;

import org.springframework.context.annotation.ImportResource;

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

	public static InsynctivePropertyDao propertyDao;
	public static AccountDao accDao;
	public static CreatePersonFormDao createPersonFormDao;
	public static CrossBrowserAccountDao crossDao;

	public static TestDao testDao;
	public static TestSuiteDao testSuiteDao;
	public static TestPlanDao testPlanDao;

	public static TestRunDao testRunDao;
	public static TestSuiteRunDao testSuiteRunDao;
	public static TestPlanRunDao testPlanRunDao;
	

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
}
