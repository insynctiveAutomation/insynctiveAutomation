package insynctive.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.*;
import com.twilio.sdk.resource.factory.*;
import com.twilio.sdk.resource.instance.*;
import com.twilio.sdk.resource.list.*;

public class TwilioUtil {

	public static final String ACCOUNT_SID = "ACc63fa1400d86c5d326510eaac57c3531";
	public static final String AUTH_TOKEN = "89b1c6e7b46eaea03a0ea0bb8f7d3fc8";
	public static final String COMPANY_PHONE = "(650) 215-6348";

	public static String getVerificationCode() {

		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

		// Build the parameters
		Map<String, String> params = new HashMap<String, String>();
		params.put("From", COMPANY_PHONE);

		MessageList messages = client.getAccount().getMessages(params);
		for (Message message : messages) {
			System.out.println(message.getBody());
			return message.getBody().split("Your HR Self Service verification code is ")[1];
		}
		
		return null;
	}

}
