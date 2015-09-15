package insynctive.utils;

public class EmergencyContact {
	String name;
	String relationship;
	String email;
	String phone;
	
	public EmergencyContact(String name, String relationship, String email,
			String phone) {
		super();
		this.name = name;
		this.relationship = relationship;
		this.email = email;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

}
