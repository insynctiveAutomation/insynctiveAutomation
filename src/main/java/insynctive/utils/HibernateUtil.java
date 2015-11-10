package insynctive.utils;

import javax.servlet.ServletContext;

import org.springframework.context.annotation.ImportResource;

import insynctive.dao.AccountDao;
import insynctive.dao.CreatePersonFormDao;
import insynctive.dao.CrossBrowserAccountDao;
import insynctive.dao.InsynctivePropertyDao;
import insynctive.dao.TestDao;
import insynctive.dao.TestSuiteDao;

@ImportResource("classpath:application.properties")
public class HibernateUtil {

	public static InsynctivePropertyDao propertyDao;
	public static AccountDao accDao;
	public static CreatePersonFormDao createPersonFormDao;
	public static CrossBrowserAccountDao crossDao;
	public static TestDao testDao;
	public static TestSuiteDao testSuiteDao;
	
	
	public static void init(TestDao testDao, InsynctivePropertyDao propertyDao, ServletContext servletContext, AccountDao accDao, CrossBrowserAccountDao crossDao, CreatePersonFormDao createPersonFormDao, TestSuiteDao testSuiteDao){
		HibernateUtil.propertyDao = propertyDao;
		HibernateUtil.accDao = accDao;
		HibernateUtil.crossDao = crossDao;
		HibernateUtil.createPersonFormDao = createPersonFormDao;
		HibernateUtil.testDao = testDao;
		HibernateUtil.testSuiteDao = testSuiteDao;
	}
}
