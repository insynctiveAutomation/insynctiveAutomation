package insynctive.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import insynctive.support.utils.InsynctiveVSEnvironment;
import insynctive.support.utils.PositionInCompany;

public enum UserDetails {

	EUGENIO_VALEIRAS(
			"Eugenio Valeiras", 
			"evaleiras@insynctive.com", 
			"@eugeniovaleiras", 
			"U06V1TN6Q", 
			PositionInCompany.QA),
	
	RODRIGO_GONZALEZ(
			"Rodrigo Gonzalez", 
			"rgonzalez@insynctive.com", 
			"@rgonzalez", 
			"U06T6HPJS", 
			PositionInCompany.QA),
	
	BOJAN_PETROVSKI(
			"Bojan Petrovski", 
			"bpetrovski@insynctive.com", 
			"@bpetrovski", 
			"U06T5UENM", 
			PositionInCompany.QA),
	
	MARIJA_JANEVA(
			"Marija Janeva", 
			"mjaneva@insynctive.com", 
			"@mjaneva", 
			"U06TQ50CW",
			PositionInCompany.SCRUM_MASTER),
	
	ATANAS_TODOROVSKI(
			"Atanas Todorovski", 
			"atodorovski@insynctive.com", 
			"@atanast", 
			"U06TMB96U", 
			PositionInCompany.DEV),
	
	CHIP_PETTIBONE(
			"Chip Pettibone", 
			"cpettibone@insynctive.com", 
			"@cpettibone", 
			"U06T995RA",
			PositionInCompany.PO),
	
	CRISTI_BOTA(
			"Cristi Bota", 
			"cbota@insynctive.com", 
			"@cristibota", 
			"U06V3C021", 
			PositionInCompany.NO_MORE_IN_COMPANY),
	
	MARTIN_DJNOV(
			"Martin Djonov", 
			"mdjonov@insynctive.com", 
			"@djonov", 
			"U06T4HP1Q", 
			PositionInCompany.DEV),
	
	DIEGO_TRAVIESO(
			"Diego Travieso", 
			"dtravieso@insynctive.com", 
			"@dtravieso", 
			"U06UW2LMT", 
			PositionInCompany.NO_MORE_IN_COMPANY),
	
	ERIC_KISH(
			"Eric Kish", 
			"ekish@insynctive.com", 
			"@erickish", 
			"U02HLNRAV", 
			PositionInCompany.OTHER),
	
	IGNACIO_FERNANDEZ(
			"Ignacio Fernandez", 
			"ignaciof6@gmail.com", 
			"@ignacio", 
			"U0GAV6ERM", 
			PositionInCompany.DEV),
	
	LJUPCO_SULEV(
			"Ljupco Sulev", 
			"lsulev@insynctive.com", 
			"@ljupco.sulev", 
			"U06TQTCPP", 
			PositionInCompany.DEV),
	
	ROLAND_ULRICH(
			"Roland Ulrich", 
			"roland.andrei@gmail.com", 
			"@roland", 
			"U071CBLJE", 
			PositionInCompany.OTHER),
	
	SERHII_LYTVYN(
			"Serhii Lytvyn", 
			"slytvyn@insynctive.com", 
			"@serhii", 
			"U06TMJM1U", 
			PositionInCompany.DEV),
	
	SIMON_VAZ(
			"Simon Vaz", 
			"svaz@insynctive.com", 
			"@simon", 
			"U06TPE2UV", 
			PositionInCompany.DEV),
	
	TOME_STOJKOVSKI(
			"Tome Stojkovski", 
			"tstojkovski@insynctive.com", 
			"@tstojkovski", 
			"U06V6UM6K", 
			PositionInCompany.NO_MORE_IN_COMPANY),
	
	VLATKO_NIKOLOVSKI(
			"Vlatko Nikolovski",
			"vlatko_n3@hotmail.com", 
			"@vlatko_n3", 
			"U0GJWU0C9", 
			PositionInCompany.EXTERNAL),
	
	VOJCHE_STOJANOSKI(
			"Vojche Stojanoski", 
			"vstojanoski@insynctive.com", 
			"@vojche", 
			"U0GJSHYSH", 
			PositionInCompany.EXTERNAL),
	
	PEPE(
			"Petrica Petrea", 
			"ppetrea@insynctive.com", 
			"@pepe", 
			"U06UN5W9G", 
			PositionInCompany.DEV);
	
	public String name;
	public String email;
	public String slackID;
	public String slackMention;
	public PositionInCompany positionInCompany;
	
	private UserDetails(String name, String email, String slackMention, String slackID, PositionInCompany positionInCompany) {
		this.name = name;
		this.email = email;
		this.slackID = slackID;
		this.slackMention = slackMention;
		this.positionInCompany = positionInCompany;
	}
	
	public static UserDetails findByName(String name){
		for(UserDetails userDetail : values()){
			if(userDetail.name.equals(name)){
				return userDetail;
			}
		}
		return null;
	}
	
	public static UserDetails findByEmail(String email){
		for(UserDetails userDetail : values()){
			if(userDetail.email.equals(email)){
				return userDetail;
			}
		}
		return null;
	}
	
	public static UserDetails findBySlackMention(String slackMention){
		for(UserDetails userDetail : values()){
			if(userDetail.slackMention.equals(slackMention)){
				return userDetail;
			}
		}
		return null;
	}

	public static UserDetails findByContainStringInName(String nameOfUser) {
		for(UserDetails userDetail : values()){
			if(userDetail.name.contains(nameOfUser)){
				return userDetail;
			}
		}
		return null;
	}
	
	public boolean isQa(){
		return positionInCompany.equals(PositionInCompany.QA);
	}
	
	public boolean isDev(){
		return positionInCompany.equals(PositionInCompany.DEV);
	}
	
	public boolean isPO(){
		return positionInCompany.equals(PositionInCompany.PO);
	}
	
	public boolean isScrumMaster(){
		return positionInCompany.equals(PositionInCompany.SCRUM_MASTER);
	}
	
	public boolean isExternal(){
		return positionInCompany.equals(PositionInCompany.EXTERNAL);
	}
	
	public boolean isInCompany(){
		return !positionInCompany.equals(PositionInCompany.NO_MORE_IN_COMPANY);
	}
	
	public static List<UserDetails> findActiveEmployees(){
		return Arrays.asList(valuesAsList().stream().filter(userDetail -> !userDetail.positionInCompany.equals(PositionInCompany.NO_MORE_IN_COMPANY)).toArray(UserDetails[]::new));
	}
	
	public static List<UserDetails> filter(Filter... filters){
		List<UserDetails> userDetails = valuesAsList();
		List<UserDetails> returnList = new ArrayList<>();
		
		for(UserDetails user : userDetails){
			for(Filter filter : filters){
				if(filter.evaluate(user)){
					returnList.add(user);
				}
			}
		}
		return returnList;
	}
	
	interface Filter {
		boolean evaluate(UserDetails ud);
	}

	public static List<UserDetails> valuesAsList() {
		return Arrays.asList(values());
	}
}
