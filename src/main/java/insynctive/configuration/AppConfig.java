package insynctive.configuration;

import java.util.Properties;

import javax.naming.ConfigurationException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import insynctive.utils.SessionScope;

@Configuration
@ComponentScan(basePackages = "insynctive")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class AppConfig {
	
	@Value("${environment}")
	private Integer environment;
	
	@Value("${hibernate.auto}")
	private String hibernateAuto;
	
	@Value("${hibernate.driver.class.name}")
	private String driverClassName;
	
	@Value("${hibernate.db.uri}")
	private String dbUri;
	
	@Value("${hibernate.db.username}")
	private String dbUsername;
	
	@Value("${hibernate.db.password}")
	private String dbPassword;
	
	
	@Value("${hibernate.dialect}")
	private String hibernateDialect;

	@Value("${hibernate.showSQL}")
	private Boolean showSQL;

	@Bean
	public SessionScope sessionScope(){
		return new SessionScope();
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public JdbcTemplate jdbcTemplate() throws ConfigurationException {
		return new JdbcTemplate(dataSource());
	}

	@Bean
	public DataSource dataSource() throws ConfigurationException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		switch(environment){
			/*LOCAL*/
			case 1 :
				dataSource.setDriverClassName(driverClassName);
				dataSource.setUrl(dbUri);
				dataSource.setUsername(dbUsername);
				dataSource.setPassword(dbPassword);
				break;
			/*HEROKU insynctiveautomation*/
			case 2 :
				dataSource.setDriverClassName("com.mysql.jdbc.Driver");
				dataSource.setUrl("jdbc:mysql://us-cdbr-iron-east-03.cleardb.net:3306/heroku_359ecbd25784b31");
				dataSource.setUsername("b797aea885e227");
				dataSource.setPassword("503f6e18");
				break;
			/*HEROKU alpha-insynctiveautomation*/
			case 3 :
				dataSource.setDriverClassName("com.mysql.jdbc.Driver");
				dataSource.setUrl("jdbc:mysql://us-cdbr-iron-east-03.cleardb.net:3306/heroku_f468d9bec36d8ec");
				dataSource.setUsername("b808518710f57f");
				dataSource.setPassword("3d6e38cf");
				break;
			default :
				throw new ConfigurationException(environment == null ? "No environment added in application.properties" : "Wrong environment added in application.properties");
		}
		
		return dataSource;
	}

	@Bean(name = "sessionFactory")
	public LocalSessionFactoryBean sessionFactory() throws ConfigurationException {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource());
		sessionFactoryBean.setPackagesToScan("insynctive.model");
		sessionFactoryBean.setHibernateProperties(hibProperties());
		return sessionFactoryBean;
	}

	@Bean
	public HibernateTransactionManager transactionManager() throws ConfigurationException {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}
    
	@Bean
    public ThreadPoolTaskExecutor taskExecutor() {
	    ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
	    pool.setCorePoolSize(5);
	    pool.setMaxPoolSize(10);
	    pool.setWaitForTasksToCompleteOnShutdown(true);
	    return pool;
    }
	
	private Properties hibProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.hbm2ddl.auto", hibernateAuto);
		properties.put("hibernate.dialect", hibernateDialect);
		properties.put("hibernate.show_sql", showSQL);
		return properties;
	}
}
