package insynctive.exception;

public class TextAreNotEquals  extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TextAreNotEquals(String text1, String text2){
		super("Text: " + text1 + " is not equals than "+text2);
	}
	
}
