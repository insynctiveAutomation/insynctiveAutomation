package insynctive.utils.reader;

import java.io.FileInputStream;
import java.util.Properties;

import insynctive.exception.ConfigurationException;

public class PDFPropertiesReader {

	private static final String DEFAULT_PDFProperties_PROPERTIES = "FillPDF.properties";

	private String fullName;
	private String socialSecurity;
	private String filingStatus;
	private String homeAddress;
	private String city;
	private String state;
	private String zipCode;

	public PDFPropertiesReader() throws ConfigurationException{
		completeAllProperties(DEFAULT_PDFProperties_PROPERTIES);
	}

	public PDFPropertiesReader(String propertiePath) throws ConfigurationException{
		completeAllProperties(propertiePath);
	}

	private void completeAllProperties(String propertiePath)
			throws ConfigurationException {
		try {
			// Open Properties Files
			Properties accountsProperties = new Properties();
			FileInputStream fileInput = new FileInputStream(propertiePath);
			accountsProperties.load(fileInput);

			// Get all properties
			fullName = accountsProperties.getProperty("fullName");
			socialSecurity = accountsProperties.getProperty("socialSecurity");
			filingStatus = accountsProperties.getProperty("filingStatus");
			homeAddress = accountsProperties.getProperty("homeAddress");
			city = accountsProperties.getProperty("city");
			state = accountsProperties.getProperty("state");
			zipCode = accountsProperties.getProperty("zipCode");

		} catch (Exception ex) {
			throw new ConfigurationException("Check config file => "
					+ ex.getMessage());
		}
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSocialSecurity() {
		return socialSecurity;
	}

	public void setSocialSecurity(String socialSecurity) {
		this.socialSecurity = socialSecurity;
	}

	public String getFillingStatus() {
		return filingStatus;
	}

	public void setFillingStatus(String fillingStatus) {
		this.filingStatus = fillingStatus;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
