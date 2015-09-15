package insynctive.utils;

public class USAddress {

	private String street;
	private String apt;
	private String secondStreet;
	private String city;
	private String state;
	private String zipCode;
	private String county;
	private String shortDescription;
	private boolean sameAsHome;
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getSecondStreet() {
		return secondStreet;
	}
	public void setSecondStreet(String secondStreet) {
		this.secondStreet = secondStreet;
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
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public boolean isSameAsHome() {
		return sameAsHome;
	}
	public void setSameAsHome(boolean sameAsHome) {
		this.sameAsHome = sameAsHome;
	}
	public String getApt() {
		return apt;
	}
	public void setApt(String apt) {
		this.apt = apt;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
}
