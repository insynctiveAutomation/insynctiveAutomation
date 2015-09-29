package insynctive.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
 
@Configuration
@EnableWebMvc
@ComponentScan(basePackages="insynctive.controller")
public class SpringMVCConfiguration extends WebMvcConfigurerAdapter {

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolverJSP = new InternalResourceViewResolver();
        viewResolverJSP.setOrder(1);
        viewResolverJSP.setViewClass(JstlView.class);
        viewResolverJSP.setPrefix("views/jsp/");
        viewResolverJSP.setSuffix(".jsp");
        
        return viewResolverJSP;
    }
}