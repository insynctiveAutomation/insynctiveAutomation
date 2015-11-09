package insynctive.model;

import java.io.FileReader;
import java.lang.reflect.Field;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import insynctive.exception.ConfigurationException;

@Entity
@Table(name = "ParamObject")
@JsonIgnoreProperties(ignoreUnknown = true) 
public class ParamObject {
	@Transient
	@JsonIgnore
	public String DEFAULT_FILE = "personFileData.json";
	
	// PROPERTIES PATH
	@Id
	@GeneratedValue
	@Column(name = "param_object_id")
	public Integer paramObjectID;

	@Column(name = "wait_time")
	public Integer waitTime;

	@Column(name = "name")
	public String name;

	@Column(name = "middle_name")
	public String middleName;
	
	@Column(name = "lastname")
	public String lastName;

	@Column(name = "maiden_name")
	public String maidenName;

	@Column(name = "birthdate")
	public String birthDate;

	@Column(name = "gender")
	public Gender gender;

	@Column(name = "email")
	public String email;

	@Column(name = "title_of_employee")
	public String titleOfEmployee;

	@Column(name = "departament")
	public String departamentOfEmployee;

	@Column(name = "primary_phone")
	public String primaryPhone;
	
	@Column(name = "ssn")
	public String ssn;
	
	@Column(name = "marital_status")
	public MaritalStatus maritalStatus;
	
	@Column(name = "loginUserName")
	public String loginUsername;
	
	@Column(name = "loginPassword")
	public String loginPassword;

	@Column(name = "checklistName")
	public String checklistName;
	
	@Column(name = "loadingTime")
	public Integer loadingTime;

	@Column(name = "boolean_param")
	public Boolean booleanParam;
	
	@AttributeOverrides({
		@AttributeOverride(name="name",column=@Column(name="medicalBenefitName")),
		@AttributeOverride(name="company",column=@Column(name="medicalBenefitCompany"))
	})
	@Embedded
	public Benefit medicalBenefit;
	
	@AttributeOverrides({
		@AttributeOverride(name="name",column=@Column(name="dentalBenefitName")),
		@AttributeOverride(name="company",column=@Column(name="dentalBenefitCompany"))
	})
	@Embedded
	public Benefit dentalBenefit;
	
	@AttributeOverrides({
		@AttributeOverride(name="name",column=@Column(name="visionBenefitName")),
		@AttributeOverride(name="company",column=@Column(name="visionBenefitCompany"))
	})
	@Embedded
	public Benefit visionBenefit;

	@OneToOne
	@Cascade({CascadeType.SAVE_UPDATE})
	@JoinColumn(name = "emergency_contact_id")
	public EmergencyContact emergencyContact;

	@OneToOne
	@Cascade({CascadeType.SAVE_UPDATE})
	@JoinColumn(name = "USAddress_id")
	public USAddress usAddress;

	@Column(name = "doc_name")
	public String docName = "";

	@Column(name = "doc_category")
	public String docCategory = "";

	@Column(name = "doc_process")
	public String docProcess = "";

	@Column(name = "doc_plan_name")
	public String docPlanName = "";

	@Column(name = "doc_keyword")
	public String docKeyword = "";

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

	public ParamObject(){
	}
	
	public ParamObject(String name, String lastname, String email){
		this.name = name;
		this.lastName = lastname;
		this.email = email;
	}
	
	public ParamObject(String runID, String filePath) throws ConfigurationException{
		DEFAULT_FILE = filePath;
		addData(runID, DEFAULT_FILE);
	}
	
	public ParamObject(String runID) throws ConfigurationException {
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
	public String getEmailToChange(String runID){
		return email.split("@")[0] +"+"+runID+ "+test@" + email.split("@")[1];
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
	
	public String getEmailWithRunID(String runID){
		return (email.split("@")[0] + "+" + runID + "@" + email.split("@")[1]);
	}
	
	@Override
	public String toString() {
		return this.name + " "+this.lastName;
	}
	
	public Benefit getMedicalBenefit() {
		return medicalBenefit;
	}

	public void setMedicalBenefit(Benefit medicalBenefit) {
		this.medicalBenefit = medicalBenefit;
	}

	public Benefit getDentalBenefit() {
		return dentalBenefit;
	}

	public void setDentalBenefit(Benefit dentalBenefit) {
		this.dentalBenefit = dentalBenefit;
	}

	public Benefit getVisionBenefit() {
		return visionBenefit;
	}

	public void setVisionBenefit(Benefit visionBenefit) {
		this.visionBenefit = visionBenefit;
	}

	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public Integer getParamObjectID() {
		return paramObjectID;
	}

	public void setParamObjectID(Integer paramObjectID) {
		this.paramObjectID = paramObjectID;
	}

	public String getChecklistName() {
		return checklistName;
	}

	public void setChecklistName(String checklistName) {
		this.checklistName = checklistName;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocCategory() {
		return docCategory;
	}

	public void setDocCategory(String docCategory) {
		this.docCategory = docCategory;
	}

	public String getDocProcess() {
		return docCategory;
	}

	public void setDocProcess(String doc_process) {
		this.docProcess = docProcess;
	}

	public String getDocPlanName() {
		return docPlanName;
	}

	public void setDocPlanName(String doc_plan_name) {
		this.docPlanName = docPlanName;
	}

	public String getDocKeyword() {
		return docKeyword;
	}

	public void setDocKeyword(String docKeyword) {
		this.docKeyword = docKeyword;
	}

	public Integer getLoadingTime() {
		return loadingTime;
	}

	public void setLoadingTime(Integer loadingTime) {
		this.loadingTime = loadingTime;
	}
	
	@JsonIgnore
	public Field getFieldByName(String name) throws Exception {
		try {
			String[] splitName = name.split("\\.");
			Class<?> aClass = this.getClass();
			switch(splitName.length){
				case 1:
					return aClass.getField(splitName[0]);
				case 2:
					return aClass.getField(splitName[0]).getType().getField(splitName[1]);
				case 3:
					return aClass.getField(splitName[0]).getType().getField(splitName[1]).getType().getField(splitName[2]);
				default: 
					throw new Exception();
			}
		} catch(Exception ex){
			ex.printStackTrace();
			throw new Exception("Error on getFieldByName("+name+")");
		}
	}
	
	@JsonIgnore
	public Object getValueByName(String name) throws Exception {
		try{
			String[] splitName = name.split("\\.");
			switch(splitName.length){
				case 1:
					return getFieldByName(name).get(this);
				case 2:
					return getFieldByName(name).get(getFieldByName(splitName[0]).get(this));
				case 3:
					return getFieldByName(name).get(getFieldByName(splitName[1]).get(getFieldByName(splitName[0]).get(this)));
				default:
					throw new Exception();
			}
		} catch(Exception ex){
			ex.printStackTrace();
			throw new Exception("Error on getValueByName("+name+")");
		}
	}

	public Object getFieldToSet(String name) throws Exception {
		try{
			String[] splitName = name.split("\\.");
			switch(splitName.length){
				case 1:
					return this;
				case 2:
					if(getFieldByName(splitName[0]).get(this) == null){
						Field field = getFieldByName(splitName[0]);
						field.set(this, field.getType().newInstance()); 
					}
					return getFieldByName(splitName[0]).get(this);
				case 3:
					
				default:
					throw new Exception();
			}
		} catch(Exception ex){
			ex.printStackTrace();
			throw new Exception("Error on getValueByName("+name+")"); 
		}
	}
	
	public Boolean getBooleanParam() {
		return booleanParam;
	}

	public void setBooleanParam(Boolean booleanParam) {
		this.booleanParam = booleanParam;
	}

	public Integer getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(Integer waitTime) {
		this.waitTime = waitTime;
	}
}