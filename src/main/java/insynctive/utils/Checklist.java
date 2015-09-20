package insynctive.utils;

import insynctive.exception.ConfigurationException;
import insynctive.utils.process.Process;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Checklist {

	private String name;
	private List<Process> processes;

	public Checklist(String name) {
		this.name = name;
		processes = new ArrayList<Process>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public  List<Process> getProcess(){
		return processes;
	}

	public void addProcess(Process proc) {
		processes.add(proc);
	}

	@Override
	public String toString() {
		return "Nombre: " + name + " | Processes: " + processes;
	}
	
	
	private static String DEFAULT_FILE = "personFileData.json";
	static JSONParser parser = new JSONParser();
	public static Checklist getCheckListToAssign() throws ConfigurationException{
		try {
			JSONObject fileObject = (JSONObject) parser.parse(new FileReader(DEFAULT_FILE));
			JSONObject jsonChecklist = (JSONObject) fileObject.get("Checklist");
			Checklist checklist = new Checklist((String)jsonChecklist.get("name"));
			
		return checklist;
			
		} catch(Exception ex) {
			throw new ConfigurationException("Fail reading (String)person configuration ====> "+ ex.getMessage());
		}
	}
}
