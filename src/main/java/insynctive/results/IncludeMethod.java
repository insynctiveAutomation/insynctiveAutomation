package insynctive.results;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import insynctive.model.ParamObject;

@JsonIgnoreProperties(ignoreUnknown = true) 
public class IncludeMethod {
	
	private String name;
	private String status;
	private ParamObject paramObject;
	
	public IncludeMethod(){
		
	}
	
	public IncludeMethod(String name, String status){
		this.name = name;
		this.status = status;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public ParamObject getParamObject() {
		return paramObject;
	}

	public void setParamObject(ParamObject paramObject) {
		this.paramObject = paramObject;
	}
}
