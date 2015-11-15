package insynctive.utils;

public enum ParamObjectField {

	PERSON_ID("param_object_id"), BOOLEAN_PARAM_ONE("booleanParamOne"), BOOLEAN_PARAM_TWO("booleanParamTwo"),
	NAME("name"), MIDDLE_NAME("middleName"),
	LAST_NAME("lastName"), MAIDEN_NAME("maidenName"), BIRTH_DATE("birthDate"),
	GENDER("gender"), EMAIL("email"), TITLE_OF_EMPLOYEE("titleOfEmployee"),
	DEPARTMENT_OF_EMPLYEE("departamentOfEmployee"), PRIMARY_PHONE("primaryPhone"),
	SSN("ssn"), MARITAL_STATUS("maritalStatus"), LOGIN_USERNAME("loginUsername"),
	LOGIN_PASSWORD("loginPassword"), CHECKLIST_NAME("checklistName"), LOADING_TIME("loadingTime"),
	
	EMERGENCY_CONTACT_NAME("emergencyContact.name"), EMERGENCY_CONTACT_RELATIONSHIP("emergencyContact.relationship"), 
	EMERGENCY_CONTACT_EMAIL("emergencyContact.email"), EMERGENCY_CONTACT_PHONE("emergencyContact.phone"), 
	
	US_ADDRESS_STREET("usAddress.street"), US_ADDRESS_APT("usAddress.apt"),US_ADDRESS_CITY("usAddress.city"),
	US_ADDRESS_SECOND_STREET("usAddress.secondStreet"), US_ADDRESS_STATE("usAddress.state"), US_ADDRESS_ZIP_CODE("usAddress.zipCode"), 
	US_ADDRESS_COUNTY("usAddress.county"), US_ADDRESS_SHORT_DESCRIPTION("usAddress.shortDescription"), US_ADDRESS_SAME_AS_HOME("usAddress.sameAsHome"), 
	
	MEDICAL_BENEFIT_NAME("medicalBenefit.name"),MEDICAL_BENEFIT_COMPANY("medicalBenefit.company"),
	DENTAL_BENEFIT_NAME("dentalBenefit.name"), DENTAL_BENEFIT_COMPANY("dentalBenefit.company"),
	VISION_BENEFIT_NAME("visionBenefit.name"), VISION_BENEFIT_COMPANY("visionBenefit.company"),

	DOC_NAME("docName"), DOC_CATEGORY("docCategory"), DOC_PROCESS("docProcess"), DOC_PLAN_NAME("docPlanName"), DOC_KEYWORD("docKeyword");


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
