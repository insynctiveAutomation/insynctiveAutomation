package insynctive.utils.data;

public enum App {
	
	US_EMPLOYEE(1, "US Employment Events"),
	I9(3, "I-9"),
	W4(7, "W-4");
	
	private final int id;
	private final String name;
	
	App(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
}
