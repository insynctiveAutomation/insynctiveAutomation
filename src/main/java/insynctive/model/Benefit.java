package insynctive.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Benefit {

	@Column(name = "name")
	public String name = "";
	
	@Column(name = "company")
	public String company = "";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
}
