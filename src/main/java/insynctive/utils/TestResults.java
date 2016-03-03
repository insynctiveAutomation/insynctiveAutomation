package insynctive.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import org.testng.TestListenerAdapter;

public class TestResults {

	public static Map<Integer, String> videos = new HashMap<>();
	public static Map<Integer, TestListenerAdapter> listeners = new HashMap<>();
	public static Map<Integer, Thread> workers = new HashMap<>();
	
	public static Map<String, String> airbrakeLink = new HashMap<>();

	private static Semaphore mutex = new Semaphore(1);
	
	//Video Utils
	public static void addVideo(Integer id, String jobURL) throws InterruptedException {
		mutex.acquire();
		videos.put(id, jobURL);
		mutex.release();
	}
	
	public static void removeVideo(Integer id) throws InterruptedException{
		mutex.acquire();
		videos.remove(id);
		mutex.release();
	}
	
	public static String  getVideo(Integer id) throws InterruptedException {
		mutex.acquire();
		String video = videos.get(id);
		mutex.release();
		return video;
	}
	
	//Listener Utils
	public static void addListener(Integer id, TestListenerAdapter listener) throws InterruptedException {
		mutex.acquire();
		listeners.put(id, listener);
		mutex.release();
	}
	
	public static void removeListener(Integer id) throws InterruptedException {
		mutex.acquire();
		listeners.remove(id);
		mutex.release();
	}
	
	public static TestListenerAdapter getListener(Integer id) throws InterruptedException{
		mutex.acquire();
		TestListenerAdapter listenerAdapter = listeners.get(id);
		mutex.release();
		return listenerAdapter;
	}
	
	//Worker Utils
	public static void addWorker(Integer id, Thread worker) throws InterruptedException {
		mutex.acquire();
		workers.put(id, worker);
		mutex.release();
	}
	
	public static void removeWorker(Integer id) throws InterruptedException {
		mutex.acquire();
		workers.remove(id);
		mutex.release();
	}
	
	public Thread getWorker(Integer id) throws InterruptedException{
		mutex.acquire();
		Thread worker = workers.get(id);
		mutex.release();
		return worker;
	}
	
	//AirBreakLinks
	public static void addAirbrakeLink(String id, String testName, String link) throws InterruptedException{
		mutex.acquire();
		airbrakeLink.put(id+"-"+testName, link);
		mutex.release();
	}
	
	public static void removeAirbrakeLink(String id) throws InterruptedException {
		mutex.acquire();
		List<String> removeKeys = new ArrayList<>();
		
		for(Map.Entry<String, String> entry : airbrakeLink.entrySet()){
			String key = entry.getKey();
			String linkID = key.split("-")[0];
			if(linkID.equals(id)){
				removeKeys.add(entry.getKey());
			}
		}
		
		for(String removeKey : removeKeys){
			airbrakeLink.remove(removeKey);
		}
		
		mutex.release();
	}
	
	public static String getAirbrakeLink(String id, String testName) throws InterruptedException{
		mutex.acquire();
		String getAirebreakLink = airbrakeLink.get(id+"-"+testName);
		mutex.release();
		return getAirebreakLink;
	}
	
	public static String getAirbrakeLinkOrDefault(String id, String testName, String defaultValue) throws InterruptedException{
		mutex.acquire();
		String getAirebreakLink = airbrakeLink.getOrDefault(id+"-"+testName, defaultValue);
		mutex.release();
		return getAirebreakLink;
	}
	 
	public static void removeScopeVars(Integer id) throws InterruptedException {
		Sleeper.sleep(5000);
		removeListener(id);
		removeVideo(id);
		removeWorker(id);
		removeAirbrakeLink(id.toString());
	}
	
	
}
