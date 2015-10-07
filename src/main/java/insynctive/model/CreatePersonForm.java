package insynctive.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CreatePersonTest")
public class CreatePersonForm {
	@Id
	@GeneratedValue
	@Column(name = "create_person_id")
	private int createPersonID;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "lastname")
	private String lastName;
	
	@Column(name = "environment")
	private String environment;
	
	@Column(name = "status_of_test")
	private boolean statusOfTest;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getCreatePersonID() {
		return createPersonID;
	}

	public void setCreatePersonID(int createPersonID) {
		this.createPersonID = createPersonID;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public boolean isStatusOfTest() {
		return statusOfTest;
	}

	public void setStatusOfTest(boolean statusOfTest) {
		this.statusOfTest = statusOfTest;
	}
}
