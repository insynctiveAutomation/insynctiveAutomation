package insynctive.usperson.support;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.util.UriUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UsPersonUtil {

	private UsPersonToken token;
	private static ObjectMapper mapper = new ObjectMapper();
	
	public UsPersonUtil(String username, String password) throws ClientProtocolException, IOException {
		token = getAuthentication(username, password);
	}
	
	public static UsPersonToken getAuthentication(String username, String password) throws ClientProtocolException, IOException{
		
		StringEntity entity = new StringEntity("grant_type=password&username="+username+"&password="+password, "UTF-8");
		
		HttpPost httpPost = new HttpPost("http://insynctiveapi.azurewebsites.net/token");
		httpPost.addHeader("Content-Type", "application/json");
		httpPost.setEntity(entity);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpResponse response = httpClient.execute(httpPost);
		String responseBodyString = EntityUtils.toString(response.getEntity(), "UTF-8");
		System.out.println(responseBodyString);
		
		return mapper.readValue(responseBodyString, UsPersonToken.class);
	}
	
	public String saveEmployee(Person person) throws ClientProtocolException, IOException{
		return saveEmployee(person, token);
	}
	
	public static String saveEmployee(Person person, UsPersonToken token) throws ClientProtocolException, IOException{
		
		StringEntity entity = new StringEntity(person.asJsonString(), "UTF-8");
		
		HttpPost httpPost = new HttpPost("http://insynctiveapi.azurewebsites.net/api/persons");
		httpPost.addHeader("Content-Type", "application/json");
		httpPost.addHeader("Authorization", "Bearer " + token.getAccessToken());
		httpPost.setEntity(entity);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpResponse response = httpClient.execute(httpPost);
		
		String responseBodyString = EntityUtils.toString(response.getEntity(), "UTF-8");
		System.out.println(responseBodyString);
		
		return mapper.readValue(responseBodyString, Person.class).getPersonID();
	}
	
	public Person getEmployeeByID(String id) throws ClientProtocolException, IOException{
		return getEmployeeByID(id, token);
	}
	
	public static Person getEmployeeByID(String id, UsPersonToken token) throws ClientProtocolException, IOException{
		HttpGet httpGet = new HttpGet("http://insynctiveapi.azurewebsites.net/api/persons/"+id);
		httpGet.addHeader("Authorization", "Bearer " + token.getAccessToken());
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpResponse response = httpClient.execute(httpGet);
		String responseBodyString = EntityUtils.toString(response.getEntity(), "UTF-8");
		System.out.println(responseBodyString);
		
		return mapper.readValue(responseBodyString, Person.class);
	}
	
	public static void printResponse(HttpResponse response) throws ParseException, IOException{
		System.out.println("Status: \n"+response.getStatusLine().getStatusCode());
		System.out.println("Response body: \n"+EntityUtils.toString(response.getEntity(), "UTF-8"));
		System.out.println("Response: \n"+response);
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		UsPersonUtil insynctiveUtil = new UsPersonUtil("alpha8", "123.Qwe");
		String personID = insynctiveUtil.saveEmployee(new Person("Eugenio", "Testing 2", "Title", "insynctiveapps+testingasd@gmail.com", "QA"));
		Person person = insynctiveUtil.getEmployeeByID(personID);
	}
	
}
