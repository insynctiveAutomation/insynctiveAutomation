package insynctive.controller;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Controller;

import insynctive.dao.AccountDao;
import insynctive.dao.CreatePersonFormDao;
import insynctive.dao.CrossBrowserAccountDao;
import insynctive.dao.InsynctivePropertyDao;
import insynctive.dao.TestDao;
import insynctive.dao.TestSuiteDao;
import insynctive.utils.HibernateUtil;

@Controller
public class InitController {

	@Inject
	public InitController(TestDao testDao, InsynctivePropertyDao propertyDao, ServletContext servletContext, AccountDao accDao, CrossBrowserAccountDao crossDao, CreatePersonFormDao createPersonFormDao, TestSuiteDao testSuiteDao) {
		HibernateUtil.init(testDao, propertyDao, servletContext, accDao, crossDao, createPersonFormDao, testSuiteDao);
	}
}
