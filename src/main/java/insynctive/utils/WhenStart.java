package insynctive.utils;

public enum WhenStart {
	
	ASAP("ASAP");
	
	private final String whenStart;
	
	private WhenStart(String whenStart) {
		this.whenStart = whenStart;
	}

	public String getWhenStart() {
		return whenStart;
	}
	
	
}
