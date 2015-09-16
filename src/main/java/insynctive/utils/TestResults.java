package insynctive.utils;

import java.util.ArrayList;
import java.util.List;

public class TestResults {

	public static List<String> results = new ArrayList<String>();
	public static String video;
	
	public static void resetResults(){
		results = new ArrayList<String>();
		video = null;
	}

	public static void addResult(String result) {
		results.add(result);
	}

	public static void setVideo(String jobURL) {
		video = jobURL;
	}
}
