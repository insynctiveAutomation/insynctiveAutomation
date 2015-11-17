package insynctive.utils;

import java.util.HashMap;
import java.util.Map;

import org.testng.TestListenerAdapter;

public class TestResults {

	public static Map<Integer, String> videos = new HashMap<>();
	public static Map<Integer, TestListenerAdapter> listeners = new HashMap<>();
	public static Map<Integer, Thread> workers = new HashMap<>();

	//Video Utils
	public static void addVideo(Integer id, String jobURL) {
		videos.put(id, jobURL);
	}
	
	public static void removeVideo(Integer id){
		videos.remove(id);
	}
	
	public static String  getVideo(Integer id) {
		return videos.get(id);
	}
	
	//Listener Utils
	public static void addListener(Integer id, TestListenerAdapter listener) {
		listeners.put(id, listener);
	}
	
	public static void removeListener(Integer id) {
		listeners.remove(id);
	}
	
	public static TestListenerAdapter getListener(Integer id){
		return listeners.get(id);
	}
	
	//Worker Utils
	public static void addWorker(Integer id, Thread worker){
		workers.put(id, worker);
	}
	
	public static void removeWorker(Integer id) {
		workers.remove(id);
	}
	
	public Thread getWorker(Integer id){
		return workers.get(id);
	}
	
	public static void removeScopeVars(Integer id) {
		removeListener(id);
		removeVideo(id);
		removeWorker(id);
	}
	
	
}
