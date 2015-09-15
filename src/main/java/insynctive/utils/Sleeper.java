package insynctive.utils;

import insynctive.utils.reader.InsynctivePropertiesReader;

import org.openqa.selenium.WebDriver;

public class Sleeper {

	
	public static void sleep(int time, WebDriver driver){
		try{
			if(InsynctivePropertiesReader.IsRemote()){
				synchronized (driver){ driver.wait(time);}
			} else {
				Thread.sleep(time); //This is for LOCAL
			}
		} catch(Exception cE) {
			//DONT WAIT
			System.out.println(cE);
		}
	}
}
