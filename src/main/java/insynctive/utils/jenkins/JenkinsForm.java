package insynctive.utils.jenkins;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import insynctive.utils.slack.SlackUtilInsynctive;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JenkinsForm {
	
	public String name;
	public JenkinsBuild build; 
	
	@JsonIgnore
	public String getMessage() throws IOException {
		String slackAccountMentionByEmail = SlackUtilInsynctive.getSlackAccountMentionByEmail(build.parameters.user);
		
		String mentionUser = (build.parameters.user != null ? slackAccountMentionByEmail  : "@channel")+ " - ";
		
		if(isPhaseStarted()){
			
			return  mentionUser + name + " <" + build.fullUrl + "|"+getValueToShare()+">" + " started from "+build.parameters.branch+" in "+build.parameters.account+" account";
		
		} else if(isStatusSuccess())
		
			return  mentionUser + name + " <" + build.fullUrl + "|"+getValueToShare()+">" + " started from " + build.parameters.branch + " has been successfully installed on " + build.parameters.account;
		
		else if(isStatusFailure() || isStatusAborted()){
		
			return  mentionUser + name + " <" + build.fullUrl + "|"+getValueToShare()+">" + " started from "+build.parameters.branch+" for "+build.parameters.account+" terminated unexpectedly. Reason: "+build.status;
		
		}
		return null;
		
	}
	
	private String getValueToShare() {
		
		if(build.parameters.version != null){
			return "(Version "+build.parameters.version+")"; 
		}
		
		if(build.parameters.releaseNumber != null && build.number != null){
			return "(Release Number "+build.parameters.releaseNumber+build.number+")"; 
		} 
			return  "-";
	}

	@JsonIgnore
	public String getEmoji(){
		if(isStatusSuccess()){

			return ":sunglasses:";
		
		} else if(isStatusFailure() || isStatusAborted()){
		
			return ":sweat:"; 
		
		} else {
		
			if(build.phase.equals("STARTED")){
				return ":simple_smile:";
			}

		}
		return null;
	}
	
	@JsonIgnore
	public String getChannel(){
		if(isMaster()){ 
			return "#master-builds";
		} else if(isIntegration()){
			return "#integration-builds";
		} else {
			return "#jenkins-builds";
		}
	}
	
	@JsonIgnore
	private String getType() {
		String jenkinsName = name != null ? name : "";
		String[] split = jenkinsName.split("TS - Main - ");
		String type = split.length > 0 ? split[1] : "";
		
		return type;
	}

	@JsonIgnore
	public boolean isMaster(){
		 return build.parameters.branch != null && build.parameters.branch.equals("master"); 
	}
	
	@JsonIgnore
	public boolean isIntegration(){
		return build.parameters.branch != null && build.parameters.branch.toLowerCase().contains("integration"); 
	}

	
	@JsonIgnore
	public String getUsername(){
		if(isStatusSuccess()){

			return "Jenkins Install - Success Messenger";
		
		} else if(isStatusFailure() || isStatusAborted()){
		
			return "Failed Jenkins Messenger"; 
			
		} else {
			
			if(build.phase.equals("STARTED")){
				return "New Jenkins Build Messenger";
			}

		}
		
		return null;
	}
	
	@JsonIgnore
	public boolean isPhaseStarted(){
		return build.phase != null && build.phase.equals("STARTED");
	}
	
	@JsonIgnore
	public boolean isStatusSuccess(){
		return build.status != null && build.status.equals("SUCCESS");
	}
	
	@JsonIgnore
	public boolean isStatusAborted(){
		return build.status != null && build.status.equals("ABORTED");
	}
	
	@JsonIgnore
	public boolean isStatusFailure(){
		return build.status != null && build.status.equals("FAILURE");
	}
	
	@JsonIgnore
	public boolean isTypeInstall(){
		return getType().equals("Install");
	}
	
	@JsonIgnore
	public boolean isTypeBuild(){
		return getType().equals("Build");
	}

	@JsonIgnore
	public boolean isDeploy(){
		return getType().equals("Deploy");
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JenkinsBuild getBuild() {
		return build;
	}

	public void setBuild(JenkinsBuild build) {
		this.build = build;
	}
}