package insynctive.utils;

import java.util.Properties;

import org.dom4j.tree.AbstractEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.context.annotation.PropertySource;

import insynctive.model.Account;
import insynctive.model.CreatePersonForm;
import insynctive.model.CrossBrowserAccount;
import insynctive.model.EmergencyContact;
import insynctive.model.InsynctiveProperty;
import insynctive.model.PersonData;
import insynctive.model.USAddress;

@PropertySource("classpath:application.properties")
public class HibernateUtil {
	
	private static Boolean local;
	private static final SessionFactory sessionFactory;
	
	static {
		try {
			Properties prop= new Properties();
			
//			if(local){
			
//				prop.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
//				prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/insynctive");
//				prop.setProperty("hibernate.connection.username", "root");
//				prop.setProperty("hibernate.connection.password", "");				

//			} else {
//
				prop.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
				prop.setProperty("hibernate.connection.url", "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net:3306/heroku_359ecbd25784b31");
				prop.setProperty("hibernate.connection.username", "b797aea885e227");
				prop.setProperty("hibernate.connection.password", "503f6e18");
//			
//			}

			prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
			prop.setProperty("hibernate.show_sql", "true");
			prop.setProperty("hibernate.hbm2ddl.auto", "update");
			prop.setProperty("hibernate.current_session_context_class", "thread");
			
			org.hibernate.cfg.Configuration config = new org.hibernate.cfg.Configuration()
			   .addProperties(prop)
			   .addAnnotatedClass(Account.class)
			   .addAnnotatedClass(InsynctiveProperty.class)
			   .addAnnotatedClass(CrossBrowserAccount.class)
			   .addAnnotatedClass(EmergencyContact.class)
			   .addAnnotatedClass(USAddress.class)
			   .addAnnotatedClass(CreatePersonForm.class)
			   .addAnnotatedClass(PersonData.class);
			
			ServiceRegistry serviceRegistry =  new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
			sessionFactory = config.buildSessionFactory(serviceRegistry);;
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	public static Session openSession() {
		return getSessionFactory().openSession();
	}

	public static void closeCurrentSession() {
		getSessionFactory().getCurrentSession().close();
	}
	
	
	public static void save(AbstractEntity entity) {
		Session session = openSession();
		Transaction tx = session.beginTransaction();
		session.evict(entity);
		session.saveOrUpdate(entity);
		tx.commit();
		session.close();
	}
	
	public static void saverOrUpdate(AbstractEntity entity){
		save(entity);
	}
}
