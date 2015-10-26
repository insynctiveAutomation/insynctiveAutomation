package insynctive.utils;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.naming.ConfigurationException;

import org.dom4j.tree.AbstractEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import insynctive.model.Account;
import insynctive.model.CreatePersonForm;
import insynctive.model.CrossBrowserAccount;
import insynctive.model.EmergencyContact;
import insynctive.model.InsynctiveProperty;
import insynctive.model.ParamObject;
import insynctive.model.Test;
import insynctive.model.USAddress;

@ImportResource("classpath:application.properties")
public class HibernateUtil {
	
	@Value("${local}")
	private Boolean local;
	
	private static final SessionFactory sessionFactory;
	
	static {
		try {
			ResourceBundle rb = ResourceBundle.getBundle("application");
			Integer environment = Integer.valueOf(rb.getString("environment"));
			
			Properties prop= new Properties();
			
			switch(environment){
			/*LOCAL*/
			case 1 :
				prop.setProperty("hibernate.connection.driver_class", rb.getString("hibernate.driver.class.name"));
				prop.setProperty("hibernate.connection.url", rb.getString("hibernate.db.uri"));
				prop.setProperty("hibernate.connection.username", rb.getString("hibernate.db.username"));
				prop.setProperty("hibernate.connection.password", rb.getString("hibernate.db.password"));
				break;
			/*HEROKU insynctiveautomation*/
			case 2 :
				prop.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
				prop.setProperty("hibernate.connection.url", "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net:3306/heroku_359ecbd25784b31");
				prop.setProperty("hibernate.connection.username", "b797aea885e227");
				prop.setProperty("hibernate.connection.password", "503f6e18");
				break;
			/*HEROKU alpha-insynctiveautomation*/
			case 3 :
				prop.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
				prop.setProperty("hibernate.connection.url", "jdbc:mysql://us-cdbr-iron-east-03.cleardb.net:3306/heroku_f468d9bec36d8ec");
				prop.setProperty("hibernate.connection.username", "b808518710f57f");
				prop.setProperty("hibernate.connection.password", "3d6e38cf");
				break;
			default :
				throw new ConfigurationException(environment == null ? "No environment added in application.properties" : "Wrong environment added in application.properties");
		}

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
			   .addAnnotatedClass(ParamObject.class)
			   .addAnnotatedClass(Test.class);
			
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
