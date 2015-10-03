package insynctive.utils;

import org.openqa.selenium.WebDriver;

public class Sleeper {
	
	public static void sleep(int time, WebDriver driver){
		try{
			if(isRemote()){
				synchronized (driver){ driver.wait(time);}
			} else {
				Thread.sleep(time); //This is for LOCAL
			}
		} catch(Exception cE) {
			System.out.println(cE);
		}
	}

	private static boolean isRemote() {
		return true;
	}
}
