package insynctive.utils;

import org.openqa.selenium.WebDriver;

public class Sleeper {
	
	static boolean isRemote;
	
	public static void sleep(int time, WebDriver driver){
		try{
			if(isRemote){
				synchronized (driver){ driver.wait(time);}
			}
			else {
				Thread.sleep(time); //This is for LOCAL
			}
		} catch(Exception cE) {
			System.out.println(cE);
		}
	}
	
	public static void setIsRemote(boolean value){
		Sleeper.isRemote = value;
	}
}
