package insynctive.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "insynctive")
@EnableTransactionManagement
public class AppConfig {

	//TODO CONFIG DATA BASE
	
}
