package insynctive.utils.slack;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;
import org.json.simple.JSONArray;

import insynctive.utils.Notify;
import insynctive.utils.jenkins.JenkinsForm;
import insynctive.utils.slack.builders.SlackMessageBuilder;

public class SlackUtil {

	public static void sendMessage(SlackMessage message) throws IOException{
		URL u = new URL(message.getUrl());
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		JSONObject payload = new JSONObject();
		payload.put("icon_emoji", message.getIconEmoji());
		payload.put("username", message.getUsername());
		payload.put("text", message.getText());
		payload.put("channel", message.getChannel());
		payload.put("link_names", true);
		
		JSONArray attachments = new JSONArray();
		for(SlackAttachment attach : message.getAttachments()){
			JSONObject attachment = new JSONObject(); 
			attachment.put("fallback", attach.getFallback());
			attachment.put("pretext", attach.getPretext());
			attachment.put("color", attach.getColor()); //AA3939
			JSONArray fields = new JSONArray();
			for(SlackField attcField : attach.getFields()){
				JSONObject field = new JSONObject();
				field.put("title", attcField.getTitle());
				field.put("value", attcField.getValue());
				field.put("short", false);
				fields.add(field);
			}
			
			attachment.put("fields", fields);
			attachments.add(attachment);
		}
		
		payload.put("attachments", attachments);
		
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5000);
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(payload.toString());
		wr.flush();
        wr.close();
        
		InputStream is = conn.getInputStream();
		System.out.println(is);
	}
	
	public static String getSlackAccountMentionByEmail(String email, Notify notifyIfNotExist, String channel) throws IOException{
		switch (email) {
		case "rgonzalez@insynctive.com":
			return "@rgonzalez";
		case "mdjonov@insynctive.com":
			return "@djonov";
		case "dtravieso@insynctive.com":
			return "@dtravieso";
		case "ppetrea@insynctive.com":
			return "@pepe";
		case "svaz@insynctive.com":
			return "@simon";
		case "atodorovski@insynctive.com":
			return "@atanast";
		default:
			if(notifyIfNotExist.value){notifyIfNotExist(email, channel);}
				return "@channel";
		}
	}

	private static void notifyIfNotExist(String email, String channel) throws IOException {
		SlackMessage message = new SlackMessageBuilder()
				.setText("@eugeniovaleiras please set "+email+" to match the right user in Slack")
				.setChannel(":heavy_plus_sign:")
				.setUsername("Bot Notify")
				.setChannel(channel)
				.build();
		SlackUtil.sendMessage(message);
	}
}
