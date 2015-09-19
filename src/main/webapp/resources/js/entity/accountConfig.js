function AccountConfig() {
	this.loginUsername;
	this.loginPassword;
	this.notification;
	this.remote;
}

AccountConfig.asAccountConfig = function (jsonAccountConfig) {
	return angular.extend(new AccountConfig(), jsonAccountConfig);
};