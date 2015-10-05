package insynctive.utils.data;

import insynctive.model.EmergencyContact;
import insynctive.model.PersonData;
import insynctive.model.PersonData.Gender;
import insynctive.model.PersonData.MaritalStatus;
import insynctive.model.USAddress;

public enum Employee {

	NO_JOB_EMPLOYEE(noJobEmployee(), "password"),
	W2_EMPLOYEE(w2Employee(), "password"),
	AGENT_OFFICER(agentOfficer(), "password");
	
	public final PersonData personData;
	public final String password;

	private Employee(PersonData personData, String password){
		this.personData = personData;
		this.password = password;
	}
	
	private static PersonData noJobEmployee(){
		PersonData person = new PersonData("Employee", "NoJob", "insynctiveapps+nojobemployee@gmail.com");
			person.setMiddleName("");
			person.setMaidenName("");
			person.setBirthDate("03151992");
			person.setGender(Gender.MALE);
			person.setMaritalStatus(MaritalStatus.SINGLE);
			person.setDepartamentOfEmployee("QA");
			person.setPrimaryPhone("2015555554");
			person.setEmergencyContact(new EmergencyContact("Sara", "Child", "sara@gmail.com", "1111111111"));
			person.setSsn("111223333");

		return person;
	}
	
	private static PersonData w2Employee(){
		USAddress address = new USAddress();
			address.setStreet("Street Example");
			address.setApt("123");
			address.setCity("City Example");
			address.setState("CA");
			address.setZipCode("12345");
			address.setCounty("Orange");
			address.setShortDescription("Description Example");
			address.setSameAsHome(true);
		PersonData person = new PersonData("Employee", "W2", "insynctiveapps+w2employee@gmail.com");
			person.setMiddleName("");
			person.setMaidenName("");
			person.setBirthDate("01111991");
			person.setGender(Gender.FEMALE);
			person.setMaritalStatus(MaritalStatus.DIVORCED);
			person.setDepartamentOfEmployee("QA");
			person.setPrimaryPhone("2015555574");
			person.setEmergencyContact(new EmergencyContact("Sarasa", "Children", "sarasa@gmail.com", "1111111112"));
			person.setSsn("111223333");
			person.setUSAddress(address);
		return person;
	}
	
	private static PersonData agentOfficer(){
		PersonData person = new PersonData("Employee", "Agent", "insynctiveapps+agent@gmail.com");
		person.setMiddleName("");
		person.setMaidenName("");
		person.setBirthDate("05121980");
		person.setGender(Gender.MALE);
		person.setMaritalStatus(MaritalStatus.PARTNER);
		person.setDepartamentOfEmployee("BOSS");
		person.setPrimaryPhone("2015555514");
		person.setEmergencyContact(new EmergencyContact("Sole", "Sister", "sole@gmail.com", "1111111114"));
		person.setSsn("111223333");
		
		return person;
	}
}
