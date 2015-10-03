package insynctive.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USAddress")
public class USAddress {
	@Id
	@GeneratedValue
	@Column(name = "USAddress_id")
	private int id;
	
	@Column(name = "street")
	private String street;
	
	@Column(name = "apt")
	private String apt;
	
	@Column(name = "second_street")
	private String secondStreet;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "zip_code")
	private String zipCode;
	
	@Column(name = "county")
	private String county;
	
	@Column(name = "short_description")
	private String shortDescription;
	
	@Column(name = "same_as_home")
	private boolean sameAsHome;
	
	public USAddress() {

	}
	
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
