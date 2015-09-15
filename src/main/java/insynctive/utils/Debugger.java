package insynctive.utils;

public class Debugger {

	public static void log(Object message, boolean isSauceLabs){
		if(isSauceLabs){
			//LOG IN SAUCELABS
			frameOf(message,'#');
			System.out.println("#"+message+"#");
			frameOf(message,'#');
		} else {
			frameOf(message,'#');
			System.out.println("#"+message+"#");
			frameOf(message,'#');
		}
	}

	public static void log(String message) {
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
			frameOf(message, '¯');
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
