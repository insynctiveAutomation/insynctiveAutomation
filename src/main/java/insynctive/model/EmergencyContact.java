package insynctive.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EmergencyContact")
public class EmergencyContact {
	@Id
	@GeneratedValue
	@Column(name = "emergency_contact_id")
	private int id;
	
	@Column(name = "name")
	String name;
	
	@Column(name = "relationship")
	String relationship;
	
	@Column(name = "email")
	String email;
	
	@Column(name = "phone")
	String phone;
	
	public EmergencyContact() {

	}
	
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
