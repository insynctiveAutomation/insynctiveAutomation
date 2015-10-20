package insynctive.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestResults {

	public static List<String> results = new ArrayList<String>();
	public static Map<Integer, String> video = new HashMap<>();
	
	public static void resetResults(){
		results = new ArrayList<String>();
	}

	public static void addResult(String result) {
		results.add(result);
	}

	public static void addVideo(Integer id, String jobURL) {
		video.put(id, jobURL);
	}
	
	public static String  getVideo(Integer id) {
		return video.get(id);
	}
	
	
}
