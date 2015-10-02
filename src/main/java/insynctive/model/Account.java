package insynctive.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "Account")
public class Account {
	
	@Id
	@GeneratedValue
	@Column(name = "account_id")
	private int account_id;
	
	@Column(name = "runID")
	private int runID;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;
	
	@NotNull
	@OneToOne
	@Cascade({CascadeType.SAVE_UPDATE})
	@JoinColumn(name = "insynctive_property_id")
	private InsynctiveProperty accountProperty;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public InsynctiveProperty getAccountProperty() {
		return accountProperty;
	}

	public void setAccountProperty(InsynctiveProperty accountProperty) {
		this.accountProperty = accountProperty;
	}

	public int getRunID() {
		return runID;
	}
	
	public String getRunIDString() {
		return String.valueOf(runID);
	}

	public void setRunID(int runID) {
		this.runID = runID;
	}
}