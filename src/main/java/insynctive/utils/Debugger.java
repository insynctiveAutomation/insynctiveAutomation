package insynctive.utils;

import airbrake.AirbrakeNotice;
import airbrake.AirbrakeNoticeBuilder;
import airbrake.Backtrace;
import insynctive.exception.FailTest;

public class Debugger {
	
	public static String log(String message, String tag){
		Debugger.log(message);		
		
		if(!message.contains("true")){
			AirbrakeNotice notice = new AirbrakeNoticeBuilder("c565ae6163c7924243ac326dd3487a5b", new Backtrace(new FailTest(message)), new FailTest(message), tag).newNotice();
			AirbrakeUtil notifier = new AirbrakeUtil();
			return notifier.notify(notice);
		}
		return null;
	}

	public static String log(Exception ex, String tag){
		AirbrakeNotice notice = new AirbrakeNoticeBuilder("c565ae6163c7924243ac326dd3487a5b", new Backtrace(ex), ex, tag).newNotice();
		AirbrakeUtil notifier = new AirbrakeUtil();
		return notifier.notify(notice);
	}

	public static String log(String message, boolean isSauceLabs){
		return Debugger.log(message, isSauceLabs ? "Remote" : "Local");
	}

	public static String log(Exception ex, boolean isSauceLabs){
		return Debugger.log(ex, isSauceLabs ? "Remote" : "Local");
	}
	
	
	private static void log(String message) {
		frameOf(message,'#');
		System.out.println("#"+message+"#");
		frameOf(message,'#');
	}

	public static void subLog(Object message, boolean isSauceLabs){
		if(isSauceLabs){
			//LOG IN SAUCELABS
		} else {
			frameOf(message, '_');
			System.out.println("|"+message+"|");
			frameOf(message, '�');
		}
	}
	
	private static void frameOf(Object message, char character) {
		System.out.print(character);
		for(int i = 0 ; i < message.toString().length(); i++){
			System.out.print(character);
		}
		System.out.println(character);
	}
	
}
