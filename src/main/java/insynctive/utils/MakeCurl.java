package insynctive.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class MakeCurl {
	
	public static JSONObject makeCurl(String url, String username, String password, String type) throws IOException, JSONException{
		String userPassword = username + ":" + password;
		
		java.util.Base64.Encoder encoded = java.util.Base64.getEncoder();
		String crud = encoded.encodeToString(userPassword.getBytes());
		URL u = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		
		conn.setRequestMethod(type);
		conn.setRequestProperty("Authorization", "Basic " + crud);
        		
		InputStream is = conn.getInputStream();
		
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8")); 
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null){
			responseStrBuilder.append(inputStr);
		}
		return new JSONObject(responseStrBuilder.toString());
	}
	
}
