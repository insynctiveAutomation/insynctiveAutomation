package insynctive.usperson.support;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
	
	private ObjectMapper mapper = new ObjectMapper();
	
	public Person() { }
	
	public Person(String firstName, String lastName, String title, String primaryEmail, String department) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.primaryEmail = primaryEmail;
		this.department = department;
	}

	@JsonProperty("PersonId")
	private String personID;
	
	@JsonProperty("FirstName")
	private String firstName;
	
	@JsonProperty("LastName")
	private String lastName;
	
	@JsonProperty("Title")
	private String title;
	
	@JsonProperty("PrimaryEmail")
	private String primaryEmail;
	
	@JsonProperty("Department")
	private String department;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrimaryEmail() {
		return primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String asJsonString() throws JsonProcessingException{
		return mapper.writeValueAsString(this);
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}
}
