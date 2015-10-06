package insynctive.utils;

public enum CheckInApp {
	NO(false),YES(true);
	
	public boolean bool;
	
	private CheckInApp(boolean bool) {
		this.bool = bool;
	}
}
