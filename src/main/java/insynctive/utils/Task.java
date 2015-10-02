package insynctive.utils;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import insynctive.exception.ConfigurationException;

public class Task {

	private static String DEFAULT_FILE = "personFileData.json";
	private String detail;
	private String basicTaskInstruction;
	private String additionalInstruction;
	private boolean sign = false;
	private boolean fill = false;
	static JSONParser parser = new JSONParser();

	
	public Task(String detail, String basicTaskInstruction, String additionalInstruction) throws ConfigurationException {
		this.detail = detail;
		this.basicTaskInstruction = basicTaskInstruction;
		this.additionalInstruction = additionalInstruction;
	}
	
	public Task(){
		
	}
	
	public static List<Task> getTasks() throws ConfigurationException{
		return addData(DEFAULT_FILE);
	}
	
	private static List<Task> addData(String path) throws ConfigurationException {
		try {
			JSONObject fileObject = (JSONObject) parser.parse(new FileReader(path));
			JSONArray jsonTasks = (JSONArray) fileObject.get("Tasks");
			List<Task> tasks = new ArrayList<Task>();
			for (Object taskObject : jsonTasks) {
				JSONObject jsonTask = (JSONObject) taskObject;
				Task task = new Task();
				task.detail = (String)jsonTask.get("detail");
				task.basicTaskInstruction = (String)jsonTask.get("basicTaskInstruction");
				task.additionalInstruction = (String)jsonTask.get("additionalInstruction");
				tasks.add(task);
			}
			return tasks;
			
		} catch(Exception ex) {
			throw new ConfigurationException("Fail reading (String)person configuration ====> "+ ex.getMessage());
		}
	}
	
	public String getDetail() {
		return detail;
	}
	public Task setDetail(String detail) {
		this.detail = detail;
		return this;
	}
	public String getBasicTaskInstruction() {
		return basicTaskInstruction;
	}
	public Task setBasicTaskInstruction(String basicTaskInstruction) {
		this.basicTaskInstruction = basicTaskInstruction;
		return this;
	}
	public String getAdditionalInstruction() {
		return additionalInstruction;
	}
	public Task setAdditionalInstruction(String additionalInstruction) {
		this.additionalInstruction = additionalInstruction;
		return this;
	}

	public boolean isFill() {
		return fill;
	}

	public void setFill(boolean fill) {
		this.fill = fill;
	}

	public boolean isSign() {
		return sign;
	}

	public void setSign(boolean sign) {
		this.sign = sign;
	}
	
	public Task sign(){
		this.sign = true;
		return this;
	}
	
	public Task fill(){
		this.fill = true;
		return this;
	}
}
