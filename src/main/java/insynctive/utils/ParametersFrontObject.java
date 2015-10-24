package insynctive.utils;

import java.util.ArrayList;
import java.util.List;

public class ParametersFrontObject {
	
	private List<String> params = new ArrayList<String>();
	private List<String> labels = new ArrayList<String>();
	
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public List<String> getParams() {
		return params;
	}
	public void setParams(List<String> params) {
		this.params = params;
	}
	
	public void addParam(String param){
		params.add(param);
	}

	public void addLabel(String label){
		labels.add(label);
	}
}
