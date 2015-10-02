package insynctive.utils;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import insynctive.model.Account;
import insynctive.model.InsynctiveProperty;

public class HibernateUtil {

	private static final SessionFactory sessionFactory;

	static {
		try {
			Properties prop= new Properties();
			prop.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
			prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/insynctive");
			prop.setProperty("hibernate.connection.username", "root");
			prop.setProperty("hibernate.connection.password", "");
			prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
			prop.setProperty("hibernate.show_sql", "true");
			prop.setProperty("hibernate.hbm2ddl.auto", "update");
			prop.setProperty("hibernate.current_session_context_class", "thread");
			Configuration config = new Configuration()
			   .addProperties(prop)
			   .addAnnotatedClass(Account.class)
			   .addAnnotatedClass(InsynctiveProperty.class);
			
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
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}
}
