package insynctive.utils.slack.builders;

import insynctive.utils.slack.SlackAttachment;
import insynctive.utils.slack.SlackField;

public class SlackAttachmentBuilder {

	SlackAttachment slackAttachment;

	public SlackAttachmentBuilder() {
		slackAttachment = new SlackAttachment();
	}
	
	public SlackAttachmentBuilder setFallback(String fallback){
		slackAttachment.setFallback(fallback);
		return this;
	}
	
	public SlackAttachmentBuilder setPretext(String pretext){
		slackAttachment.setPretext(pretext);
		return this;
	}
	
	public SlackAttachmentBuilder setColor(String color){
		slackAttachment.setColor(color);
		return this;
	}
	
	public SlackAttachmentBuilder addField(SlackField field){
		slackAttachment.addField(field);
		return this;
	}
	
	public SlackAttachment build(){
		return slackAttachment;
	}
	
}
