package insynctive.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Account")
public class Account {
	
	@Id
	@GeneratedValue
	@Column(name = "account_id")
	private int accountID;
	
	@Column(name = "run_id")
	private int runID;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "password")
	private String password;
	
	@OneToOne
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "insynctive_property_id")
	private InsynctiveProperty accountProperty;
	
	@OneToOne
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "param_object_id")
	private ParamObject paramObject;

	
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
	
	@JsonIgnore
	public String getRunIDString() {
		return String.valueOf(runID);
	}

	public void setRunID(Integer runID){
		this.runID = runID;
	}
	
	@JsonIgnore
	public void setRunID() throws IOException {
		URL u = new URL("https://insynctive-support.herokuapp.com/runID");
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5000);
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		
		InputStream is = conn.getInputStream();
		
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8")); 
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null){
			responseStrBuilder.append(inputStr);
		}
		
		JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
		Integer runID = jsonObject.getInt("runID");
		this.runID = runID;
	}

	public int incrementRunID(){
		return this.runID+=1;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int account_id) {
		this.accountID = account_id;
	}

	public ParamObject getParamObject() {
		return paramObject;
	}

	public void setParamObject(ParamObject paramObject) {
		this.paramObject = paramObject;
	}
}