package insynctive.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Property {

	@Value("${environment}")
	private Integer environmentNumberInFile;
	private String environmentNumberHeroku = System.getenv("ENVIRONMENT_NUMBER");

	public Integer getEnvironmentNumber(){
		return environmentNumberHeroku != null ? Integer.valueOf(environmentNumberHeroku) :  environmentNumberInFile;
	}
	
}
