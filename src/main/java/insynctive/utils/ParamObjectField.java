package insynctive.utils;

public enum ParamObjectField {
	
	PERSON_ID("param_object_id"), BOOLEAN_PARAM("booleanParam"), 
	NAME("name"), MIDDLE_NAME("middleName"),
	LAST_NAME("lastName"), MAIDEN_NAME("maidenName"), BIRTH_DATE("birthDate"),
	GENDER("gender"), EMAIL("email"), TITLE_OF_EMPLOYEE("titleOfEmployee"),
	DEPARTMENT_OF_EMPLYEE("departamentOfEmployee"), PRIMARY_PHONE("primaryPhone"),
	SSN("ssn"), MARITAL_STATUS("maritalStatus"), LOGIN_USERNAME("loginUsername"),
	LOGIN_PASSWORD("loginPassword"), EMERGENCY_CONTACT("emergencyContact"), US_ADDRESS("usAddress"),
	CHECKLIST_NAME("checklistName"), LOADING_TIME("loadingTime"),
	MEDICAL_BENEFIT_NAME("medicalBenefit.name"),MEDICAL_BENEFIT_COMPANY("medicalBenefit.company"),
	DENTAL_BENEFIT_NAME("dentalBenefit.name"), DENTAL_BENEFIT_COMPANY("dentalBenefit.company"),
	VISION_BENEFIT_NAME("visionBenefit.name"), VISION_BENEFIT_COMPANY("visionBenefit.company");
	
	private String value;
	
	ParamObjectField(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public ParamObjectField value(String valueParam){
		for(ParamObjectField enumObj :values()){
			if(enumObj.getValue().equals(valueParam)){
				return enumObj;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}
