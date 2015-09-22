package insynctive.utils;

import insynctive.exception.ConfigurationException;
import insynctive.exception.MethodNoImplementedException;
import insynctive.utils.reader.InsynctivePropertiesReader;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PersonData {

	public enum Gender{
		MALE("Male"), FEMALE("Female"), UNKNOWN("Unknown"); 
		public final String name;
		
		Gender(String name){
			this.name = name;
		}
	}
	
	public enum MaritalStatus{
		UNKNOWN("Unknown"), SINGLE("Single"), MARRIED("Married"), DIVORCED("Divorced"), WIDOWER("Widower"), PARTNER("Partner"); 
		public final String status;
		
		MaritalStatus(String status){
			this.status = status;
		}
	}

	// PROPERTIES PATH
	private String DEFAULT_FILE = "personFileData.json";

	private String name;
	private String middleName;
	private String lastName;
	private String maidenName;
	private String birthDate;
	private Gender gender;
	private MaritalStatus maritalStatus;
	private String email;
	private String emailAfterEdit;
	private String titleOfEmployee;
	private String departamentOfEmployee;
	private String primaryPhone;
	private EmergencyContact emergencyContact;
	private USAddress usAddress;
	private Dependent Dependents;
	private String searchEmail;
	private String ssn;

	JSONParser parser = new JSONParser();

	public PersonData(InsynctivePropertiesReader properties) throws MethodNoImplementedException {
		this.name = properties.getNewEmployeeName();
		this.lastName = properties.getNewEmployeeLastName();
		this.email = properties.getNewEmployeeEmail();
		this.departamentOfEmployee = properties.getNewEmployeeDepartment();
		this.titleOfEmployee = properties.getNewEmployeeTitle();
	}
	
	public PersonData(String name, String lastname, String email){
		this.name = name;
		this.lastName = lastname;
		this.email = email;
	}
	
	public PersonData(String runID, String filePath) throws ConfigurationException{
		DEFAULT_FILE = filePath;
		addData(runID, DEFAULT_FILE);
	}
	
	public PersonData(String runID) throws ConfigurationException {
		addData(runID, DEFAULT_FILE);
	}

	private void addData(String runID, String path) throws ConfigurationException {
		try {
			JSONObject person = (JSONObject) parser.parse(new FileReader(path));
			name = (String)person.get("name");
			middleName = (String)person.get("middleName");
			lastName = (String)person.get("lastName");
			maidenName = (String)person.get("maidenName");
			birthDate = (String)person.get("birthDate");
			gender = Gender.valueOf((String)person.get("gender"));
			maritalStatus = MaritalStatus.valueOf((String)person.get("maritalStatus"));
			email = (String)person.get("email");
			searchEmail = email.split("@")[0];
			setSsn((String)person.get("ssn"));
			
			email = email.split("@")[0] + "+" + runID + "@" + email.split("@")[1];
			emailAfterEdit = (String)person.get("emailAfterEdit");
			titleOfEmployee = (String)person.get("titleOfEmployee");
			departamentOfEmployee = (String)person.get("departamentOfEmployee");
			primaryPhone = (String)person.get("primaryPhone");

			JSONObject emg = (JSONObject) person.get("emergencyContact");
			emergencyContact = new EmergencyContact((String) emg.get("name"),
					(String) emg.get("relationShip"),
					(String) emg.get("email"), (String) emg.get("phone"));
			
			JSONObject jsonUSAddres = (JSONObject)person.get("USAddress");
			usAddress = new USAddress();
			usAddress.setStreet((String)jsonUSAddres.get("street"));
			usAddress.setSecondStreet((String)jsonUSAddres.get("secondStreet"));
			usAddress.setCity((String)jsonUSAddres.get("city"));
			usAddress.setState((String)jsonUSAddres.get("state"));
			usAddress.setZipCode((String)jsonUSAddres.get("zipCode"));
			usAddress.setCounty((String)jsonUSAddres.get("county"));
			usAddress.setSameAsHome((Boolean)jsonUSAddres.get("sameAsHome"));
			
//			JSONArray jsonDependents = (JSONArray) person.get("Dependents");
			
		} catch(Exception ex) {
			throw new ConfigurationException("Fail reading Person Configuration ====> "+ ex.getMessage());
		}
	}
	
	public String getEmailToChange(){
		return email.split("@")[0] + "+test@" + email.split("@")[1];
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getMaidenName() {
		return maidenName;
	}

	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getPrimaryPhone() {
		return primaryPhone;
	}

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public EmergencyContact getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(EmergencyContact emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	public USAddress getUSAddress() {
		return usAddress;
	}

	public void setUSAddress(USAddress uSAddress) {
		usAddress = uSAddress;
	}

	public Dependent getDependents() {
		return Dependents;
	}

	public void setDependents(Dependent dependents) {
		Dependents = dependents;
	}

	public String getDepartamentOfEmployee() {
		return departamentOfEmployee;
	}

	public void setDepartamentOfEmployee(String departamentOfEmployee) {
		this.departamentOfEmployee = departamentOfEmployee;
	}

	public String getTitleOfEmployee() {
		return titleOfEmployee;
	}

	public void setTitleOfEmployee(String titleOfEmployee) {
		this.titleOfEmployee = titleOfEmployee;
	}

	public String getEmailAfterEdit() {
		return emailAfterEdit;
	}

	public void setEmailAfterEdit(String emailAfterEdit) {
		this.emailAfterEdit = emailAfterEdit;
	}

	public String getSearchEmail() {
		return searchEmail;
	}

	public void setSearchEmail(String searchEmail) {
		this.searchEmail = searchEmail;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	
	@Override
	public String toString() {
		return this.name + " "+this.lastName;
	}

}