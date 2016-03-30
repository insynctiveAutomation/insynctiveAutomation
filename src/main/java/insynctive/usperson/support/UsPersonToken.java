package insynctive.usperson.support;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsPersonToken {

//	{
//		access_token: "-J344oq7pZgSbWuFzPm5KrmLay5ZcrOxtSqmd1hwdY2CNhUid3Z3QFaqxW5s0LfFIA7dhxsW71U_kwU2rClhiptJs6gt1AuSs9Y-Og6cptnxz7-wLQBbzQLsE4OSAwKrF5KKrPrJnBR04FqItxK-vUbCtUyNVJ_Fjh-I0lX8dsD41h5RCxauV90zDVijJgJVrI19kW4BaRmtP4iVd1GIMHzyi8dZGaOjtSFOdYoCu2AlG8IZudZpkAtjt3khVBRyE_ooPgCeoxRxxm6Ga6qNbz9lQrwLrUeBhrn_03_Ikix5LIi2IvunA7F1huS3hdFbwPENeXBTHk65pcxMRz5CbRuEUBxS1q6Y1oEhc_7iE1hpvjWcj_PfjvA_a3A7pzRq"
//		token_type: "bearer"
//		expires_in: 1209599
//		userName: "sandbox"
//		.issued: "Tue, 22 Mar 2016 18:15:13 GMT"
//		.expires: "Tue, 05 Apr 2016 18:15:13 GMT"
//	}
	
	@JsonProperty("access_token")
	private String accessToken;
	
	@JsonProperty("userName")
	private String userName;
	
	@JsonProperty("token_type")
	private String tokenType;
	
	@JsonProperty("expires_in")
	private String expiresIn;
	
	@JsonProperty(".issued")
	private String issued;
	
	@JsonProperty(".expires")
	private String expires;

	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getIssued() {
		return issued;
	}
	public void setIssued(String issued) {
		this.issued = issued;
	}
	public String getExpires() {
		return expires;
	}
	public void setExpires(String expires) {
		this.expires = expires;
	}
}
