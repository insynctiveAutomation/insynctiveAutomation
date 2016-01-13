function Environment() {
	this.name;
}

Environment.getEnvironments = function () {
	return ["alpha1","alpha2","alpha3","alpha4","alpha5","alpha6","alpha7","alpha8","alpha9",
	        "staging","production","release50","demotest", "2FA", "automationqa"];
};

Environment.getBrowsers = function(){
	return [
	        {value : "FIREFOX", text : "Firefox"},
	        {value : "CHROME", text : "Chrome"},
	        {value : "IE_10", text : "IE 10"},
	        {value : "IE_11", text : "IE 11"},
	        {value : "IPAD", text : "iPad"}
	        ];
}