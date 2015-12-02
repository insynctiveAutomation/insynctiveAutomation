package insynctive.utils.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

import insynctive.exception.ConfigurationException;
import insynctive.model.Account;
import insynctive.model.InsynctiveProperty;
import insynctive.utils.data.App;
import insynctive.utils.data.Checklist;

@Deprecated()
public class InsynctivePropertiesReader {

	//INSTANCE
	private static InsynctivePropertiesReader isInstanced;
	
	// PROPERTIES PATH
	private String DEFAULT_ACCOUNTS_PROPERTIES = "accounts.properties";
	private String DEFAULT_RUNID_PROPERTIES = "run.properties";
	private String DEFAULT_INSTALL_APPS_JSON = "installapps.json";
	private String DEFAULT_CREATE_CHECKLISTS_JSON = "checklists_create.json";

	// RUN ID
		private String runID;

		// ACCOUNTS PROPERTIES
		private String enviroment;
		private String remote;
		
		private String loginUsername;
		private String loginPassword;

		private String newEmployeeName;
		private String newEmployeeLastName;
		private String newEmployeeEmail;
		private String newEmployeePassword;
		private String newEmployeeTitle;
		private String newEmployeeDepartment;

		private String gmailPassword;
		private boolean notification;

		// AppList
		private List<App> apps;

		// ChecklistList
		private List<Checklist> checklists;

	

	public InsynctivePropertiesReader() throws ConfigurationException {
		if (isInstanced == null){
			isInstanced = this;
		}
	}

	public static InsynctivePropertiesReader getAllProperties(WebDriver driver)
			throws ConfigurationException {
		InsynctivePropertiesReader insynctiveProp;
		insynctiveProp = (isInstanced == null) ? new InsynctivePropertiesReader() : isInstanced;
			insynctiveProp = new InsynctivePropertiesReader();
		try {
			insynctiveProp.addAccountsProperties();
			insynctiveProp.addApps();
			insynctiveProp.addCheckLists(driver);
		} catch (Exception ex) {
			throw new ConfigurationException("Check Config => "
					+ ex.getMessage());
		}
		return insynctiveProp;
	}

	public static InsynctivePropertiesReader getAllAccountsProperties()
			throws ConfigurationException {
		InsynctivePropertiesReader insynctiveProp;
		insynctiveProp = (isInstanced == null) ? new InsynctivePropertiesReader() : isInstanced;
		insynctiveProp.addAccountsProperties();
		return insynctiveProp;
	}

	public static InsynctivePropertiesReader getAllApps() throws Exception {
		InsynctivePropertiesReader insynctiveProp;
		insynctiveProp = (isInstanced == null) ? new InsynctivePropertiesReader() : isInstanced;
		insynctiveProp.addApps();
		return insynctiveProp;
	}

	public static InsynctivePropertiesReader getAllChecklist(WebDriver driver)
			throws Exception {
		InsynctivePropertiesReader insynctiveProp;
		insynctiveProp = (isInstanced == null) ? new InsynctivePropertiesReader() : isInstanced;
		insynctiveProp.addCheckLists(driver);
		return insynctiveProp;
	}
	
	public List<App> addApps() throws Exception {
		AppsReader appReader = new AppsReader(DEFAULT_INSTALL_APPS_JSON);
		apps = appReader.getApps();
		return apps;
	}

	public List<Checklist> addCheckLists(WebDriver driver) throws Exception {
		ChecklistReader checklistReader = new ChecklistReader(driver,
				DEFAULT_CREATE_CHECKLISTS_JSON);
		checklists = checklistReader.getAllCheckLists();
		return checklists;
	}

	public void addAccountsProperties() throws ConfigurationException {
		try {
			// Open Properties Files
			Properties accountsProperties = new Properties();
			FileInputStream fileInput = new FileInputStream(
					DEFAULT_ACCOUNTS_PROPERTIES);
			accountsProperties.load(fileInput);

			// Get all properties
			enviroment = accountsProperties.getProperty("environment");
			setRemote(accountsProperties.getProperty("remote"));
			notification = Boolean.parseBoolean(accountsProperties.getProperty("notification"));
			
			loginUsername = accountsProperties.getProperty("loginUsername");
			loginPassword = accountsProperties.getProperty("loginPassword");

			gmailPassword = accountsProperties.getProperty("gmailPassword");

		} catch (Exception ex) {
			throw new ConfigurationException("Check config file => "+ ex.getMessage());
		}
	}

	public void IncrementRunID() throws ConfigurationException {
		try {
			// Open Properties Files
			Properties runPropertie = new Properties();
			File fileID = new File(DEFAULT_RUNID_PROPERTIES);
			FileInputStream runIDFile = new FileInputStream(fileID);
			runPropertie.load(runIDFile);

			// Get runID
			runID = runPropertie.getProperty("runID");
			runPropertie.setProperty("runID",
					String.valueOf(Integer.parseInt(runID) + 1));

			// Save new Properties into File
			OutputStream output = new FileOutputStream(fileID);
			runPropertie.store(output, "LAST RUN");
		} catch (Exception ex) {
			throw new ConfigurationException("Check config file => "+ ex.getMessage());
		}
	}
	
	public static boolean IsRemote() throws ConfigurationException {try {
		InsynctivePropertiesReader insynctiveProp;
		insynctiveProp = (isInstanced == null) ? new InsynctivePropertiesReader() : isInstanced;
		
		// Open Properties Files
		Properties accountsProperties = new Properties();
		FileInputStream fileInput = new FileInputStream(
				insynctiveProp.DEFAULT_ACCOUNTS_PROPERTIES);
		accountsProperties.load(fileInput);

		insynctiveProp.setRemote(accountsProperties.getProperty("remote"));
		return Boolean.parseBoolean(insynctiveProp.getRemote());

		} catch (Exception ex) {
			throw new ConfigurationException("Check config file => "+ ex.getMessage());
		}
	}
	
	public static boolean isNotificationActive() throws ConfigurationException {
		try {
			InsynctivePropertiesReader insynctiveProp;
			insynctiveProp = (isInstanced == null) ? new InsynctivePropertiesReader() : isInstanced;
			
			// Open Properties Files
			Properties accountsProperties = new Properties();
			FileInputStream fileInput = new FileInputStream(
					insynctiveProp.DEFAULT_ACCOUNTS_PROPERTIES);
			accountsProperties.load(fileInput);
	
			insynctiveProp.notification = Boolean.parseBoolean(accountsProperties.getProperty("notification"));
			return insynctiveProp.notification;

		} catch (Exception ex) {
			throw new ConfigurationException("Check config file => "+ ex.getMessage());
		}
	}
	
	public void setEnvironment(String environment) throws ConfigurationException{
		try {
			// Open Properties Files
			Properties accPropertie = new Properties();
			File fileID = new File(DEFAULT_ACCOUNTS_PROPERTIES);
			FileInputStream accFile = new FileInputStream(fileID);
			accPropertie.load(accFile);

			// Get runID
			accPropertie.setProperty("environment",environment);

			// Save new Properties into File
			OutputStream output = new FileOutputStream(fileID);
			accPropertie.store(output, "environment");
		} catch (Exception ex) {
			throw new ConfigurationException("Check config file => "+ ex.getMessage());
		}
	}
	
	public void saveAccConfig() throws ConfigurationException {
		try {
			// Open Properties Files
			Properties accPropertie = new Properties();
			File fileID = new File(DEFAULT_ACCOUNTS_PROPERTIES);
			FileInputStream accFile = new FileInputStream(fileID);
			accPropertie.load(accFile);

			// Get runID
			accPropertie.setProperty("loginUsername",this.loginUsername);
			accPropertie.setProperty("loginPassword",this.loginPassword);
			accPropertie.setProperty("remote",this.remote);
			accPropertie.setProperty("notification",String.valueOf(isNotification()));
			
			// Save new Properties into File
			OutputStream output = new FileOutputStream(fileID);
			accPropertie.store(output, "environment");
		} catch (Exception ex) {
			throw new ConfigurationException("Check config file => "+ ex.getMessage());
		}
	
	}

	public void addAppToList(App app) {
		apps.add(app);
	}

	public void addChecklistToList(Checklist checklist) {
		checklists.add(checklist);
	}

	/* Getters and Setters */
	public String getRunID() {
		return runID;
	}

	public String getEnviroment() {
		return enviroment;
	}

	public String getLoginUsername() {
		return loginUsername;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public String getNewEmployeeName() {
		return newEmployeeName;
	}

	public String getNewEmployeeLastName() {
		return newEmployeeLastName;
	}

	public String getNewEmployeeEmail() {
		return newEmployeeEmail;
	}

	public String getNewEmployeePassword() {
		return newEmployeePassword;
	}

	public String getGmailPassword() {
		return gmailPassword;
	}

	public List<App> getApps() {
		return apps;
	}

	public List<Checklist> getCheckLists() {
		return checklists;
	}

	public String getRunPropertiesStringFile() {
		return this.DEFAULT_RUNID_PROPERTIES;
	}

	public String getAccountsPropertiesStringFile() {
		return this.DEFAULT_ACCOUNTS_PROPERTIES;
	}

	public String getInstallAppsStringFile() {
		return this.DEFAULT_INSTALL_APPS_JSON;
	}

	public String getCreateCheckListsStringFile() {
		return this.DEFAULT_CREATE_CHECKLISTS_JSON;
	}

	public void setRunPropertiesStringFile(String runPropertiesStringFile) {
		this.DEFAULT_RUNID_PROPERTIES = runPropertiesStringFile;
	}

	public void setAccountPropertiesStringFile(
			String accountPropertiesStringFile) {
		this.DEFAULT_ACCOUNTS_PROPERTIES = accountPropertiesStringFile;
	}

	public void setInstallAppsStringFile(String installAppsStringFile) {
		this.DEFAULT_INSTALL_APPS_JSON = installAppsStringFile;
	}

	public void setCreateChecklistsStringFile(String crateChecklistsStringFile) {
		this.DEFAULT_CREATE_CHECKLISTS_JSON = crateChecklistsStringFile;
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

	public boolean isNotification() {
		return notification;
	}

	public void setNotification(boolean notification) {
		this.notification = notification;
	}

	public String getRemote() {
		return remote;
	}

	public void setRemote(String remote) {
		this.remote = remote;
	}

	public static Account createAccount() throws ConfigurationException {
		Account account = new Account();
		InsynctivePropertiesReader properties = getAllAccountsProperties();
		account.setUsername(properties.getLoginUsername());
		account.setPassword(properties.getLoginPassword());
		
		InsynctiveProperty property = new InsynctiveProperty();
		property.setEnvironment(properties.getEnviroment());
		property.setGmailPassword(properties.getGmailPassword());
		property.setNotification(properties.isNotification());
		property.setRemote(Boolean.parseBoolean(properties.getRemote()));
		
		account.setAccountProperty(property);
		return account;
	}
}
