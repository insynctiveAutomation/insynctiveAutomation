package insynctive.utils.slack;

import java.io.IOException;

import insynctive.utils.UserDetails;
import utils.slack.SlackMessageObject;
import utils.slack.SlackUtil;
import utils.slack.builder.SlackMessageBuilder;

public class SlackUtilInsynctive extends SlackUtil {

	public static final String token =  "xoxp-2598773363-6987940228-21463692417-b6025bd177";
	
	public static String getSlackAccountMentionByEmail(String email) throws IOException{
		
		UserDetails userDetailFindByEmail = UserDetails.findByEmail(email);
		
		if(userDetailFindByEmail != null){
			return userDetailFindByEmail.slackMention;
		} 
		
		if(email != null && !email.equals("")){
			notifyIfNotExist(email);
		}
		return null;
	}
	
	public static String getSlackAccountMentionByName(String name) throws IOException{
		
		UserDetails userDetailFindByName = UserDetails.findByName(name);
		
		if(userDetailFindByName != null){
			return userDetailFindByName.slackMention;
		} 
		
		if(name != null && !name.equals("")){
			notifyIfNotExist(name);
		}
		return null;
	}

	private static void notifyIfNotExist(String email) throws IOException {
		if(email != null && !email.equals("")){
			SlackMessageObject message = new SlackMessageBuilder() 
					.setText(UserDetails.EUGENIO_VALEIRAS.slackMention+" please set "+email+" to match the right user in Slack")
					.setChannel(":heavy_plus_sign:")
					.setUsername("Bot Notify")
					.setChannel("@eugeniovaleiras")
					.build();
			SlackUtilInsynctive.sendMessage(message);
		}
	}
	
}
