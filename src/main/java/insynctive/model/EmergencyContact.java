package insynctive.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "EmergencyContact")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmergencyContact {
	
	@Id
	@GeneratedValue
	@Column(name = "emergency_contact_id")
	public Integer emergencyID;
	
	@Column(name = "name")
	public String name;
	
	@Column(name = "relationship")
	public String relationship;
	
	@Column(name = "email")
	public String email;
	
	@Column(name = "phone")
	public String phone;
	
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

	public Integer getEmergencyID() {
		return emergencyID;
	}

	public void setEmergencyID(Integer emergencyID) {
		this.emergencyID = emergencyID;
	}
	
	

}
