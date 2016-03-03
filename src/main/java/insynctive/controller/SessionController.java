package insynctive.controller;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import insynctive.model.Account;
import insynctive.utils.HibernateUtil;

@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS, value="session")
@Controller
@Transactional
public class SessionController {

	//Scope
	public static Account account;
	
	public static void refreshAccount() throws ClientProtocolException, IOException {
		account = HibernateUtil.getAccDao().getAccountByID(account.getAccountID());
	}
	
}
