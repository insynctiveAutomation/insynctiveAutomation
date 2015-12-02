package insynctive.utils;

import org.springframework.context.annotation.ImportResource;

import insynctive.dao.AccountDao;
import insynctive.dao.CreatePersonFormDao;
import insynctive.dao.CrossBrowserAccountDao;
import insynctive.dao.InsynctivePropertyDao;
import insynctive.dao.test.TestDao;
import insynctive.dao.test.TestPlanDao;
import insynctive.dao.test.TestRunDao;
import insynctive.dao.test.TestSuiteDao;
import insynctive.dao.test.TestSuiteRunDao;

@ImportResource("classpath:application.properties")
public class HibernateUtil {

	public static InsynctivePropertyDao propertyDao;
	public static AccountDao accDao;
	public static CreatePersonFormDao createPersonFormDao;
	public static CrossBrowserAccountDao crossDao;

	public static TestPlanDao testPlanDao;
	public static TestSuiteDao testSuiteDao;
	public static TestSuiteRunDao testSuiteRunDao;
	public static TestDao testDao;
	public static TestRunDao testRunDao;

	public static synchronized void init(InsynctivePropertyDao propertyDao, AccountDao accDao,
			CreatePersonFormDao createPersonFormDao, CrossBrowserAccountDao crossDao, TestPlanDao testPlanDao,
			TestSuiteDao testSuiteDao, TestSuiteRunDao testSuiteRunDao, TestDao testDao, TestRunDao testRunDao) {

		HibernateUtil.propertyDao = propertyDao;
		HibernateUtil.accDao = accDao;
		HibernateUtil.crossDao = crossDao;
		HibernateUtil.createPersonFormDao = createPersonFormDao;

		HibernateUtil.testPlanDao = testPlanDao;
		HibernateUtil.testSuiteDao = testSuiteDao;
		HibernateUtil.testSuiteRunDao = testSuiteRunDao;
		HibernateUtil.testDao = testDao;
		HibernateUtil.testRunDao = testRunDao;
	}
}
