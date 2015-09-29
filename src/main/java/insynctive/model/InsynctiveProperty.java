package insynctive.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "insynctiveProperty")
public class InsynctiveProperty {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	// RUN ID
	@Column(name = "runID")
	private String runID;

	// ACCOUNTS PROPERTIES
	@Column(name = "environment")
	private String enviroment;
	@Column(name = "remote")
	private Boolean remote;

	@Column(name = "loginUserName")
	private String loginUsername;
	@Column(name = "loginPassword")
	private String loginPassword;

	@Column(name = "newEmployeeName")
	private String newEmployeeName;
	@Column(name = "newEmployeeLastName")
	private String newEmployeeLastName;
	@Column(name = "newEmployeeEmail")
	private String newEmployeeEmail;
	@Column(name = "newEmployeePassword")
	private String newEmployeePassword;
	@Column(name = "newEmployeeTitle")
	private String newEmployeeTitle;
	@Column(name = "newEmployeeDepartment")
	private String newEmployeeDepartment;

	@Column(name = "gmailPassword")
	private String gmailPassword;

	@Column(name = "notification")
	private Boolean notification;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRunID() {
		return runID;
	}

	public void setRunID(String runID) {
		this.runID = runID;
	}

	public String getEnviroment() {
		return enviroment;
	}

	public void setEnviroment(String enviroment) {
		this.enviroment = enviroment;
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

	public String getNewEmployeeName() {
		return newEmployeeName;
	}

	public void setNewEmployeeName(String newEmployeeName) {
		this.newEmployeeName = newEmployeeName;
	}

	public String getNewEmployeeLastName() {
		return newEmployeeLastName;
	}

	public void setNewEmployeeLastName(String newEmployeeLastName) {
		this.newEmployeeLastName = newEmployeeLastName;
	}

	public String getNewEmployeeEmail() {
		return newEmployeeEmail;
	}

	public void setNewEmployeeEmail(String newEmployeeEmail) {
		this.newEmployeeEmail = newEmployeeEmail;
	}

	public String getNewEmployeePassword() {
		return newEmployeePassword;
	}

	public void setNewEmployeePassword(String newEmployeePassword) {
		this.newEmployeePassword = newEmployeePassword;
	}

	public String getNewEmployeeTitle() {
		return newEmployeeTitle;
	}

	public void setNewEmployeeTitle(String newEmployeeTitle) {
		this.newEmployeeTitle = newEmployeeTitle;
	}

	public String getNewEmployeeDepartment() {
		return newEmployeeDepartment;
	}

	public void setNewEmployeeDepartment(String newEmployeeDepartment) {
		this.newEmployeeDepartment = newEmployeeDepartment;
	}

	public String getGmailPassword() {
		return gmailPassword;
	}

	public void setGmailPassword(String gmailPassword) {
		this.gmailPassword = gmailPassword;
	}

	public Boolean getNotification() {
		return notification;
	}

	public void setNotification(Boolean notification) {
		this.notification = notification;
	}
}
