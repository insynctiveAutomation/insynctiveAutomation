package insynctive.utils.jenkins;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JenkinsParameters {
	@JsonProperty("User")
	public String user;
	@JsonProperty("Branch")
	public String branch;
	@JsonProperty("Node")
	public String node;
	@JsonProperty("Account")
	public String account;
	@JsonProperty("Version")
	public String version;
	@JsonProperty("LoginServiceAddress")
	public String loginServiceAddress;
	@JsonProperty("ReleaseNumber")
	public String releaseNumber;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}

	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getLoginServiceAddress() {
		return loginServiceAddress;
	}
	public void setLoginServiceAddress(String loginServiceAddress) {
		this.loginServiceAddress = loginServiceAddress;
	}
	public String getReleaseNumber() {
		return releaseNumber;
	}
	public void setReleaseNumber(String releaseNumber) {
		this.releaseNumber = releaseNumber;
	}
	
}