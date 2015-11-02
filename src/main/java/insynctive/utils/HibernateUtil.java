package insynctive.utils;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.naming.ConfigurationException;

import org.dom4j.tree.AbstractEntity;
import org.hibernate.ConnectionReleaseMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;

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

	private static volatile SessionFactory INSTANCE_SESSION_FACTORY = null;

	public enum Common {
		SUCCESS, ROLLBACK
	}

	private synchronized static void createSessionFactory() {
		try {
			if (INSTANCE_SESSION_FACTORY != null) {
				return;
			}
			ResourceBundle rb = ResourceBundle.getBundle("application");
			Integer environment = Integer.valueOf(rb.getString("environment"));

			Properties prop = new Properties();

			switch (environment) {
			/* LOCAL */
			case 1:
				prop.setProperty("hibernate.connection.driver_class", rb.getString("hibernate.driver.class.name"));
				prop.setProperty("hibernate.connection.url", rb.getString("hibernate.db.uri"));
				prop.setProperty("hibernate.connection.username", rb.getString("hibernate.db.username"));
				prop.setProperty("hibernate.connection.password", rb.getString("hibernate.db.password"));
				break;
			/* HEROKU insynctiveautomation */
			case 2:
				prop.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
				prop.setProperty("hibernate.connection.url",
						"jdbc:mysql://us-cdbr-iron-east-03.cleardb.net:3306/heroku_359ecbd25784b31?autoReconnect=true");
				prop.setProperty("hibernate.connection.username", "b797aea885e227");
				prop.setProperty("hibernate.connection.password", "503f6e18");
				break;
			/* HEROKU alpha-insynctiveautomation */
			case 3:

				prop.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
				prop.setProperty("hibernate.connection.url",
						"jdbc:mysql://us-cdbr-iron-east-03.cleardb.net:3306/heroku_f468d9bec36d8ec?autoReconnect=true");
				prop.setProperty("hibernate.connection.username", "b808518710f57f");
				prop.setProperty("hibernate.connection.password", "3d6e38cf");
				break;
			default:
				throw new ConfigurationException(environment == null ? "No environment added in application.properties"
						: "Wrong environment added in application.properties");
			}

			prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
			prop.setProperty("hibernate.show_sql", "true");
			prop.setProperty("hibernate.hbm2ddl.auto", "update");
			prop.setProperty("hibernate.current_session_context_class", "thread");
			prop.setProperty("hibernate.connection.pool_size", "100");

			prop.setProperty("hibernate.c3p0.minPoolSize", "5");
			prop.setProperty("hibernate.c3p0.maxPoolSize", "100");
			prop.setProperty("hibernate.c3p0.initialPoolSize", "10");
			prop.setProperty("hibernate.c3p0.timeout", "1800");
			prop.setProperty("hibernate.c3p0.max_statements=", "50");

			org.hibernate.cfg.Configuration config = new org.hibernate.cfg.Configuration().addProperties(prop)
					.addAnnotatedClass(Account.class).addAnnotatedClass(InsynctiveProperty.class)
					.addAnnotatedClass(CrossBrowserAccount.class).addAnnotatedClass(EmergencyContact.class)
					.addAnnotatedClass(USAddress.class).addAnnotatedClass(CreatePersonForm.class)
					.addAnnotatedClass(ParamObject.class).addAnnotatedClass(Test.class);

			ServiceRegistryBuilder builder = new ServiceRegistryBuilder().applySettings(config.getProperties());
			INSTANCE_SESSION_FACTORY = config.buildSessionFactory(builder.buildServiceRegistry());
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		if (INSTANCE_SESSION_FACTORY == null) {
			createSessionFactory();
		}
		return INSTANCE_SESSION_FACTORY;
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

	public synchronized static Object get(Class<?> clazz, Integer id) {
		Session session = getCurrentSession();
		final Transaction transaction = session.beginTransaction();
		try {
			Object obj = session.get(clazz, id);
			transaction.commit();
			return obj;
		} catch (RuntimeException ex) {
			System.out.println(ex);
			transaction.rollback();
			throw ex;
		} finally {

		}
	}

	public Common save(Object object) {
		Session session = openCurrentSession();
		Transaction transaction = null;
		Common result = null;
		try {
			transaction = openTransaction(session);
			session.save(object);
			transaction.commit();
			result = Common.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
			result = Common.ROLLBACK;
		} finally {
			session.close();
		}

		return result;
	}

	public Common update(Object object) {
		Session session = openCurrentSession();
		Transaction transaction = null;
		Common result;

		try {
			transaction = openTransaction(session);
			session.update(object);
			transaction.commit();
			result = Common.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();

			if (transaction != null) {
				transaction.rollback();
			}
			result = Common.ROLLBACK;
		} finally {
			session.close();
		}
		return result;
	}

	public Session openCurrentSession() {
		return getSessionFactory().openSession();
	}

	public Transaction openTransaction(Session session) {
		return session.beginTransaction();
	}
}
