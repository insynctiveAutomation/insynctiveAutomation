package insynctive.utils;

public enum Wait {
	WAIT(true),NOWAIT(false);
	
	private final boolean wait;
	
	private Wait(boolean wait){
		this.wait = wait;
	}

	public boolean isWait() {
		return wait;
	}
	
}
