package insynctive.model;

import java.io.FileReader;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.annotation.JsonIgnore;

import insynctive.exception.ConfigurationException;

@Entity
@Table(name = "Person")
public class PersonData {
	@Transient
	@JsonIgnore
	private String DEFAULT_FILE = "personFileData.json";
	
	// PROPERTIES PATH
	@Id
	@GeneratedValue
	@Column(name = "person_data_id")
	private int peronID;

	@Column(name = "name")
	private String name;

	@Column(name = "middle_name")
	private String middleName;
	
	@Column(name = "lastname")
	private String lastName;

	@Column(name = "maiden_name")
	private String maidenName;

	@Column(name = "birthdate")
	private String birthDate;

	@Column(name = "gender")
	private Gender gender;

	@Column(name = "email")
	private String email;

	@Column(name = "title_of_employee")
	private String titleOfEmployee;

	@Column(name = "departament")
	private String departamentOfEmployee;

	@Column(name = "primary_phone")
	private String primaryPhone;
	
	@Column(name = "ssn")
	private String ssn;
	
	@Column(name = "marital_status")
	private MaritalStatus maritalStatus;
	
	@Column(name = "medical_benefit")
	private String medicalBenefit;
	
	@Column(name = "dental_benefit")
	private String dentalBenefit;
	
	@Column(name = "vision_benefit")
	private String visionBenefit;

	@NotNull
	@OneToOne
	@Cascade({CascadeType.SAVE_UPDATE})
	@JoinColumn(name = "emergency_contact_id")
	private EmergencyContact emergencyContact;

	@NotNull
	@OneToOne
	@Cascade({CascadeType.SAVE_UPDATE})
	@JoinColumn(name = "USAddress_id")
	private USAddress usAddress;
	
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

	public PersonData(){
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

	@Deprecated
	private void addData(String runID, String path) throws ConfigurationException {
		try {
			JSONParser parser = new JSONParser();
			JSONObject person = (JSONObject) parser.parse(new FileReader(path));
			name = (String)person.get("name");
			middleName = (String)person.get("middleName");
			lastName = (String)person.get("lastName");
			maidenName = (String)person.get("maidenName");
			birthDate = (String)person.get("birthDate");
			gender = Gender.valueOf((String)person.get("gender"));
			maritalStatus = MaritalStatus.valueOf((String)person.get("maritalStatus"));
			email = (String)person.get("email");
			setSsn((String)person.get("ssn"));
			
			email = email.split("@")[0] + "+" + runID + "@" + email.split("@")[1];
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
	
	@JsonIgnore
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

	@JsonIgnore
	public String getSearchEmail() {
		return email.split("@")[0];
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	
	public String getEmailWithRunID(Account acc){
		return (email.split("@")[0] + "+" + acc.getRunID() + "@" + email.split("@")[1]);
	}
	
	@Override
	public String toString() {
		return this.name + " "+this.lastName;
	}

	public int getPeronID() {
		return peronID;
	}

	public void setPeronID(int peronID) {
		this.peronID = peronID;
	}

	public String getMedicalBenefit() {
		return medicalBenefit;
	}

	public void setMedicalBenefit(String medicalBenefit) {
		this.medicalBenefit = medicalBenefit;
	}

	public String getDentalBenefit() {
		return dentalBenefit;
	}

	public void setDentalBenefit(String dentalBenefit) {
		this.dentalBenefit = dentalBenefit;
	}

	public String getVisionBenefit() {
		return visionBenefit;
	}

	public void setVisionBenefit(String visionBenefit) {
		this.visionBenefit = visionBenefit;
	}

}