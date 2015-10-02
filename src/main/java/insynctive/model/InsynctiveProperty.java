package insynctive.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import insynctive.utils.data.App;

@Entity
@Table(name = "InsynctiveProperty")
public class InsynctiveProperty {
	
	@Id
	@GeneratedValue
	@Column(name = "insynctive_property_id")
	private int insynctivePropertyID;

	// ACCOUNTS PROPERTIES
	@Column(name = "environment")
	private String environment;

	@Column(name = "loginUserName")
	private String loginUsername;
	@Column(name = "loginPassword")
	private String loginPassword;

	@Column(name = "gmailPassword")
	private String gmailPassword;

	@Column(name = "notification")
	private Boolean notification;
	@Column(name = "remote")
	private Boolean remote;

	public int getId() {
		return insynctivePropertyID;
	}

	public void setId(int id) {
		this.insynctivePropertyID = id;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String enviroment) {
		this.environment = enviroment;
	}

	public Boolean isRemote() {
		return remote;
	}

	public void setRemote(Boolean remote) {
		this.remote = remote;
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

	public String getGmailPassword() {
		return gmailPassword;
	}

	public void setGmailPassword(String gmailPassword) {
		this.gmailPassword = gmailPassword;
	}

	public Boolean isNotification() {
		return notification;
	}

	public void setNotification(Boolean notification) {
		this.notification = notification;
	}

	public List<App> getApps() {
		// TODO Auto-generated method stub
		return null;
	}
}
