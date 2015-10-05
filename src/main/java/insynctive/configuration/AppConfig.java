package insynctive.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import insynctive.utils.SessionScope;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "insynctive")
@EnableTransactionManagement
public class AppConfig {
	
	@Autowired
    Environment env;

	@Bean
	public SessionScope sessionScope(){
		return new SessionScope();
	}
	
	@Bean
	public PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		/*HEROKU*/
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://us-cdbr-iron-east-03.cleardb.net:3306/heroku_359ecbd25784b31");
		dataSource.setUsername("b797aea885e227");
		dataSource.setPassword("503f6e18");
		
		/*LOCAL*/
//		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//		dataSource.setUrl("jdbc:mysql://localhost:3306/insynctive");
//		dataSource.setUsername("root");
//		dataSource.setPassword("");

		return dataSource;
	}

	@Bean(name = "sessionFactory")
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setPackagesToScan("insynctive.model");
		sessionFactoryBean.setHibernateProperties(hibProperties());
		return sessionFactoryBean;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

	private Properties hibProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.hbm2ddl.auto", "create");
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.put("hibernate.show_sql", true);
		return properties;
	}
	
}
